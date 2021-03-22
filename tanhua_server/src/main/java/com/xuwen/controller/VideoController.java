package com.xuwen.controller;

import com.xuwen.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/smallVideos")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping
    private ResponseEntity uploadVedio(MultipartFile videoThumbnail,MultipartFile videoFile) throws IOException {
        videoService.uploadVedio(videoThumbnail,videoFile);
        return ResponseEntity.ok(null);
    }

}
