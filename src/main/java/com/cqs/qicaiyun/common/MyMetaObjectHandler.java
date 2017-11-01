package com.cqs.qicaiyun.common;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自定义填充处理器
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("uTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("cTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public boolean openUpdateFill() {
        return true;
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 关闭更新填充、这里不执行
        this.setFieldValByName("uime", LocalDateTime.now(), metaObject);
    }
}
