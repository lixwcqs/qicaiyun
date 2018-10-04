package com.cqs.qicaiyun.modules.controller;


import com.cqs.qicaiyun.common.FTPUtils;
import com.cqs.qicaiyun.common.result.FailedResult;
import com.cqs.qicaiyun.common.result.Result;
import com.cqs.qicaiyun.common.result.SuccessResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cqs on 2017/8/9.
 */
@RestController
@RequestMapping("/image/")
@Log4j2
public class ImageController {

    //这里可以使用zookeeper来存储
    private String host = "192.168.2.145";
    private FTPUtils.FTPHelper helper = FTPUtils.FTPHelper.builder().setHost(host)
            .setPort(21).setUser("uftp").setPassword("1");

    @PostMapping("uploadImage")
    public Map<String, Object> uploadImg(@RequestParam(value = "fileDataFileName") MultipartFile img) {
        Map<String, Object> resultMap = new HashMap<>();
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String workingDir = "/home/uftp/pictures/qicaiyun/img/" + today;//不知道这里为何根目录就是/
        helper.setWorkingDirectory(workingDir);
        helper.setTargetFile(img.getOriginalFilename());
        try {
            helper.setInStream(img.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取文件输 入流异常");
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


    @PostMapping("/upload")
    public Result upload(HttpServletRequest request) {
        try {
            Collection<Part> parts = request.getParts();
            if (parts != null) {
                byte[] buf = new byte[1024 * 512];
                parts.stream().filter(part -> !part.getName().startsWith("qicaiyun"))
                        .forEach(part -> {
                            try (InputStream is = part.getInputStream()) {
                                String name = part.getSubmittedFileName();
//                                log.debug("name:" + name);
                                File file = new File(name);
                                int len;
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    while ((len = is.read(buf)) != -1) {
                                        fos.write(buf, 0, len);
                                    }
                                    fos.flush();
                                }
                                log.info("上传文件{}成功", name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException | ServletException e) {
            return FailedResult.build().reason(e.getMessage());
        }
        return SuccessResult.build().result("SUCCESS");
    }

    //文件下载
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        //下载文件路径 -- demo
        File file = new File("/home/li/Documents/sublime_text_3_build_3176_x64.tar.bz2");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
            //
            try (ServletOutputStream os = response.getOutputStream()) {
                int len;
                byte[] download = new byte[1024 * 512];
                while ((len = fis.read(download)) != -1) {
                    os.write(download, 0, len);
                }
                os.flush();
            }
            response.setContentLengthLong(file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
