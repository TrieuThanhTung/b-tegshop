package com.project.tegshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api")
@Tag(name = "user", description = "@Tag(name = \"User\", description = \"The User API. Contains all the operations that can be performed on a user.\")")
public class TestController {

    @PreAuthorize("hasAnyAuthority('CUSTOMER')")

    @PostMapping("/hello")
    public String helloWorld1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
//        Object pw = authentication.getCredentials();

//        log.info("password: " + pw.toString());

        return "hello every one1 : " + user;
    }

    @PreAuthorize("hasAnyAuthority('SELLER')")
    @GetMapping("/hello2")
    public String helloWorld2() {
        return "hello every one2";
    }
}
