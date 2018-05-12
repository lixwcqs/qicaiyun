package com.cqs.tool;

public class Test {
    //几个相同的
    static int N = 4;
    //唯一的数字
    static int RESULT = 53246246;

    public static void main(String[] args) {
        int[] ns = new int[N * 1000 + 1];
        int k = 1;
        for (int i = 0; i < ns.length - 1; i=i+N) {
            for (int i1 = 0; i1 < N; i1++) {
                ns[i+i1]=k;
            }
            k++;
        }
        ns[ns.length - 1] = RESULT;
        System.out.println(find(ns));
        System.out.println(3>>1);
    }

    public static int find(int[] ns) {
        int result = 0;
        int sum;
        int i, j;
        //int 类型32位
        for (i = 0; i < 32; i++) {
            //统计1的个数
            sum = 0;
            for (j = 0; j < ns.length; j++)
                //(ns[j] >> i) & 1表示ns[j]第j为0还是1
                sum += ((ns[j] >> i) & 1);

            //要是出现sum不是N的倍数 那一定是只有一个的那个数RESULT导致
            //且第i位数一定是1 反之若sum % N ==0 那么第i位数一定是0
            if (sum % N == 1)
                //RESULT的第位一定是1
                result = result | (1 << i);
        }
        return result;
    }
}