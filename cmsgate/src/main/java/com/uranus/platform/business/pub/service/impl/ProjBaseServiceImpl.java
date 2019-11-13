package com.uranus.platform.business.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.uranus.platform.business.pub.dao.ProjBaseDataMapper;
import com.uranus.platform.business.pub.entity.po.ProjBaseData;
import com.uranus.platform.business.pub.service.ProjBaseService;

@Service
public class ProjBaseServiceImpl implements ProjBaseService {

	@Autowired
	private ProjBaseDataMapper projBaseMapper;
	@Override
	@Cacheable(cacheNames= "projBaseCatche",key="#projNo")
	public ProjBaseData getByProjNo(String projNo) {
		ProjBaseData projBaseData = projBaseMapper.getByProjNo(projNo);
		return projBaseData;
	}

}
