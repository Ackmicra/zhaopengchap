package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JDRequest2110 {
	
	@NotBlank
	private String applicationNo;
	@NotBlank
	private String fileCode;
}
