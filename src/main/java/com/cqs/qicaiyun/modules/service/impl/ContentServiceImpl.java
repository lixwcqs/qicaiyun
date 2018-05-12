package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.mapper.ContentMapper;
import com.cqs.qicaiyun.modules.service.ContentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by cqs on 2018/4/21.
 */
@Service("contentServiceImpl")
@Log4j2
public class ContentServiceImpl extends ServiceImpl<ContentMapper,Content> implements ContentService{


}
