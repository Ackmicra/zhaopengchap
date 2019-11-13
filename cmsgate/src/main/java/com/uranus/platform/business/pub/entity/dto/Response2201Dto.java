package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class Response2201Dto {

	private Integer dataCnt;
	private List<Response2201ListDto> response2201List;
}
