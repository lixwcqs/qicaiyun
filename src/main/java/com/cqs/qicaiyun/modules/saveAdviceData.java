package com.cqs.qicaiyun.modules;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static com.sun.xml.internal.fastinfoset.util.ValueArray.MAXIMUM_CAPACITY;

/**
 * Created by cqs on 2018/4/21.
 */
@Log4j2
@Component
public class saveAdviceData {


    //    @Resource
//    private AdviceService adviceService;
//
//    public static void main(String[] args) throws IOException {
//        saveAdviceData obj = new saveAdviceData();
//        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
//        String file  = "F:/data/sample_train2.csv";
//        obj.readSaveData(file);
//    }
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] weights = {2, 10, 100, 1000};//必须有序
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
        }
        Random r = new Random(sum);
        final int times = 1000000;
        int times2 = times;
        Map<Integer, Integer> map = new TreeMap<>();
        while (--times2 >= 0) {
            int sw = 0;
            for (int i = weights.length - 1; i >= 0; --i) {
                sw += weights[i];
                if ((1-r.nextDouble())*sum <= sw) {
                    map.put(i, map.getOrDefault(i, 0) + 1);
                    break;
                }
            }
        }

        map.forEach((k, v) -> {
            System.out.println("key:" + k + "\t" + v);
            System.out.println("key:" + k + "\t" + v * 1.0 / times);
        });

    }

}
