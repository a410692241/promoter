package com.linayi.timer;

import com.linayi.entity.correct.Correct;
import com.linayi.enums.CorrectStatus;
import com.linayi.service.correct.CorrectService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;
public class PriceAffectTimer extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(PriceAffectTimer.class);

    @Autowired
    private CorrectService correctService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("PriceAffectTimerMethod:START");
        Correct correct = new Correct();
        Date date = new Date();
        correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
        correct.setActualStartTime(date);
        //查询审核通过的数据
        List<Correct> list = correctService.getCorrect(correct);
        System.out.println("priceAffect totalNums=" + list.size());
        if(list.size()>0){
            for(Correct corrects : list){
                try {
                    correctService.priceAffect(corrects);
                } catch (Exception e) {

                }
            }
        }
        logger.info("PriceAffectTimerMethod:END");
    }



}
