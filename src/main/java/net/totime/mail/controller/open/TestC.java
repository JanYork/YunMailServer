package net.totime.mail.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class TestC {
    @RequestMapping("/hello")
    public String test(Model model) {
        HashMap<String, String> map = new HashMap<>();
        //设置模板参数
        map.put("code", "200");
        map.put("token", "123456");
        map.put("msg", "success");
        map.put("data", "123456");
        map.put("states", "success");
        model.addAllAttributes(map);
        return "login";
    }
}