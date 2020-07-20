package com.feng.youfang.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengmuhai
 * @date 2020/7/20
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/youfound")
    public String youfound() {
        return "Hello Youfound!";
    }
}
