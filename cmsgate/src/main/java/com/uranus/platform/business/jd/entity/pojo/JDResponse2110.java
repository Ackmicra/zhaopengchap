package com.uranus.platform.business.jd.entity.pojo;

import java.util.List;

import lombok.Data;

@Data
public class JDResponse2110 {

	private List<String> errorMessages;
	private String applicationNo; // 申请号
	private String fileType; // 文件类型PDF或WORD
	private String transferType; // 传输方式S:使用sftp; P:使用数据包
	private String contractFile; //文件地址  传输方式为S时必须上送,表示合同文件存在SFTP的绝对路径
	private String contractContent;// 文件内容  传输方式为P时必须上送,表示合同文件的具体内容,将文件字节流进行Base64转码
	/**
	 * 合同文件的签章状态:
			0=未知状态
			资金渠道自主生成合同相关状态:
			1=未生成合同
			2=生成合同成功
			3=生成合同失败,
			资金渠道自主完成签章相关状态:
			4=合同签章成功
			5=合同签章失败,
			8=签章处理中
	 */
	private String signStatus; //签章状态 
}
