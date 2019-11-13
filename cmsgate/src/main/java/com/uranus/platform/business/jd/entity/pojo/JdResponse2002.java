package com.uranus.platform.business.jd.entity.pojo;

import lombok.Data;

/**
 * @Describe JD审核结果回调接口响应数据
 * @author  wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月21日 下午2:52:01
 * 
 */
@Data
public class JdResponse2002 {
	private String  applicationNo;    //申请号
	private String  auditResult;      //审批结果
	private String  auditRejectReason;//审批拒绝原因 
	private String  auditDate;        //审核时间

}
