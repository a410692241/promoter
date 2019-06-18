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

import java.util.Calendar;
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
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY,0);
        ca.set(Calendar.MINUTE,0);
        ca.set(Calendar.SECOND,0);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = ca.getTime();
        correct.setStatus(CorrectStatus.AFFECTED.toString());
        correct.setEndTime(endTime);
        Calendar cas = Calendar.getInstance();
        cas.set(Calendar.HOUR_OF_DAY,0);
        cas.set(Calendar.MINUTE,0);
        cas.set(Calendar.SECOND,0);
        cas.add(Calendar.DAY_OF_MONTH, 0);
        Date startTime = cas.getTime();
        correct.setStartTime(startTime);
        System.out.println(correct.getStartTime());
        System.out.println(correct.getEndTime());
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
