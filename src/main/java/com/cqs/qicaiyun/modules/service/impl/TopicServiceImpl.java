package com.cqs.qicaiyun.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cqs.qicaiyun.modules.entity.Topic;
import com.cqs.qicaiyun.modules.mapper.TopicMapper;
import com.cqs.qicaiyun.modules.service.TopicService;
import org.springframework.stereotype.Service;

/**
* Created by cqs on 2017-09-13T22:58:26.503.
*/
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
}
