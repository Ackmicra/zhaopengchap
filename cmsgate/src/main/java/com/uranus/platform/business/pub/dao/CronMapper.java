package com.uranus.platform.business.pub.dao;

import org.apache.ibatis.annotations.Select;

public interface CronMapper {
	
	@Select("select cron from cron WHERE ROWNUM =1")
	public String getCron();
}
