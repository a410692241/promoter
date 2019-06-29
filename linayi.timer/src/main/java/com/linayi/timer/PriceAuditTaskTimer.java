package com.linayi.timer;

import com.linayi.entity.correct.Correct;
import com.linayi.service.correct.CorrectService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


import java.util.List;

public class PriceAuditTaskTimer extends QuartzJobBean {
    Logger logger = LoggerFactory.getLogger(PriceAuditTaskTimer.class);

    @Autowired
    private CorrectService correctService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("PriceAffectTimerMethod:START");
        System.out.println("价格审核任务开始");
        correctService.priceAudit();
        System.out.println("价格审核任务结束");
        logger.info("PriceAffectTimerMethod:END");
    }
}
