package com.fl.esse.service;

import com.fl.esse.consts.RoleCode;
import com.fl.esse.consts.UserStatus;
import com.fl.esse.entity.HHWEnterprises;
import com.fl.esse.entity.HHWUsers;
import com.fl.esse.exception.BusinessException;
import com.fl.esse.model.UserModel;
import com.fl.esse.security.PasswordGenerator;
import com.fl.esse.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserModel userModel;

    @Autowired
    private UserContext userContext;

    public List<HHWUsers> findUsers() {
        return userModel.findUsers();
    }

    public void createUser(HHWUsers user) {
        this.validatePermission(user);
        this.validateData(user);

        user.setStatus(UserStatus.NORMAL);
        user.setPassword(PasswordGenerator.getPassword(user.getPassword()));
        userModel.createUser(user);
    }

    public void invalidUsers(List<Integer> userIds) {
        List<HHWUsers> hhwUsers = userModel.findUsers(userIds);
        hhwUsers.forEach(u -> {
            this.validatePermission(u);
            u.setStatus(UserStatus.PENDING);
        });

        userModel.updateUser(hhwUsers);
    }

    private void validatePermission(HHWUsers user) {
        HHWUsers contextUser = userContext.getCurrentUser();
        List<Integer> managedIds = contextUser.getHhwEnterprises()
                .stream()
                .filter(h -> h.getHhwRoles().getCode() != null &&
                        (h.getHhwRoles().getCode().equals(RoleCode.ADMIN) || h.getHhwRoles().getCode().equals(RoleCode.ENTERPRISE)))
                .map(HHWEnterprises::getId)
                .collect(Collectors.toList());
        if (!user.getHhwEnterprises()
                .stream()
                .allMatch(hhwEnterprises -> managedIds.contains(hhwEnterprises.getId()))) {
            throw new BusinessException("不能操作其他企业的数据");
        }
    }

    private void validateData(HHWUsers user) {
        if (user.getName() == null) {
            throw new BusinessException("登录名不能为空");
        }
        HHWUsers existUser = userModel.findUser(user.getName());
        if (existUser != null) {
            throw new BusinessException("用户" + existUser.getName() + "已存在");
        }
        if (user.getPassword() == null || user.getPassword().length() > 16 || user.getPassword().length() < 8) {
            throw new BusinessException("只能设置长度8 - 16位的密码");
        }
        if (user.getPhone() == null) {
            throw new BusinessException("手机号不能为空");
        }
        if (userModel.findUserByPhone(user.getPhone()) != null) {
            throw new BusinessException("手机号不能重复");
        }
        if (user.getEmail() != null && userModel.findUserByEmail(user.getEmail()) != null) {
            throw new BusinessException("邮箱不能重复");
        }
    }

    public HHWUsers login(String username, String password) {
        HHWUsers result = userModel.findUser(username, PasswordGenerator.getPassword(password));
        if (result == null) {
            throw new BusinessException("用户不存在");
        }

        return result;
    }
}
