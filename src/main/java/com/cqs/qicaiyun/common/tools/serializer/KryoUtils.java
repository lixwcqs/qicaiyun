package com.cqs.qicaiyun.common.tools.serializer;

import com.cqs.qicaiyun.common.tools.ThreadPoolUtils;
import com.cqs.qicaiyun.mock.UserMock;
import com.cqs.qicaiyun.system.entity.User;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

/**
 * 参考 https://github.com/EsotericSoftware/kryo/blob/master/README.md
 * <p>
 * <p>
 * <p>
 * Created by cqs on 2017/11/12.
 */
@Log4j2
public class KryoUtils {


    private final static int BUFFER_SIZE = 1024 * 1024;//序列化对象不能超过 BUFFER_SIZE Byte

    //线程绑定
    private final static ThreadLocal<Kryo> local = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        System.out.println(Thread.currentThread().getId() + "\t" + kryo);
        return kryo;
    });


    //序列化
    public <T extends Serializable> byte[] serialize(T entity) {

        Assert.notNull(entity, "序列化对象不能为null");
        Output ot = new ByteBufferOutput(BUFFER_SIZE);
        byte[] result;
        try {
            Kryo kryo = local.get();
            //If the class is known and the object could be null:
//            kryo.writeObjectOrNull(ot, entity,entity.getClass());
            //If the class is known and the object cannot be null:
            kryo.writeObject(ot, entity);
            ot.flush();
            result = ot.toBytes();
        } finally {
            ot.close();
        }
        return result;
    }

    public <T extends Serializable> byte[] serializeNullable(T entity, Class<T> type) {

        Output ot = new ByteBufferOutput(BUFFER_SIZE);
        byte[] result;
        try {
            Kryo kryo = local.get();
            //If the class is known and the object could be null:
            kryo.writeObjectOrNull(ot, entity, type);
            ot.flush();
            result = ot.toBytes();
        } finally {
            ot.close();
        }
        return result;
    }

    public byte[] serializeObj(Object entity) {

        Output ot = new ByteBufferOutput(BUFFER_SIZE);
        byte[] result;
        try {
            Kryo kryo = local.get();
            //If the concrete class of the object is not known and the object could be null:
            kryo.writeClassAndObject(ot, entity);
            ot.flush();
            result = ot.toBytes();
        } finally {
            ot.close();
        }
        return result;
    }

    public <T> T deserialize(byte[] bytes, Class<T> type) {
        Input input = new ByteBufferInput(bytes);
        //If the class is known and the object could be null:
        return local.get().readObject(input, type);
    }

    public <T> T deserializeNullable(byte[] bytes, Class<T> type) {
        Input input = new ByteBufferInput(bytes);
        //If the class is known and the object could be null:
        return local.get().readObjectOrNull(input, type);
    }

    public Object deserializeObj(byte[] bytes) {
        Input input = new ByteBufferInput(bytes);
        return local.get().readClassAndObject(input);
    }

    public static void main(String[] args) {
        ExecutorService instance = ThreadPoolUtils.getInstance();
        for (int i = 0; i < 2; i++) {
            instance.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 2; i++) {
                            KryoUtils kryoUtils = new KryoUtils();
                            byte[] bytes = kryoUtils.serializeNullable(null, User.class);
                            User user1 = kryoUtils.deserializeNullable(bytes, User.class);
                            byte[] bytes2 = kryoUtils.serializeObj(UserMock.newUser());
                            Object user2 = kryoUtils.deserializeObj(bytes2);

                            System.out.println(user1 + "\t" + user2);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        instance.shutdown();
    }

}
