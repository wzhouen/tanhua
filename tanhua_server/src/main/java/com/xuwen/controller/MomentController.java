package com.xuwen.controller;

import com.xuwen.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movement")
public class MomentController {
    @Autowired
    private MomentService momentService;

    @GetMapping("/{id}/like")
    public ResponseEntity like(@PathVariable("id") String publishId){
        return momentService.like(publishId);
    }

    @GetMapping("/{id}/unlike")
    public ResponseEntity unlike(@PathVariable("id") String publishId){
        return momentService.unlike(publishId);
    }
}
