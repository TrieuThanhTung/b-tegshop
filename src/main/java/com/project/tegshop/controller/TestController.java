package com.project.tegshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @PostMapping("/hello")
    public String helloWorld1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        return "hello every one1 : " + user;
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @GetMapping("/hello2")
    public String helloWorld2() {
        return "hello every one2";
    }
}
