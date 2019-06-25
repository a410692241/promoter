package com.linayi.service.community.impl;

import com.linayi.enums.SmallCommunityReqType;
import com.linayi.service.community.SmallCommunityReqService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.linayi.dao.community.SmallCommunityReqMapper;
import com.linayi.entity.community.SmallCommunityReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SmallCommunityReqServiceImpl implements SmallCommunityReqService {

    @Resource
    private SmallCommunityReqMapper smallCommunityReqMapper;


    @Override
    @Transactional
    public void addSmallCommunityReq(SmallCommunityReq smallCommunityReq) {
        //设置操作记录
        smallCommunityReq.setCreateTime(new Date());
        smallCommunityReq.setStatus(SmallCommunityReqType.NOTVIEWED.name());
        smallCommunityReqMapper.insert(smallCommunityReq);
    }

    @Override
    public List<SmallCommunityReq> getSmallCommunityReqList(SmallCommunityReq smallCommunityReq) {
        return smallCommunityReqMapper.selectByAll(smallCommunityReq);
    }

    @Override
    @Transactional
    public void updateStatus(SmallCommunityReq smallCommunityReq) {
        smallCommunityReqMapper.updateByPrimaryKeySelective(smallCommunityReq);
    }

    @Override
    public SmallCommunityReq get(SmallCommunityReq smallCommunityReq) {
        return smallCommunityReqMapper.selectByPrimaryKey(smallCommunityReq.getSmallCommunityReqId());
    }

    @Transactional
    @Override
    public void batchUpdateStatus(List<Integer> idList) {
        for (Integer smallCommunityReqId : idList) {
            SmallCommunityReq record = new SmallCommunityReq();
            record.setSmallCommunityReqId(smallCommunityReqId);
            record.setStatus(SmallCommunityReqType.PROCESSED.name());
            smallCommunityReqMapper.updateByPrimaryKeySelective(record);
        }
    }
}
