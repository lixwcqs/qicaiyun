package com.cqs.qicaiyun.common;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * 生成实体模拟数据类工具
 * Created by cqs on 2017/8/30.
 */
@Log4j2
public class MockEntityTemplateTool {
    public static void main(String[] args) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        Template t = ve.getTemplate("scripts/vm/MockEntityTemplate.vm");
        VelocityContext ctx = new VelocityContext();
        final String className = "Follower";//类名
        ctx.put("className", className);
        ctx.put("fullClassName", "com.cqs.jianshu.modules.entity.Follower");
        ctx.put("count", 5);//生成list的元素个数
        ctx.put("date", LocalDateTime.now());//生成list的元素个数
        ctx.put("istName", firstLetterLower(className));//设置对应变量名 首字母小写


        StringWriter sw = new StringWriter();

        t.merge(ctx, sw);

        generateJavaFile(className,sw);

    }

    private static void generateJavaFile(String fileName, StringWriter sw) {
        String path = System.getProperty("user.dir");
        File file = new File(path + "/src/main/java/com/cqs/mock/" + fileName + "Mock.java");
        if (file.exists()) {
            log.info("文件{}已经存在", file.getAbsoluteFile());
        } else {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(sw.toString());
                writer.flush();
                log.info("文件{}生成", file.getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //首字母小写
    private static String firstLetterLower(String modelName) {
        if (StringUtils.isEmpty(modelName)) return "";
        return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
    }
}
