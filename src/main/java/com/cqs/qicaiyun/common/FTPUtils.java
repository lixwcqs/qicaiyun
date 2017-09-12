package com.cqs.qicaiyun.common;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * FTP上传下载的工具类
 * Created by cqs on 2017/8/10.
 */
public class FTPUtils {

    private static Logger logger = LoggerFactory.getLogger(FTPUtils.class);


    public static void printDirectory(@NotNull FTPHelper helper) {
        FTPClient cli = connection(helper);
        Optional.ofNullable(cli).ifPresent(client -> {
            try {
                //判断目录是否存在
                if (!client.changeWorkingDirectory(helper.getWorkingDirectory()))
                    throw new RuntimeException("ftp不存在目录" + helper.getWorkingDirectory());
                //logger.debug("当前工作目录:" + client.printWorkingDirectory());
                FTPFile[] files = client.listFiles();
                for (FTPFile file : files) {
                    logger.debug("file:" + file.getName());
                }
            } catch (IOException e) {
                logger.error("error happens:", e);
            } finally {
                try {
                    if (client.isConnected())
                        client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 但单文件下载
     * 注意：要设置
     * workDirectory
     * target为要下载的文件名 比如a.txt 不可以带有路径 比如 bb/aa.png
     *
     * @param helper
     */
    public static void download(@NotNull FTPHelper helper) {
        FTPClient cli = connection(helper);
        Optional.ofNullable(cli).ifPresent(client -> {
            String target = helper.getTargetFile();
            logger.debug("fileName" + target);
            try {
                System.out.println("当前工作目录：" + client.printWorkingDirectory());
                //切换工作目录
                if (!StringUtils.equals(client.printWorkingDirectory(), helper.getWorkingDirectory())) {
                    client.changeWorkingDirectory(helper.getWorkingDirectory());
                }
                retrieveFile(client, helper.getDownloadDir(), target);
            } catch (IOException e) {
                throw new RuntimeException(String.format("从ftp服务器下载文件异常"));
            }
        });
    }


    /**
     * 下载某个目录下的文件
     *
     * @param helper
     */
    public static void mutiDownload(@NotNull FTPHelper helper) {
        FTPClient cli = connection(helper);
        Optional.ofNullable(cli).ifPresent(client -> {
            try {
                System.out.println("当前工作目录：" + client.printWorkingDirectory());
                //切换工作目录
                if (!StringUtils.equals(client.printWorkingDirectory(), helper.getWorkingDirectory())) {
                    client.changeWorkingDirectory(helper.getWorkingDirectory());
                }
                FTPFile[] ftpFiles = client.listFiles(client.printWorkingDirectory(), FTPFile::isFile);
                for (FTPFile file : ftpFiles) {
                    retrieveFile(client, helper.getDownloadDir(), file.getName());
                }

            } catch (IOException e) {
                throw new RuntimeException(String.format("从ftp服务器下载文件异常"));
            }
        });
    }

    /**
     * @param client
     * @param download 下载存放路径
     * @param target   待下载文件
     */
    private static void retrieveFile(FTPClient client,@NotNull String download, String target) {
        if (StringUtils.endsWith(download, "/")) {
            //要是最后一个是/
            download = download.substring(0, download.length() - 1);
        }
        File down = new File(download);
        if (!down.exists()) {
            down.mkdirs();
        }
        if (down.isFile()) {
            throw new RuntimeException("存放路径不能是文件");
        }
        File file = new File(download + "/" + target);
        FileOutputStream foo = null;
        try {
            foo = new FileOutputStream(file);
            boolean isOk = client.retrieveFile(target, foo);
            logger.debug("下载文件{}成功？{}", target, isOk);
        } catch (IOException e) {
            throw new RuntimeException(String.format("从ftp服务器下载文件异常"));
        } finally {
            IOUtils.closeQuietly(foo);
        }


    }

    public static void upload(FTPHelper helper) {
        FTPClient cli = connection(helper);
        Optional.ofNullable(cli).ifPresent(client -> {
            try {

                client.setFileType(FTP.BINARY_FILE_TYPE);
                changeWorkingDirectory(helper, client);
                boolean isStore = client.storeFile(helper.getTargetFile(), helper.getInStream());
                helper.getInStream().close();
                client.logout();
                if (!isStore) {
                    throw new RuntimeException(String.format("上传文档{}失败", helper.getWorkingDirectory()));
                }
            } catch (IOException e) {
                throw new RuntimeException("连接ftp服务器异常");
            } finally {
                if (client.isConnected()) {
                    try {
                        cli.disconnect();
                    } catch (IOException e) {
                        logger.warn("关闭异常");
                    }
                }
            }
        });
    }

    /**
     * 切换工作目录
     *
     * @param helper
     * @param client
     * @throws IOException
     */
    private static void changeWorkingDirectory(FTPHelper helper, FTPClient client) throws IOException {
        String dir = helper.getWorkingDirectory();
        String cwd = client.printWorkingDirectory();
        //若要不相同的话 需要切换工作目录 否则不需要切换
        if (!StringUtils.equals(cwd, dir)) {
            boolean changeOK = client.changeWorkingDirectory(dir);
            //没有切换成功 说明ftp服务器不存在dir目录
            if (!changeOK) {
                //不能递归创建目录
                boolean makeDir = client.makeDirectory(dir);
                logger.debug("mkdirDir:" + makeDir);
                changeOK = client.changeWorkingDirectory(dir);
                if (!changeOK) {//要是还是切换失败 终止上传文件
                    logger.error("切换到工作目录{}失败上传文件失败", dir);
                    throw new RuntimeException("切换到工作目录失败，上传文件失败");
                }
            }
        }
    }

    private static FTPClient connection(FTPHelper helper) {
        FTPClient client = new FTPClient();
        try {
            client.connect(helper.host, helper.port);
            //不加入都取不出文件 且必须放在 client.connect(helper.host, helper.port);方法后面
            client.enterLocalPassiveMode();
//            client.login("anonymous", null);//匿名登录
            client.login(helper.getUser(), helper.getPassword());
            int reply = client.getReplyCode();
            //logger.debug(reply + "\t" + client.getDataConnectionMode());
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return null;
            }
            logger.info("FTP登陆成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 减少方法的参数的数量
     */
    @Getter
    @ToString
    public static class FTPHelper {

        private String host;
        private int port = 21;//默认端口
        private String user = "anonymous";//默认用户
        private String password = null;
        private String workingDirectory = "/";//默认路径
        private String targetFile;//待上传或者下载的文件
        private InputStream inStream;
        private String downloadDir;//下载文件存放路径

        private FTPHelper() {
        }

        public static FTPHelper builder() {
            return new FTPHelper();
        }

        public FTPHelper setHost(String host) {
            this.host = host;
            return this;
        }


        public FTPHelper setPort(int port) {
            this.port = port;
            return this;
        }

        public FTPHelper setUser(String user) {
            this.user = user;
            return this;
        }

        public FTPHelper setPassword(String password) {
            this.password = password;
            return this;
        }

        public FTPHelper setWorkingDirectory(String workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        public FTPHelper setTargetFile(String targetFile) {
            this.targetFile = targetFile;
            return this;
        }

        public FTPHelper setInStream(InputStream inStream) {
            this.inStream = inStream;
            return this;
        }

        public FTPHelper setDownloadDir(String downloadDir) {
            this.downloadDir = downloadDir;
            return this;
        }
    }

}
