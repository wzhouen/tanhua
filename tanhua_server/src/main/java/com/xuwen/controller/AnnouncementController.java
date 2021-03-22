package com.xuwen.controller;

import com.xuwen.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcements")
    public ResponseEntity announcements(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int pagesize){
        return announcementService.findPage(page,pagesize);
    }
}
