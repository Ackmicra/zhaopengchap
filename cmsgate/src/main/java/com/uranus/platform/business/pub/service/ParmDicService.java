package com.uranus.platform.business.pub.service;

import com.uranus.platform.business.pub.entity.po.ParmDicData;

public interface ParmDicService {
	
	public ParmDicData getParmDic(String keyName, String optCode);
	
	public ParmDicData selectByPrimaryKey(String keyId);
	
	public void deleteParmDic(String keyName, String optCode);
	
	public ParmDicData update(ParmDicData parmDicData);

	public ParmDicData getParmDicByMateCode(String keyName, String mateCode);
}
