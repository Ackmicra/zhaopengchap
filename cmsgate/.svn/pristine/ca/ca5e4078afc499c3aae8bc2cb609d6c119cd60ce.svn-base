package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe 京东HTTP文件上传实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 下午1:46:22
 * 
 */
@Data
@ConvertBean
public class JDLoanfileUpload {
	
	@NotBlank
	private String applicationNo;    //申请号
	@NotEmpty
	@Valid
	private List<JDFile> files;        //文件列表
	
}
