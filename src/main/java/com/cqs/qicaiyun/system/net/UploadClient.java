package com.cqs.qicaiyun.system.net;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 客户端实现文件上传　下载
 * <p>
 * create date: 18-7-21 10:43
 */
@Log4j2
public class UploadClient {
    private final static String BOUNDARY_PREFIX = "--";
    private final static String NEW_LINE = "\r\n";
    //数据分割线
    private final static String BOUNDARY = "----WebKitFormBoundaryVc5ISK3OrIupy3EZ";

    public static void upload(String url, File[] files) {
        if (files == null || files.length == 0) {
            log.info("上传文件为空");
            return;
        }
        //URL
        URL url2 = null;
        try {
            url2 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("打开URL失败:", e);
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);


            //表单方式提交
            connection.setRequestProperty("content-type", "multipart/form-data; BOUNDARY=" + BOUNDARY);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            connection.setRequestProperty("Charset", "UTF-8");

            long content_length = 100;
            connection.setRequestProperty("Range", String.valueOf(content_length));
            try (OutputStream os = connection.getOutputStream()) {
                //向流中输出数据
                //开始写入文件
                int count = 0;
                for (File file : files) {
                    //注意
                    StringBuilder start = new StringBuilder(NEW_LINE + BOUNDARY_PREFIX);
                    start.append(BOUNDARY);
                    start.append(NEW_LINE);
                    //TODO  name可根据业务决定是否写死
                    String name = "qicaiyun" + (++count);
                    start.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"");
                    start.append(file.getName());
                    start.append("\"");
                    start.append(NEW_LINE);
                    start.append("Content-type: application/octet-stream");
                    //重要：注意这里是两个换行（其他地方是一个换行）
                    start.append(NEW_LINE);
                    start.append(NEW_LINE);
                    /**
                    ------WebKitFormBoundaryVc5ISK3OrIupy3EZ
                    Content-Disposition: form-data; name="qicaiyun2"; filename="WebSocketClient.java"
                    Content-type: application/octet-stream


                     **/
                    log.debug("start:" + start.toString());
                    os.write(start.toString().getBytes());
                    os.flush();
                    //写入内容
                    byte[] buf = new byte[1024 * 512];
                    try (FileInputStream fis = new FileInputStream(file)) {
                        int len;
                        while ((len = fis.read(buf)) != -1) {
                            os.write(buf, 0, len);
                            content_length += len;
                        }
                        os.flush();
                    }
                    log.debug("写入文件{}到输出流", file.getName());
                }
                //上传文件结束
                StringBuilder end = new StringBuilder(NEW_LINE + BOUNDARY_PREFIX);
                end.append(BOUNDARY);
                end.append("--");
                end.append(NEW_LINE);
                os.write(end.toString().getBytes());
                /**
                 ------WebKitFormBoundaryVc5ISK3OrIupy3EZ--

                 */
                log.debug("end:" + end);
                os.flush();
            }
            //打印返回结果
            try (InputStream is = connection.getInputStream()) {
                if (is != null) {
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    log.info("返回结果:"+new String(bytes));
                    is.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("打开" + url + "失败:" + e.getMessage());
        }

    }


    /**
     * 客户端实现文件下载
     *
     * @param url
     */
    public static void download(String url) {
        URL url2 = null;
        try {
            url2 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("打开URL失败:", e);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            String field = connection.getHeaderField("Content-Disposition");
            String fn = "filename=";
            String fileName = "unknown";
            if (StringUtils.isNotEmpty(field) && field.contains(fn)) {
                fileName = field.substring(field.indexOf(fn));
            }
            FileOutputStream fos;
            try (InputStream is = connection.getInputStream()) {
                //输出的是文件类型
                File file = new File(fileName);
                fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024 * 512];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param url      请求地址
     * @param map      请求的参数
     * @param filePath 文件路径
     * @param charset  字符集
     * @return
     */
    public static String doPostSubmitBody(String url, Map<String, String> map,
                                          String filePath, String charset) {
        // 设置三个常用字符串常量：换行、前缀、分界线（NEWLINE、PREFIX、BOUNDARY）；
        final String NEWLINE = "\r\n"; // 换行，或者说是回车
        final String PREFIX = "--"; // 固定的前缀
        final String BOUNDARY = "#"; // 分界线，就是上面提到的boundary，可以是任意字符串，建议写长一点，这里简单的写了一个#
        HttpURLConnection httpConn = null;
        BufferedInputStream bis = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 实例化URL对象。调用URL有参构造方法，参数是一个url地址；
            URL urlObj = new URL(url);
            // 调用URL对象的openConnection()方法，创建HttpURLConnection对象；
            httpConn = (HttpURLConnection) urlObj.openConnection();
            // 调用HttpURLConnection对象setDoOutput(true)、setDoInput(true)、setRequestMethod("POST")；
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            // 设置Http请求头信息；（Accept、Connection、Accept-Encoding、Cache-Control、Content-Type、User-Agent），不重要的就不解释了，直接参考抓包的结果设置即可
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            httpConn.setRequestProperty("Cache-Control", "no-cache");
            // 这个比较重要，按照上面分析的拼装出Content-Type头的内容
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; BOUNDARY=" + BOUNDARY);
            // 这个参数可以参考浏览器中抓出来的内容写，用chrome或者Fiddler抓吧看看就行
            httpConn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
            // 调用HttpURLConnection对象的connect()方法，建立与服务器的真实连接；
            httpConn.connect();

            // 调用HttpURLConnection对象的getOutputStream()方法构建输出流对象；
            dos = new DataOutputStream(httpConn.getOutputStream());
            // 获取表单中上传控件之外的控件数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
            if (map != null && !map.isEmpty()) { // 这时请求中的普通参数，键值对类型的，相当于上面分析的请求中的username，可能有多个
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey(); // 键，相当于上面分析的请求中的username
                    String value = map.get(key); // 值，相当于上面分析的请求中的sdafdsa
                    dos.writeBytes(PREFIX + BOUNDARY + NEWLINE); // 像请求体中写分割线，就是前缀+分界线+换行
                    dos.writeBytes("Content-Disposition: form-data; "
                            + "name=\"" + key + "\"" + NEWLINE); // 拼接参数名，格式就是Content-Disposition: form-data; name="key" 其中key就是当前循环的键值对的键，别忘了最后的换行
                    dos.writeBytes(NEWLINE); // 空行，一定不能少，键和值之间有一个固定的空行
                    dos.writeBytes(URLEncoder.encode(value.toString(), charset)); // 将值写入
                    // 或者写成：dos.write(value.toString().getBytes(charset));
                    dos.writeBytes(NEWLINE); // 换行
                } // 所有循环完毕，就把所有的键值对都写入了
            }
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] body_data = new byte[fis.available()];
            fis.read(body_data);
            // 获取表单中上传附件的数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
            if (body_data.length > 0) {
                dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);// 像请求体中写分割线，就是前缀+分界线+换行
                String fileName = filePath.substring(filePath
                        .lastIndexOf(File.separatorChar) + 1); // 通过文件路径截取出来文件的名称，也可以作文参数直接传过来
                // 格式是:Content-Disposition: form-data; name="请求参数名"; filename="文件名"
                // 我这里吧请求的参数名写成了uploadFile，是死的，实际应用要根据自己的情况修改
                // 不要忘了换行
                dos.writeBytes("Content-Disposition: form-data; " + "name=\""
                        + "uploadFile" + "\"" + "; filename=\"" + fileName
                        + "\"" + NEWLINE);
                // 换行，重要！！不要忘了
                dos.writeBytes(NEWLINE);
                dos.write(body_data); // 上传文件的内容
                dos.writeBytes(NEWLINE); // 最后换行
            }
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE); // 最后的分割线，与前面的有点不一样是前缀+分界线+前缀+换行，最后多了一个前缀
            dos.flush();

            // 调用HttpURLConnection对象的getInputStream()方法构建输入流对象；
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            // 调用HttpURLConnection对象的getResponseCode()获取客户端与服务器端的连接状态码。如果是200，则执行以下操作，否则返回null；
            if (httpConn.getResponseCode() == 200) {
                bis = new BufferedInputStream(httpConn.getInputStream());
                while ((c = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, c);
                    baos.flush();
                }
            }
            // 将输入流转成字节数组，返回给客户端。
            return new String(baos.toByteArray(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null)
                    dos.close();
                if (bis != null)
                    bis.close();
                if (baos != null)
                    baos.close();
                httpConn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        File[] files = {
                new File("/home/li/IdeaProjects/qicaiyun/src/main/java/com/cqs/qicaiyun/system/net/websocket/PushDemo.java"),
                new File("/home/li/IdeaProjects/qicaiyun/src/main/java/com/cqs/qicaiyun/system/net/websocket/WebSocketClient.java")
        };
        String url = "http://192.168.2.116:9090/qicaiyun/image/upload";
        upload(url, files);

        String url2 = "http://192.168.2.116:9090/qicaiyun/image/download";
        download(url2);

//        String s = doPostSubmitBody(url, null, "/home/li/IdeaProjects/qicaiyun/src/main/java/com/cqs/qicaiyun/system/net/websocket/PushDemo.java"
//                , "utf-8");
//        log.info(s);
    }

}
