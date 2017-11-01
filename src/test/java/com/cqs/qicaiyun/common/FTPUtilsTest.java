package com.cqs.qicaiyun.common;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.cqs.qicaiyun.common.FTPUtils.upload;

/**
 * Created by cqs on 2017/10/30.
 */
public class FTPUtilsTest {

    private String host = "192.168.2.145";
    private FTPUtils.FTPHelper helper = FTPUtils.FTPHelper.builder().setHost(host)
            .setPort(21).setUser("uftp").setPassword("1");

    @Test
    public void testUpload() throws Exception {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String workingDir = "/home/uftp/pictures/qicaiyun/img/" + today;//不知道这里为何根目录就是/
        File file = new File("C:\\Users\\cqs\\Pictures\\1.png");
        helper.setWorkingDirectory(workingDir);
        helper.setTargetFile(file.getName());
        helper.setInStream(new FileInputStream(file));
        upload(helper);
        String path = "http://www.image.com/qicaiyun/" + today + "/" + file.getName();
        System.out.println(path);
    }
}