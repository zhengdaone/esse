package com.fl.esse.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fl.esse.entity.HHWUsers;
import com.fl.esse.exception.BusinessException;
import com.fl.esse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET, path = "/v1/users")
    @ResponseBody
    public ResponseEntity<?> findUsers() {
        if (request.getSession().getAttribute("user") == null) {
            throw new BusinessException("未认证");
        }

        return ResponseEntity.ok(userService.findUsers());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/v1/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody JsonNode jsonNode) {
        HHWUsers user = userService.login(jsonNode.path("username").textValue(), jsonNode.path("password").textValue());
        request.getSession().setAttribute("user", user);
        // TODO: 2023/8/15 设置需要的信息

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/v1/users/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody HHWUsers hhwUsers) {
        userService.createUser(hhwUsers);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/v1/users/invalid")
    @ResponseBody
    public ResponseEntity<?> invalid(@RequestBody List<Integer> userIds) {
        userService.invalidUsers(userIds);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
