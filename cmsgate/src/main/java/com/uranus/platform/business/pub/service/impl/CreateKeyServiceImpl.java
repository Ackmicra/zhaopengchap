package com.uranus.platform.business.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uranus.platform.business.pub.dao.CreateKeyMapper;
import com.uranus.platform.business.pub.service.CreateKeyService;

@Service
public class CreateKeyServiceImpl implements CreateKeyService {

	@Autowired
	private CreateKeyMapper createKeyMapper;
	
	@Override
	public String getJdTradeNo() {
		String jdTradeNo = createKeyMapper.getJdTradeNo();
		return jdTradeNo;
	}

	@Override
	public String getBatchNoNo() {
		String batchNo = createKeyMapper.getBatchNoNo();
		return batchNo;
	}

	@Override
	public String getJdBatchNo() {
		String batchNo = createKeyMapper.getJdBatchNo();
		return batchNo;
	}

}
