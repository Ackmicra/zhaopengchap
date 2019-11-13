package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JDTransferPlansRequset {

	@NotBlank
	private String batchNum;
}
