package com.fl.esse.model;

import com.fl.esse.entity.HHWUsers;
import com.fl.esse.entity.QHHWUsers;
import com.fl.esse.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserModel {

    @Autowired
    private UserRepository userRepository;

    public List<HHWUsers> findUsers() {
        return userRepository.findAll();
    }

    public void createUser(HHWUsers users) {
        userRepository.save(users);
    }

    public void updateUser(HHWUsers users) {
        userRepository.save(users);
    }

    public void updateUser(List<HHWUsers> users) {
        userRepository.saveAll(users);
    }

    public HHWUsers findUser(String username, String password) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QHHWUsers qhhwUsers = QHHWUsers.hHWUsers;
        booleanBuilder.and(qhhwUsers.name.eq(username));
        booleanBuilder.and(qhhwUsers.password.eq(password));

        return userRepository.findOne(booleanBuilder).orElse(null);
    }

    public HHWUsers findUser(String username) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QHHWUsers qhhwUsers = QHHWUsers.hHWUsers;
        booleanBuilder.and(qhhwUsers.name.eq(username));

        return userRepository.findOne(booleanBuilder).orElse(null);
    }

    public List<HHWUsers> findUsers(List<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    public HHWUsers findUserByPhone(String phone) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QHHWUsers qhhwUsers = QHHWUsers.hHWUsers;
        booleanBuilder.and(qhhwUsers.phone.eq(phone));

        return userRepository.findOne(booleanBuilder).orElse(null);
    }

    public HHWUsers findUserByEmail(String email) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QHHWUsers qhhwUsers = QHHWUsers.hHWUsers;
        booleanBuilder.and(qhhwUsers.email.eq(email));

        return userRepository.findOne(booleanBuilder).orElse(null);
    }
}
