package net.totime.mail.controller.open;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin
public class TestC {
    @RequestMapping("/hello")
    @SaCheckLogin
    public String test() {
        return "Hello World!";
    }
}