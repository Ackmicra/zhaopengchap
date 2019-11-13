package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe 京东审核结果查询接口请求数据实体类
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月21日 上午10:34:02
 * 
 */
@Data
@ConvertBean
public class JDLoanQuery {
	  @NotBlank
	  private String      applicationNo    ;        //    申请号         
}
