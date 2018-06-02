package com.cqs.qicaiyun.common.datastructure.ds;

import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 缓存策略替换算法
 * <p>
 * create date: 18-5-31 21:45
 */
public class CacheAlgorithms {
    private static Random r = new Random(20);

    interface Cache {
        void put(int i);
    }

    static class LRU implements Cache {

        private final static int capacity = 10;

        private int[] queue = new int[capacity];

        private int used = 0;

        //并发
        @Override
        public void put(int i) {
            //if
            for (int j = 0; j < used; j++) {
                if (queue[j] == i) {
                    delete(j);
                    break;
                }
            }
            if (used == capacity) {
                delete(0);
            }
            queue[used++] = i;
//            if (exists) {
//                System.out.println("更新缓存:" + i);
//            } else {
//                System.out.println("新增检点:" + i);
//            }
//            System.out.print("掺入:" + i + "\t\t");
//            print();
        }

        private void print() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < used; i++) {
                sb.append(queue[i] + ", ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            System.out.println("缓存中元素:" + sb);
        }

        private void delete(int idx) {
            for (int i = idx; i < used - 1; i++) {
                queue[i] = queue[i + 1];
            }
            used--;
        }

        public static void main(String[] args) {
            LRU lru = new LRU();
            for (int i = 0; i < 100; i++) {
                lru.put(r.nextInt(20));
            }
        }
    }

    private static class LFU implements Cache {
        private class Node implements Comparable<Node> {
            int key;
            int frequency;

            public Node(int key, int frequency) {
                this.key = key;
                this.frequency = frequency;
            }

            @Override
            public int compareTo(Node o) {
                return o.frequency - this.frequency;
            }

            @Override
            public String toString() {
                return key + ":" + frequency;
            }
        }

        private final static int capacity = 10;
        private int used = 0;
        Node[] nodes = new Node[capacity];

        //根据频率替换
        @Override
        public void put(int element) {
            int index = -1;
            if (used == 0) {
                nodes[used++] = new Node(element, 1);
            } else {
                index = index(element);
                Node node = (index != -1) ? nodes[index] : null;
                if (node == null) {
                    if (used >= capacity) {
                        used = capacity - 1;
//                        System.out.println("remove element:" + nodes[capacity - 1]);
                    }
                    nodes[used++] = new Node(element, 1);
                } else {
                    node.frequency++;
                    resort(index);
                }
            }
//            System.out.println(index + "\t" + element + "\t" + this);
        }

        //////
        private void resort(int idx) {
            if (idx == 0) return;
            Node node = nodes[idx];
            int p = idx - 1;
            while (p >= 0 && nodes[p].frequency < node.frequency) {
                --p;
            }
            p++;
            if (p != idx) {
                Node tmp = nodes[p];
                nodes[p] = nodes[idx];
                nodes[idx] = tmp;
            }
        }

        private int index(int element) {
            for (int i = 0; i < used; i++) {
                if (nodes[i].key == element) {
                    return i;
                }
            }
            return -1;
        }

        private Node getNode(int i) {
            return index(i) == -1 && i >= used ? null : nodes[i];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < used; i++) {
                sb.append(nodes[i] + ", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            return "LFU{" +
                    "used=" + used +
                    ", nodes=" + sb +
                    '}';
        }

        public static void main(String[] args) {
            LFU lfu = new LFU();
            Random r = new Random();
            for (int i = 0; i < 100; i++) {
                lfu.put(r.nextInt(16));
            }
        }
    }

    class FIFO implements Cache {
        private final static int capacity = 10;
        private Queue<Integer> queue = new ArrayBlockingQueue<>(capacity);

        private int used = 0;

        @Override
        public void put(int i) {
            if (!queue.contains(i)) {
                if (used == capacity) {
                    //队列首部先删除
                    queue.remove();
                }
                queue.add(i);
            }
        }
    }

    class LIFO implements Cache {
        private final static int capacity = 10;
        private final Stack<Integer> stack = new Stack<>();

        private int used = 0;

        @Override
        public void put(int i) {
            if (!stack.contains(i)) {
                if (used == capacity) {
                    stack.pop();
                }
                stack.push(i);
                used++;
            }
        }
    }
}
