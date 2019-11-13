package com.uranus.platform.utils.aop.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.po.WsBaseData;
import com.uranus.platform.business.pub.service.WsBaseService;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 请求京东时利用AOP插入WS_BASE表日志类
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月20日下午3:34:58
 */
@Aspect
@Component
@Slf4j
public class WsBaseLogAspect {

	@Autowired
	private WsBaseService wsBaseService;
	//Controller层切点
    @Pointcut("@annotation(com.uranus.platform.utils.aop.log.WsBaseLog)")
    public void controllerAspect() {
    	
    }
    
    //环绕通知
    @Around("controllerAspect()")
    public JdResponseView around(ProceedingJoinPoint joinPoint) throws Throwable {
    	JdResponseView jdResponseView = null;
    	try {
    		Object[] arguments = joinPoint.getArgs();
    		String begTime= DateUtils.nowTimeFormat();
    		jdResponseView = (JdResponseView)joinPoint.proceed();
    		String endTime= DateUtils.nowTimeFormat();
    		WsBaseData wsBase = new WsBaseData(DateUtils.nowDateFormat(), begTime, "", CmsBusinessStatus.JD.businessCode(), 
    				jdResponseView.getTradeNo(), endTime, jdResponseView.getCode(), jdResponseView.getMsg(), CmsBusinessStatus.PROCESS.businessCode(), 
    				(String) arguments[1], (String) arguments[2], jdResponseView.getBizContent());
			wsBaseService.insert(wsBase);
		} catch (Throwable e) {
			throw e;
		}
    	return jdResponseView;
    }
    
    //异常拦截抛出后执行
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "ex")
    public void aroundThrowing(JoinPoint joinPoint, Throwable ex) {
    	try {
    		Object[] arguments = joinPoint.getArgs();
    		String sysTime= DateUtils.nowTimeFormat();
    		WsBaseData wsBase = new WsBaseData(DateUtils.nowDateFormat(), sysTime, "", CmsBusinessStatus.JD.businessCode(), 
    				"", sysTime, CmsBusinessStatus.BUSINESS_FAILURE.businessCode(), 
    				CmsBusinessStatus.BUSINESS_FAILURE.businessMessage(), CmsBusinessStatus.PROCESS.businessCode(), 
    				(String) arguments[1], (String) arguments[2], ex.getMessage());
			wsBaseService.insert(wsBase);
    		log.info(">>>>>>>>>>>>>>>>>>>"+ex.getMessage()+">>>>>>>>>>>>>>>>>>>");
		} catch (Throwable e) {
			throw e;
		}
    }
}
