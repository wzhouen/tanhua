package com.xuwen.controller;

import com.xuwen.pojo.vo.MomentVo;
import com.xuwen.pojo.vo.PageResult;
import com.xuwen.pojo.vo.PublishVo;
import com.xuwen.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    /**
     * 发布动态
     * @param publishVo
     * @param imageContent
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity postMoment(PublishVo publishVo, MultipartFile[] imageContent) throws IOException {
        movementService.save(publishVo,imageContent);
        return ResponseEntity.ok(null);
    }

    /**
     * 查询好友动态
     * @return
     */
    @GetMapping
    public ResponseEntity queryFriendPublishList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pagesize){
        PageResult<MomentVo> pageResult = movementService.queryFriendPublishList(page,pagesize);
        return ResponseEntity.ok(pageResult);
    }
}
