package com.xuwen.service;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xuwen.api.VideoApi;
import com.xuwen.interceptor.UserHolder;
import com.xuwen.pojo.mongo.Video;
import com.xuwen.templates.OssTemplate;
import org.apache.dubbo.config.annotation.Reference;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VideoService {

    @Reference
    private VideoApi videoApi;
    @Autowired
    private OssTemplate ossTemplate;
    @Autowired
    private FastFileStorageClient client;
    @Autowired
    private FdfsWebServer fdfsWebServer;

    public void uploadVedio(MultipartFile videoThumbnail, MultipartFile videoFile) throws IOException {
        String videoThumbnailUrl = ossTemplate.upload(videoThumbnail.getOriginalFilename(), videoThumbnail.getInputStream());
        String suffix = videoFile.getOriginalFilename().substring(videoFile.getOriginalFilename().lastIndexOf(".") + 1);
        StorePath storePath = client.uploadFile(videoFile.getInputStream(), videoFile.getSize(), suffix, null);
        String videoFileUrl = fdfsWebServer.getWebServerUrl() + storePath.getFullPath();

        Video video = new Video();
        video.setId(ObjectId.get());
        video.setUserId(UserHolder.getUserId());
        video.setText("上传视频");
        video.setPicUrl(videoThumbnailUrl);
        video.setVideoUrl(videoFileUrl);
        video.setCreated(System.currentTimeMillis());

        videoApi.save(video);
    }
}
