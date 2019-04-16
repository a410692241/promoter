package com.linayi.timer;

import com.linayi.entity.correct.Correct;
import com.linayi.enums.CorrectStatus;
import com.linayi.service.correct.CorrectService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class PriceExpireTimer extends QuartzJobBean implements Job {

    @Autowired
    private CorrectService correctService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println("Method:价格过期方法开始！");
            Correct correct = new Correct();
            correct.setStatus(CorrectStatus.AFFECTED.toString());
            //查询审核通过的数据
            List<Correct> list = correctService.getCorrect(correct);
            if(list.size()>0){
                for(Correct corrects : list){
                    correctService.priceExpire(corrects);
                }
            }
            System.out.println("Method:价格过期方法结束！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
