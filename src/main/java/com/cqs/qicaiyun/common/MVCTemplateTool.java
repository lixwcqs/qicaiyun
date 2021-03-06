package com.cqs.qicaiyun.common;

//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang.StringUtils;
//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.VelocityEngine;
//import org.apache.velocity.runtime.RuntimeConstants;
//import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 生成实体模拟数据类工具
 * Created by cqs on 2017/8/30.
 */
//@Log4j2
public class MVCTemplateTool {
//    public static void main(String[] args) {
//        VelocityEngine ve = new VelocityEngine();
//        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//        ve.init();
//

//        final String entityClass = "Include";//类名
////        String[] vms = {"MapperTemplate","ServiceTemplate","ServiceImplTemplate"};
//        Map<String,String> map = new HashMap<>();
//        map.put("MapperTemplate",String.format("/src/main/java/com/cqs/qicaiyun/modules/mapper/%sMapper.java",entityClass));
//        map.put("ServiceTemplate",String.format("/src/main/java/com/cqs/qicaiyun/modules/service/%sService.java",entityClass));
//        map.put("ServiceImplTemplate",String.format("/src/main/java/com/cqs/qicaiyun/modules/service/impl/%sServiceImpl.java",entityClass));
//        map.put("MockEntityTemplate",String.format("/src/main/java/com/cqs/qicaiyun/mock/%sMock.java",entityClass));
//        VelocityContext ctx = new VelocityContext();
//        ctx.put("entityClass", entityClass);
//        ctx.put("istName", firstLetterLower(entityClass));
//        ctx.put("date", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        ctx.put("istNum",5);
//        for (String vm : map.keySet()) {
//            Template t = ve.getTemplate("template/vm/" + vm);
//            StringWriter sw = new StringWriter();
//            t.merge(ctx, sw);
//            generateJavaFile(map.get(vm),sw);
//        }
//
//    }
//
//    private static void generateJavaFile(String fileName, StringWriter sw) {
//        String path = System.getProperty("user.dir");
//        File file = new File(path + fileName);
//        if (file.exists()) {
//            log.info("文件{}已经存在", file.getAbsoluteFile());
//        } else {
//            try {
//                FileWriter writer = new FileWriter(file);
//                writer.write(sw.toString());
//                writer.flush();
//                log.info("文件{}生成", file.getAbsoluteFile());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //首字母小写
//    private static String firstLetterLower(String modelName) {
//        if (StringUtils.isEmpty(modelName)) return "";
//        return modelName.substring(0, 1).toLowerCase() + modelName.substring(1);
//    }
}
