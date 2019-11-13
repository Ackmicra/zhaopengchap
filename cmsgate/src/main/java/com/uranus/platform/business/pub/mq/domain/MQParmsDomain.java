package com.uranus.platform.business.pub.mq.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description: MQ任务参数类
 * @author zhaopengchao@dhcc.com.cn
 * @Date 2019年8月27日上午10:57:31
 */
@Data
public class MQParmsDomain implements Serializable{
	
	/**  
	* @Fields field:field:{todo}
	*/  
	private static final long serialVersionUID = 1L;
	
	
	/**  
	* @Fields 任务类型
	*/  
	private String taskType;
	
	/**  
	* @Fields 消息延时级别
	*/  
	private int delayLevel;
	
	/**  
	* @Fields 任务数据实体
	*/  
	private Object taskData;
}
