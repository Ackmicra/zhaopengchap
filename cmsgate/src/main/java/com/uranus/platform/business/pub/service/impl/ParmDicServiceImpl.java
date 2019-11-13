package com.uranus.platform.business.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.uranus.platform.business.pub.dao.ParmDicDataMapper;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.service.ParmDicService;

@Service
public class ParmDicServiceImpl implements ParmDicService{

	@Autowired
	private ParmDicDataMapper parmDicDataMapper;
	
	@Override
	@Cacheable(cacheNames="parmDicCatche",key="#keyName+'_'+#optCode") 
	public ParmDicData getParmDic(String keyName, String optCode) {
		ParmDicData parmDicData = new ParmDicData();
		parmDicData.setKeyName(keyName);
		parmDicData.setOptCode(optCode);
		parmDicData = parmDicDataMapper.getByKeyNameOptCode(parmDicData);
		return parmDicData;
	}

	
	@Override
	@Cacheable(cacheNames= "parmDicCatche",key="#keyId")
	public ParmDicData selectByPrimaryKey(String keyId) {
		ParmDicData parmDicData = parmDicDataMapper.selectByPrimaryKey(keyId);
		return parmDicData;
	}


	@Override
	@CacheEvict(cacheNames = "parmDicCatche",allEntries=true)
	public void deleteParmDic(String keyName, String optCode) {
		ParmDicData parmDicData = new ParmDicData();
		parmDicData.setKeyName(keyName);
		parmDicData.setOptCode(optCode);
		parmDicData = parmDicDataMapper.deleteParmDic(parmDicData);
	}
	
	@Override
	@CachePut(cacheNames = "parmDicCatche",key="#parmDicData.keyName+'_'+#parmDicData.optCode")
	public ParmDicData update(ParmDicData parmDicData) {
		parmDicDataMapper.update(parmDicData);
		return  parmDicData;
	}


	@Override
	@Cacheable(cacheNames="parmDicCatche",key="#keyName+'_'+#mateCode") 
	public ParmDicData getParmDicByMateCode(String keyName, String mateCode) {
		ParmDicData parmDicData = new ParmDicData();
		parmDicData.setKeyName(keyName);
		parmDicData.setMateCode(mateCode);
		parmDicData = parmDicDataMapper.getByKeyNameMateCode(parmDicData);
		return parmDicData;
	}

}
