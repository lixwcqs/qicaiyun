package com.cqs.qicaiyun.common.cvs;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cqs on 2018/4/21.
 */
@Log4j2
public class CvsSegment {


    private static int SIZE = 100;
    private static int COLUMNS = 8;//有多少列
    private static ExecutorService exec;
    private static int fnum;
    private static int tnum;
    private static String dir = "F:/data/";
//    private static String fName = "sample_train";
    private static String fName = "train";
    private static String subir = dir + "small_" + fName + "/";
    private static String suffix = ".csv";
    private static String header = "";

    public static void main(String[] args) throws IOException{
        for (int i = 1000; i < 2000; i++) {
            File file = new File(subir + i + suffix);
            if (file.exists()){
                file.delete();
            }
        }
//        test();
//        String file = subir+"1.csv";
//        RandomAccessFile aftfile = new RandomAccessFile(file, "rw");
//        System.out.println(aftfile.length()+"\t"+aftfile.getFilePointer());
////        aftfile.seek(0);
//        aftfile.write("45400,".getBytes());
//        System.out.println(aftfile.length()+"\t"+aftfile.getFilePointer());
//        aftfile.close();
    }

    private static void test() {
        long flength = 0;
        try {
            RandomAccessFile rf0 = new RandomAccessFile(dir + fName + suffix, "r");
            flength = rf0.length();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
        int size = 200 << 10;
        long bsize = size <<10;
        splitBySize(size);
        fnum = (int) Math.ceil((flength / (bsize)));
//        just();
//        String s = deleteLastLine(subir + "0-0" + suffix);
//        log.info(s);
    }

    /**
     * @param size 单位KB
     */
    private static void splitBySize(int size) {
        if (!new File(subir).exists()) {
            new File(subir).mkdirs();
        }

        long flength = 0;
        try {
            RandomAccessFile rf0 = new RandomAccessFile(dir + fName + suffix, "r");
            flength = rf0.length();
            header = rf0.readLine();
            IOUtils.closeQuietly(rf0);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
        try {
            //转化为byet
            long bsize = size << 10;
            fnum = (int) Math.ceil((flength / (bsize)));
            tnum = Math.min(fnum, 8);
            log.info("fnum:" + fnum + "\ttnum:" + tnum);
            exec = Executors.newFixedThreadPool(tnum);
            CountDownLatch latch = new CountDownLatch(tnum);
            //N个线程
            for (int i = 0; i < tnum; i++) {
                final int j = i;
                Runnable r = () -> {
                    int no = j;
                    try {
                        RandomAccessFile rf = new RandomAccessFile(dir + fName + suffix, "r");
                        byte[] buf = new byte[1024];
                        while (no < fnum) {
                            log.info("新建文件" + (subir + no + suffix) + "\t读取开始位置:" + (no * bsize));
                            RandomAccessFile f = new RandomAccessFile(subir + no + suffix, "rw");
                            rf.seek(no * bsize);
                            int read = 0;
                            //写一个文件
                            while ((read = rf.read(buf)) != 0 && f.length() < bsize) {
                                f.write(buf, 0, read);
                            }
                            f.close();
                            no += tnum;
                            log.info("子文件" + (subir + no + suffix) + "分割完成，进入分割至下一个子文件");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    log.info("子线程工作完毕，退出。");
                    latch.countDown();
                };
                exec.submit(r);
            }
            latch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (exec != null) {
                exec.shutdown();
            }
        }

        just();
    }

    private static void just() {
        //调整
        for (int i = 0; i < fnum - 1; i++) {
            RandomAccessFile prefile = null;
            RandomAccessFile aftfile = null;
            String pf = subir + i + suffix;
            String af = subir + (i + 1) + suffix;
            try {
                prefile = new RandomAccessFile(pf, "rw");
                aftfile = new RandomAccessFile(af, "rw");
                String preLastLine = lastLine(pf);
                log.info(pf+"\t"+preLastLine);
                //需要合并
                if (StringUtils.isNotEmpty(preLastLine) && preLastLine.split(",").length != COLUMNS) {
                    //将preLastLine和aftfile文件的第一行拼接
                    aftfile.seek(0);
                    aftfile.write(preLastLine.getBytes());
                    aftfile.close();
                    aftfile = new RandomAccessFile(af, "rw");
                    //校验是否合法
                    aftfile.seek(0);
                    String line = aftfile.readLine();
                    if (line.split(",").length!=COLUMNS){
                        throw new RuntimeException(af+"修正文件异常: "+line+"\t---"+preLastLine);
                    }
                    //prefile删除最后一行
                    deleteLastLine(pf);

                }
                //加入头文件
                aftfile.seek(0);
                aftfile.write(header.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (prefile != null) {
                    try {
                        prefile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (aftfile != null) {
                    try {
                        aftfile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static String lastLine(String file) {
        try {
            long pos = lastLinePointer(file);
            RandomAccessFile f = new RandomAccessFile(file, "r");
            f.seek(pos);
            return f.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long lastLinePointer(String file) {
        //调整
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(file, "r");
            long pos = f.length();
            while (--pos >= 0) {
                f.seek(pos);
                try {
                    int c = f.read();
//                    log.info((char)c);
                    if (c == '\n') {
//                        log.error("pos: " + pos + "\t" + f.length());
                        return f.getFilePointer();
                    }
                } catch (IOException e) {
                    log.error("pos: " + pos + "\t" + f.length());
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
            log.info("读取文件{}最后一行失败", file, e.getMessage());
        } finally {
            IOUtils.closeQuietly(f);
        }
        return -1;
    }

    private static String deleteLastLine(String file) {
        RandomAccessFile rf = null;
        try {
            log.info(file);
            rf = new RandomAccessFile(file, "rw");
            final long pos = lastLinePointer(file);
//            log.info(pos+"\t"+rf.length());
            rf.seek(pos);
            String result = rf.readLine();
            rf.setLength(pos-1);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(rf);
        }
        return null;
    }

    private static void splitLineByLine() {
        try {
//            RandomAccessFile rf = new RandomAccessFile("F:/data/train.cvs", "r");
            String dir = "F:/data/";
            String fName = "sample_train";
            String subir = "F:/data/small_" + fName + "/";
            if (!new File(subir).exists()) {
                new File(subir).mkdirs();
            }
            String suffix = ".csv";
            RandomAccessFile rf = new RandomAccessFile(dir + fName + suffix, "r");
            //去掉头
            final String head = rf.readLine();
            String line = "";
            //多线程同时读取
            long count = 0;
            RandomAccessFile file = new RandomAccessFile(subir + fName + "-" + count / SIZE + suffix, "rw");
            String lineBreak = "\r\n";
            while (!(line = lineBreak + rf.readLine()).equals(lineBreak)) {
                if (++count % SIZE == 1) {
                    file.close();
                    file = new RandomAccessFile(subir + fName + "-" + count / SIZE + suffix, "rw");
                    file.writeBytes(head);
                }
                file.writeBytes(line);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
