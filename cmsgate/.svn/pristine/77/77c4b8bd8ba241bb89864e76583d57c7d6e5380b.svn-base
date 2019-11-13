package com.uranus.platform.business.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.uranus.platform.business.pub.dao.CronMapper;
import com.uranus.tools.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 动态定时任务
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月22日上午11:12:07
 */
//@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
@Slf4j
public class DynamicScheduleTask implements SchedulingConfigurer{

    @Autowired      //注入mapper
    CronMapper cronMapper;

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                	log.info("####################定时任务开始执行######################");
                	log.info("执行动态定时任务: " + DateUtils.nowDateFormat());
                	log.info("####################定时任务执行接收######################");
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                    	log.info("####################定时任务未配置######################");
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
