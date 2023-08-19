package com.fl.esse.security;

import com.fl.esse.entity.HHWUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserContext {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public HHWUsers getCurrentUser() {
        return (HHWUsers) httpServletRequest.getSession().getAttribute("user");
    }
}
