package com.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    @GetMapping("hello")
    public String hello(@RequestParam(value = "name") String name) {
        log.debug("name = {}", name);
        log.info("name = {}", name);
        log.error("name = {}", name);
        return "hello, " + name;
    }
}
