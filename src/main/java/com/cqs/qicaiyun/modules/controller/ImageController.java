package com.cqs.qicaiyun.modules.controller;


import com.cqs.qicaiyun.common.FTPUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cqs on 2017/8/9.
 */
@RestController
@RequestMapping("/image")
@Log4j2
public class ImageController {

    //这里可以使用zookeeper来存储
    private String host = "192.168.2.145";
    private FTPUtils.FTPHelper helper = FTPUtils.FTPHelper.builder().setHost(host)
            .setPort(21).setUser("uftp").setPassword("1");

    @PostMapping("/upload")
    public Map<String, Object> uploadImg(@RequestParam(value = "fileDataFileName") MultipartFile img) {
        Map<String, Object> resultMap = new HashMap<>();
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String workingDir = "/home/uftp/pictures/qicaiyun/img/" + today;//不知道这里为何根目录就是/
        helper.setWorkingDirectory(workingDir);
        helper.setTargetFile(img.getOriginalFilename());
        try {
            helper.setInStream(img.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取文件输入流异常");
        }
        FTPUtils.upload(helper);
        //存储到文件系统
        resultMap.put("success", true);
//        String filePath = "http://www.image.com/img/" + today + "/" + helper.getTargetFile();
        String filePath = "http://www.image.com/" + today + "/" + helper.getTargetFile();
        resultMap.put("file_path", filePath);
        resultMap.put("msg", "图片上传完成");
        return resultMap;
    }

}
