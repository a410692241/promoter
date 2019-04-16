package com.linayi.timer;

import com.linayi.entity.correct.Correct;
import com.linayi.enums.CorrectStatus;
import com.linayi.service.correct.CorrectService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
public class PriceAffectTimer extends QuartzJobBean {
    @Autowired
    private CorrectService correctService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Method:价格生效方法开始！");
        Correct correct = new Correct();
        correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
        //查询审核通过的数据
        List<Correct> list = correctService.getCorrect(correct);
        if(list.size()>0){
            for(Correct corrects : list){
                correctService.priceAffect(corrects);
            }
        }
        System.out.println("Method:价格生效结束！！！");
    }



}
