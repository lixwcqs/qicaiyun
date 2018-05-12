package com.cqs.qicaiyun.modules.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cqs.qicaiyun.modules.entity.Advice;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Scope("prototype")
public interface AdviceMapper extends BaseMapper<Advice>  {

    boolean insertBatch(List<Advice> list);
}