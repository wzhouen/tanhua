package com.xuwen.test;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import com.xuwen.TanhuaServerApplication;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TanhuaServerApplication.class)
public class FastDFSTest {

    @Autowired
    private FastFileStorageClient client;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    @Test
    public void testUploadFile() throws IOException {
        File file = new File("D:\\电影\\黑马数据文件\\day64_探花第七天\\资料\\01-视频图片\\1.mp4");
        StorePath storePath = client.uploadFile(FileUtils.openInputStream(file), file.length(), "mp4", null);
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());

        //获取文件请求地址
        String url = fdfsWebServer.getWebServerUrl()+storePath.getFullPath();
        System.out.println(url);
    }
}