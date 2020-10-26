package com.poc.jwt.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class HomeController {

    @RequestMapping(path = {"/"})
    public String hello() {
        return "hello";
    }

    @RequestMapping(path = {"/free"})
    public String free() {
        return "free";
    }

    @RequestMapping(path = {"/admin"})
    @RolesAllowed("ADMIN")
    public String admin() {
        return "admin";
    }
}
