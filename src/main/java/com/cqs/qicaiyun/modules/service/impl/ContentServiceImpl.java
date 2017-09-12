package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Content;
import com.cqs.qicaiyun.modules.mapper.ContentMapper;
import com.cqs.qicaiyun.modules.service.ContentService;
import org.springframework.stereotype.Service;

/**
 * Created by cqs on 2017/8/20.
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper,Content> implements ContentService {
}
