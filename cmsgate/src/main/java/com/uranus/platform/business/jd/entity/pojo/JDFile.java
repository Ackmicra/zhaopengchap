package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe 京东HTTP文件上传List文件信息实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月20日 下午1:50:50
 * 
 */
@Data
@ConvertBean
public class JDFile {
	@NotBlank
	private String type;        //文件类型
	@NotBlank
	private String fileName;    //文件名
	@NotBlank
	private String content;     //文件内容

}
