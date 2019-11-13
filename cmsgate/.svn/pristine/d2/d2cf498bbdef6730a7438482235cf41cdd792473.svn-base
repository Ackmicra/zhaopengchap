package com.uranus.platform.business.jd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uranus.platform.business.jd.dao.JdAccountDataMapper;
import com.uranus.platform.business.jd.entity.po.JdAccountData;
import com.uranus.platform.business.jd.service.JdAccountService;

@Service
public class JdAccountServiceImpl implements JdAccountService {

	@Autowired
	private JdAccountDataMapper jdAccountDataMapper;
	
	@Override
	public boolean insert(JdAccountData jdAccountData) {
		return jdAccountDataMapper.insert(jdAccountData) > 0;
	}

}
