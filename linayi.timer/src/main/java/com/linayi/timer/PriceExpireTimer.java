package com.linayi.timer;

import com.linayi.entity.correct.Correct;
import com.linayi.enums.CorrectStatus;
import com.linayi.service.correct.CorrectService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.util.Date;
import java.util.List;

public class PriceExpireTimer extends QuartzJobBean implements Job {

    Logger logger = LoggerFactory.getLogger(PriceExpireTimer.class);

    @Autowired
    private CorrectService correctService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("PriceExpireTimerMethod:START");
        Correct correct = new Correct();
        correct.setStatus(CorrectStatus.AFFECTED.toString());
        correct.setEndTime(new Date());
        //查询审核通过的数据
        List<Correct> list = correctService.getCorrectExpire(correct);
        System.out.println("priceExpire totalNums=" + list.size());
        if (list.size() > 0) {
            for (Correct corrects : list) {
                try {
                    correctService.priceExpire(corrects);
                } catch (Exception e) {
                    System.out.println("priceExpire has Exception correct_id=" + corrects.getCorrectId());
                    e.printStackTrace();
                }
            }
        }
        logger.info("PriceExpireTimerMethod:END");
    }

}
