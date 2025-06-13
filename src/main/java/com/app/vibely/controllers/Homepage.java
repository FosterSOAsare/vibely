package com.app.vibely.controllers;


import com.app.vibely.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Homepage {
    @RequestMapping("/")
    public Message home() {
        return new Message("Vibely server running");
    }

    @RequestMapping("/api")
    public Message api() {
        return new Message( "Vibely server running. Add valid endpoints to proceed");
    }
}
