package com.pacheco.app.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
