package com.uranus.platform.business.jd.entity.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * @Describe JD贷款申请人用户信息实体类
 * 
 * @author wangshuai0106@dhcc.com.cn
 *
 */

@Data
@ConvertBean
public class JDLoanUser {
	@NotBlank
	private String name; // 姓名
	@NotBlank
	private String ceritificateType; // 证件类型
	@NotBlank
	private String ceritificateNo; // 证件号码
	@NotBlank
	private String effstartdate; // 证件有效期起始日
	private String effenddate; // 证件有效期止
	private String authroity; // 签发机关
	@NotBlank
	private String nation; // 民族
	@NotBlank
	private String job; // 职业
	@NotBlank
	private String telephone; // 联系电话
	@NotBlank
	private String cellphone; // 移动电话
	private String postcode; // 邮政编码
	private String address; // 通讯地址
	private String email; // 邮箱
	@NotBlank
	private String maritalStatus; // 婚姻状况
	@NotBlank
	private String education; // 学历
	private String censusRegister; // 户籍
	private String censusRegisterAddress; // 户籍地址
	@Min(value = 0)
	private Double monthlyIncome; // 个人月收入
	private String homeAddress; // 家庭住址
	private String homePostcode; // 家庭邮编
	private String homeTelphone; // 住宅电话
	private Double userNetAssets; // 家庭净资产
	private Double userHouseValue; // 住房价值
	private Double userHouseArea; // 住房面积
	private Integer realEstateType; // 居住类型

}
