package com.cqs.qicaiyun.modules.loop;

/**
 * Created by cqs on 2018/5/9.
 */
public class Single {

    private Single (){
        System.out.println("实例化了");
    }

    static {
        System.out.println("single");
    }

  public static Single getInstance(){
        return Builder.single;
    }


    private static class Builder {

        public Builder() {
            System.out.println("初始化了");
        }

        private final static Single single  = new Single();
    }
}
