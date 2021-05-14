package com.example.ged;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public String sayHello(){
        return "jenkins final test7";
    }
}
