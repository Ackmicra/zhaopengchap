package com.uranus.platform.business.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uranus.platform.business.pub.dao.WsBaseDataMapper;
import com.uranus.platform.business.pub.entity.po.WsBaseData;
import com.uranus.platform.business.pub.service.WsBaseService;

@Service
public class WsBaseServicesImpl implements WsBaseService {

	@Autowired
	private WsBaseDataMapper wsBaseDataMapper;
	
	@Override
	public int insert(WsBaseData wsBaseData) {
		return wsBaseDataMapper.insert(wsBaseData);
	}

}
