package com.uranus.platform.business.jd.service.trans;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uranus.platform.business.jd.dao.JdLoanApplyInfoDataMapper;
import com.uranus.platform.business.jd.dao.JdLoanPaymentStepDataMapper;
import com.uranus.platform.business.jd.entity.po.JdAccountData;
import com.uranus.platform.business.jd.entity.po.JdLoanApplyInfoData;
import com.uranus.platform.business.jd.entity.po.JdLoanPaymentStepData;
import com.uranus.platform.business.jd.entity.po.JdLoanUserData;
import com.uranus.platform.business.jd.entity.po.JdRelationUserData;
import com.uranus.platform.business.jd.entity.pojo.JdErrorMessage;
import com.uranus.platform.business.jd.entity.status.JdResponseStatus;
import com.uranus.platform.business.jd.entity.vo.JdResponseView;
import com.uranus.platform.business.pub.entity.dto.LnAcDto;
import com.uranus.platform.business.pub.entity.dto.LnComDto;
import com.uranus.platform.business.pub.entity.dto.LnGageDto;
import com.uranus.platform.business.pub.entity.dto.LnRelDto;
import com.uranus.platform.business.pub.entity.dto.Request2101Dto;
import com.uranus.platform.business.pub.entity.dto.Request2101ListDto;
import com.uranus.platform.business.pub.entity.dto.Response2101Dto;
import com.uranus.platform.business.pub.entity.po.ParmDicData;
import com.uranus.platform.business.pub.mq.domain.MQParmsDomain;
import com.uranus.platform.business.pub.mq.producer.JDMQProducerManager;
import com.uranus.platform.business.pub.service.CreateKeyService;
import com.uranus.platform.business.pub.service.ParmDicService;
import com.uranus.platform.business.ws.process.Request;
import com.uranus.platform.business.ws.process.Response;
import com.uranus.platform.business.ws.service.ProcessService;
import com.uranus.platform.utils.exception.PlatformExceptionFactory;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.utils.DateUtils;

/**
 * @Describe: 向小微2101进件批量接口发送请求，并且向京东返回响应数据
 * @author    wangshuai0106@dhcc.com.cn
 * @Date 创建时间：2019年8月14日 下午6:37:04
 * 
 */
@Service
public class LnPaymentsTrans2101Service {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProcessService processService;
	@Autowired
	private ParmDicService parmDicService;
	@Autowired
	private CreateKeyService createKeyService;
	@Autowired
	private JdLoanApplyInfoDataMapper jdLoanApplyInfoDataMapper;
	@Autowired
	private JdLoanPaymentStepDataMapper jdLoanPaymentStepDataMapper;
	@Autowired
	private JDMQProducerManager jDMQProducerManager;
	
	/**
	 * @Description 拼装报文、请求小微、转换响应
	 * @param tradeNo 交易流水号
	 * @param brNo    合作机构编号
	 * @param projNo  信托项目编号
	 * @param Data    京东数据
	 * @return 京东响应数据、京东业务数据
	 */

	public JdResponseView request2101(String tradeNo, String brNo, String projNo, String prdtNo, Map<String, Object> map, String applicationNo) {
		// 拼发送小微业务数据
		Request request = getRequest2101(tradeNo, brNo, projNo, prdtNo, map);
		// 发送小微系统
		Response response = processService.wsProcess(CmsBusinessStatus.MFS.businessCode(), request); 
		// 转换小微的响应为京东所需响应数据
		JdResponseView jdResponseView = transLnPaymentsResponse(tradeNo, response, applicationNo);

		return jdResponseView;
	}

	/**
	 * @Description: 拼发送小微请求报文
	 * @param brNo
	 * @param projNo
	 * @param mapEntry
	 * @return 小微请求报文
	 */
	@SuppressWarnings("unchecked")
	private Request getRequest2101(String tradeNo, String brNo, String projNo, String prdtNo, Map<String, Object> map) {
		//获取已导入数据库的Data层数据
		JdLoanApplyInfoData jdLoanApplyInfoData = (JdLoanApplyInfoData)map.get("jdLoanApplyInfoData");
		JdLoanUserData jdLoanUserData = (JdLoanUserData)map.get("jdLoanUserData");
		List<JdRelationUserData> jdRelationUserDataList = (List<JdRelationUserData>)map.get("jdRelationUserDataList");
		JdAccountData jdRepayAccountData = (JdAccountData)map.get("jdRepayAccountData");
		List<JdLoanPaymentStepData> jdLoanPaymentStepDataList = (List<JdLoanPaymentStepData>)map.get("jdLoanPaymentStepData");
		//选用域数据取值
		if(jdLoanUserData.getPostcode() == null) {
			jdLoanUserData.setPostcode("000000");
		}
		if(jdLoanUserData.getAddress() == null) {
			jdLoanUserData.setAddress("北京市东城区");
		}
		if(jdLoanUserData.getEmail() == null) {
			jdLoanUserData.setEmail("1111@1111.com");
		}
		if(jdLoanUserData.getCensusRegister() == null) {
			jdLoanUserData.setCensusRegister("1111");
		}
		if(jdLoanUserData.getCensusRegisterAddress() == null) {
			jdLoanUserData.setCensusRegisterAddress("北京市东城区");
		}
		if(jdLoanUserData.getMonthlyIncome() == null) {
			jdLoanUserData.setMonthlyIncome(5000.00);
		}
		if(jdLoanUserData.getHomeAddress() == null) {
			jdLoanUserData.setHomeAddress("北京市东城区");
		}
		if(jdLoanUserData.getHomePostcode() == null) {
			jdLoanUserData.setHomePostcode("000000");
		}
		if(jdLoanUserData.getHomeTelphone() == null) {
			jdLoanUserData.setHomeTelphone("17000000000");
		}
		if(jdLoanUserData.getUserNetAssets() == null) {
			jdLoanUserData.setUserNetAssets(100000.00);
		}
		if(jdLoanUserData.getUserHouseValue() == null) {
			jdLoanUserData.setUserHouseValue(1000000.00);
		}
		if(jdLoanUserData.getUserHouseArea() == null) {
			jdLoanUserData.setUserHouseArea(200.00);
		}
		if(jdLoanUserData.getRealEstateType() == null) {
			jdLoanUserData.setRealEstateType(9);
		}

		List<Request2101ListDto> cblistDtoList = new ArrayList<Request2101ListDto>();
		List<LnRelDto> cblistRelDtoList = new ArrayList<LnRelDto>();
		List<LnAcDto> AcDtoList = new ArrayList<LnAcDto>();
		LnAcDto RepayAcDto = new LnAcDto();
		LnAcDto LoanAcDto = new LnAcDto();
		
		
		//判断关系人信息是否为空(当关系人信息不为空)
		if(jdRelationUserDataList != null) {
		//将Data层数据插入Dto
		// List(Rel)
		LnRelDto cblistRelDto = new LnRelDto();
		for (JdRelationUserData jdRelationUserData : jdRelationUserDataList) {
			//如果必填值没有，则赋予默认值
			if(jdRelationUserData.getName() == null) {
				jdRelationUserData.setName("未知");			
			}
			if(jdRelationUserData.getTelephone() == null) {
				jdRelationUserData.setTelephone("17000000000");			
			}
			cblistRelDto.setRelName(jdRelationUserData.getName());
			cblistRelDto.setRelTel(jdRelationUserData.getTelephone());
//			cblistRelDto.setRelIdno(jdRelationUserData.getCertificateNo());
//			cblistRelDto.setRelTel(jdRelationUserData.getTelephone());
//			cblistRelDto.setRelType(jdRelationUserData.getRelationType());
			//关系人信息选用域数据取值
			if(jdRelationUserData.getEmail() == null) {
				jdRelationUserData.setEmail("1111@111.com");
			}
			if(jdRelationUserData.getMonthlyIncome() == null) {
				jdRelationUserData.setMonthlyIncome(5000.00);
			}
			if(jdRelationUserData.getAddress() == null) {
				jdRelationUserData.setAddress("北京市东城区");
			}

			cblistRelDtoList.add(cblistRelDto);
		}
		
		// List(LoanAcc)放款账户信息(acUse=2)
		JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataList.get(0);

		//较数据库新增字段
		LoanAcDto.setAcUse(CmsBusinessStatus.AC_USE_LOAN.businessCode());
		
//			cblistAcLoanDto.setAcAmt(acAmt);
//			cblistAcLoanDto.setBankSite(bankSite);
//			cblistAcLoanDto.setCvvNo(cvvNo);
//			cblistAcLoanDto.setBankCity(jdLoanPaymentStepData.);
//			cblistAcLoanDto.setBankProv(bankProv);
		
		LoanAcDto.setAcType("11");
		LoanAcDto.setAcno(jdLoanPaymentStepData.getAccountNo());
		LoanAcDto.setAcName(jdLoanPaymentStepData.getAccountName());
		LoanAcDto.setAcAmt(jdLoanPaymentStepData.getPaymentAmount());
//			cblistAcLoanDto.setBankCode(jdLoanPaymentStepData.getBankCode());

		//银行代码字典项转换
		ParmDicData bankCodeDic = parmDicService.getParmDic("BANK_CODE",
				jdLoanPaymentStepData.getBankCode());
		if (bankCodeDic != null) {
			LoanAcDto.setBankCode(bankCodeDic.getMateCode());
		} else {
			LoanAcDto.setBankCode(jdLoanPaymentStepData.getBankCode());
		}
		
		LoanAcDto.setIdType(jdLoanPaymentStepData.getHolderIdType());
		LoanAcDto.setIdNo(jdLoanPaymentStepData.getHolderIdNo());
		LoanAcDto.setPhoneNo(jdLoanPaymentStepData.getHolderMobileNo());
		AcDtoList.add(LoanAcDto);
	
		// List(RepayAcc)还款账户信息(acUse=1)
		JdAccountData JdAccountData = jdRepayAccountData;
//			cblistAcRepayDto.setAcAmt(acAmt);
//			cblistAcRepayDto.setBankSite(bankSite);
//			cblistAcRepayDto.setCvvNo(cvvNo);
//			cblistAcRepayDto.setBankCity(jdLoanPaymentStepData.);
//			cblistAcRepayDto.setBankProv(bankProv);
		
		RepayAcDto.setAcUse(CmsBusinessStatus.AC_USE_REPAY.businessCode());
		RepayAcDto.setAcType("11");
		RepayAcDto.setAcno(JdAccountData.getAccountNo());
		RepayAcDto.setAcName(JdAccountData.getAccountName());
//		RepayAcDto.setBankProv(bankProv);
//		RepayAcDto.setBankCity(bankCity);
//		RepayAcDto.setBankSite(bankSite);
//		cblistAcRepayDto.setBankCode(JdAccountData.getBankCode());
		//银行代码字典项转换
		ParmDicData bankCodeDic2 = parmDicService.getParmDic("BANK_CODE",
				JdAccountData.getBankCode());
		if (bankCodeDic2 != null) {
			RepayAcDto.setBankCode(bankCodeDic2.getMateCode());
		} else {
			RepayAcDto.setBankCode(JdAccountData.getBankCode());
		}
		RepayAcDto.setIdType(JdAccountData.getHolderIdType());
		RepayAcDto.setIdNo(JdAccountData.getHolderIdNo());
		RepayAcDto.setPhoneNo(JdAccountData.getHolderMobileNo());
		AcDtoList.add(RepayAcDto);
	
		// List(All)
	
		Request2101ListDto cblistDto = new Request2101ListDto();
		List<LnComDto> listCom = new ArrayList<LnComDto>();
		List<LnGageDto> listGage = new ArrayList<LnGageDto>();
		cblistDto.setListCom(listCom);
		cblistDto.setListGage(listGage);
		cblistDto.setListRel(cblistRelDtoList);
		cblistDto.setListAc(AcDtoList);
		cblistDto.setPactNo(jdLoanApplyInfoData.getApplicationNo());
		cblistDto.setCustName(jdLoanUserData.getName());
		cblistDto.setIdType(jdLoanUserData.getCeritificateType());
		cblistDto.setIdNo(jdLoanUserData.getCeritificateNo());
		cblistDto.setIdPreDate(jdLoanUserData.getEffstartdate().replace("-", ""));
		cblistDto.setIdEndDate(jdLoanUserData.getEffenddate().replace("-", ""));
		cblistDto.setTrade("26");
		//获取利率类型用来计算发送给小微的利率(月)
		cblistDto.setRateType(jdLoanApplyInfoData.getPrimaryRateType());
		//获取期限类型用来计算申请期限的值
		cblistDto.setPeriodType(jdLoanApplyInfoData.getPeriodType());
		
		Map<String, String> SexAndBirth = getBirSex(jdLoanUserData.getCeritificateNo());
		//身份证号码中拆解获取
		cblistDto.setSex(SexAndBirth.get("sexCode"));
		cblistDto.setBirth(SexAndBirth.get("birthday"));
		
		//****小微生产上规定为必填,在此添加测试****
		cblistDto.setIfId("2");
		cblistDto.setIfCar("2");
		cblistDto.setIfApp("1");
		cblistDto.setHomeIncome("11000.00");
		cblistDto.setIfRoom("1");
		cblistDto.setHomeCode(jdLoanUserData.getHomePostcode());
		cblistDto.setIfCard("2");
		cblistDto.setIfMort("2");
		cblistDto.setIfCarCred("2");
		cblistDto.setCardAmt("0.00");
		cblistDto.setIfPact("2");
		//****2101再添
		cblistDto.setCustType("99");
		cblistDto.setTelNo(jdLoanUserData.getTelephone());
		cblistDto.setHomeArea("000000");
		cblistDto.setFeeTotal("0.00");
		cblistDto.setVouType("4");
		cblistDto.setVouAmt("0.00");

		//判断关系人类型是否为配偶（10）
		for(LnRelDto RelDto:cblistRelDtoList) {
			if("10".equals(RelDto.getRelType())) {
				// 转换婚姻状况字典项
				ParmDicData marriageDic = parmDicService.getParmDic("MARITAL_STATUS",
						jdLoanUserData.getMaritalStatus());
				if (marriageDic != null) {
					cblistDto.setMarriage(marriageDic.getMateCode());
				} else {
					cblistDto.setMarriage(jdLoanUserData.getMaritalStatus());
				}
				if("20".endsWith(cblistDto.getMarriage())||"21".endsWith(cblistDto.getMarriage())||"22".endsWith(cblistDto.getMarriage())||"23".endsWith(cblistDto.getMarriage())) {
					//****2101接口暂定为非必填*****
					//cblistDto.setMateName(jdRelationUserDataList.get(i).getName());
					//cblistDto.setMateIdno(jdRelationUserDataList.get(i).getCertificateNo());
					//cblistDto.setMateIdtype(jdRelationUserDataList.get(i).getCertificateType());
					//cblistDto.setMateTel(jdRelationUserDataList.get(i).getTelephone());
				}
				}else {
					// 转换婚姻状况字典项
					ParmDicData marriageDic = parmDicService.getParmDic("MARITAL_STATUS",
							jdLoanUserData.getMaritalStatus());
				if (marriageDic != null) {
					cblistDto.setMarriage(marriageDic.getMateCode());
				} else {
					cblistDto.setMarriage(jdLoanUserData.getMaritalStatus());
				}
			}
			}
			cblistDto.setEdu(jdLoanUserData.getEducation());
			cblistDto.setDegree("9");
			cblistDto.setPhoneNo(jdLoanUserData.getCellphone());
			cblistDto.setPostAddr(jdLoanUserData.getAddress());
			cblistDto.setIncome(jdLoanUserData.getMonthlyIncome());	
			cblistDto.setProjNo(jdLoanApplyInfoData.getChannelProdNo());
	    	cblistDto.setPrdtNo(prdtNo);
			cblistDto.setPactAmt(jdLoanApplyInfoData.getContractAmount());
			cblistDto.setAppArea("000000");
			cblistDto.setAppUse("07");
			//****2101接口暂定为非必填
			//cblistDto.setPostCode(jdLoanUserDataList.getPostcode());
			//cblistDto.setHomeSts(jdLoanUserData.getRealEstateType().toString());
			//cblistDto.setWorkType("Z");
			//cblistDto.setWorkName("未知");
			//cblistDto.setWorkWay("Z");
			//cblistDto.setWorkDuty("9");
			//cblistDto.setWorkTitle("0");

			//****待处理
			cblistDto.setHomeAddr(jdLoanUserData.getHomeAddress());
			
			//利率处理("02"为月利率;"01"为年利率;"03"为日利率)
			if("02".equals(cblistDto.getRateType())) {
				cblistDto.setLnRate(jdLoanApplyInfoData.getPrimaryRate());
				}else if("01".equals(cblistDto.getRateType())) {
					BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getPrimaryRate().toString());
					BigDecimal b1 = a1.divide(new BigDecimal(12));
					//四舍五入
					cblistDto.setLnRate(b1.setScale(2, RoundingMode.UP).doubleValue());
				}else if("03".equals(cblistDto.getRateType())) {
					BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getPrimaryRate().toString());
					BigDecimal b1 = a1.multiply(new BigDecimal(365));
					BigDecimal c1 = b1.divide(new BigDecimal(12));
					//日利率*365/12(四舍五入)
					cblistDto.setLnRate(c1.setScale(2, RoundingMode.UP).doubleValue());
				}
			
			//已处理（合同期限）
			if("02".equals(cblistDto.getPeriodType())){
			cblistDto.setTermMon(jdLoanApplyInfoData.getExpiresMonth());
			cblistDto.setTermDay(0);
			}else if("01".equals(cblistDto.getPeriodType())) {
				BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getExpiresMonth().toString());
				cblistDto.setTermMon(a1.divide(new BigDecimal(30)).intValue());
				cblistDto.setTermDay(a1.divideAndRemainder(new BigDecimal(30))[1].intValue());
			}
			// 转换扣款日类型字典项
			ParmDicData payTypeDic = parmDicService.getParmDic("PAY_TYPE", jdLoanApplyInfoData.getChargeDateType());
			if (payTypeDic != null) {
				cblistDto.setPayType(payTypeDic.getMateCode());
			} else {
				cblistDto.setPayType(jdLoanApplyInfoData.getChargeDateType());
			}			
			
			cblistDto.setIfLaunder("02");
			cblistDto.setLaunder("03");
			cblistDto.setSales("01");
			cblistDto.setIfAgent("02");
			cblistDto.setCountry("CHN");
			cblistDto.setProfession("12");

			//实际贷款合同号（申请号）
			cblistDto.setFactPactNo(jdLoanApplyInfoData.getApplicationNo());
			
			cblistDtoList.add(cblistDto);
		
		
		}else {
			
			
			//关系人信息为空时将Data层数据插入Dto
			//赋予默认值(Rel)
			LnRelDto cblistRelDto = new LnRelDto();
			cblistRelDto.setRelName("未知");
			cblistRelDto.setRelTel("17000000000");
			cblistRelDtoList.add(cblistRelDto);
			// List(LoanAcc)放款账户信息(acUse=2)
			JdLoanPaymentStepData jdLoanPaymentStepData = jdLoanPaymentStepDataList.get(0);

			//较数据库新增字段
			LoanAcDto.setAcUse(CmsBusinessStatus.AC_USE_LOAN.businessCode());
			
//				cblistAcLoanDto.setAcAmt(acAmt);
//				cblistAcLoanDto.setBankSite(bankSite);
//				cblistAcLoanDto.setCvvNo(cvvNo);
//				cblistAcLoanDto.setBankCity(jdLoanPaymentStepData.);
//				cblistAcLoanDto.setBankProv(bankProv);
			
			LoanAcDto.setAcType("11");
			LoanAcDto.setAcno(jdLoanPaymentStepData.getAccountNo());
			LoanAcDto.setAcName(jdLoanPaymentStepData.getAccountName());
			LoanAcDto.setAcAmt(jdLoanPaymentStepData.getPaymentAmount());
//				cblistAcLoanDto.setBankCode(jdLoanPaymentStepData.getBankCode());

			//银行代码字典项转换
			ParmDicData bankCodeDic = parmDicService.getParmDic("BANK_CODE",
					jdLoanPaymentStepData.getBankCode());
			if (bankCodeDic != null) {
				LoanAcDto.setBankCode(bankCodeDic.getMateCode());
			} else {
				LoanAcDto.setBankCode(jdLoanPaymentStepData.getBankCode());
			}
			
			LoanAcDto.setIdType(jdLoanPaymentStepData.getHolderIdType());
			LoanAcDto.setIdNo(jdLoanPaymentStepData.getHolderIdNo());
			LoanAcDto.setPhoneNo(jdLoanPaymentStepData.getHolderMobileNo());
			AcDtoList.add(LoanAcDto);
			// List(RepayAcc)还款账户信息(acUse=1)
			JdAccountData JdAccountData = jdRepayAccountData;
//				cblistAcRepayDto.setAcAmt(acAmt);
//				cblistAcRepayDto.setBankSite(bankSite);
//				cblistAcRepayDto.setCvvNo(cvvNo);
//				cblistAcRepayDto.setBankCity(jdLoanPaymentStepData.);
//				cblistAcRepayDto.setBankProv(bankProv);
			
			RepayAcDto.setAcUse(CmsBusinessStatus.AC_USE_REPAY.businessCode());
			RepayAcDto.setAcType("11");
			RepayAcDto.setAcno(JdAccountData.getAccountNo());
			RepayAcDto.setAcName(JdAccountData.getAccountName());
//			cblistAcRepayDto.setBankCode(JdAccountData.getBankCode());
			//银行代码字典项转换
			ParmDicData bankCodeDic2 = parmDicService.getParmDic("BANK_CODE",
					JdAccountData.getBankCode());
			if (bankCodeDic2 != null) {
				RepayAcDto.setBankCode(bankCodeDic2.getMateCode());
			} else {
				RepayAcDto.setBankCode(JdAccountData.getBankCode());
			}
			RepayAcDto.setIdType(JdAccountData.getHolderIdType());
			RepayAcDto.setIdNo(JdAccountData.getHolderIdNo());
			RepayAcDto.setPhoneNo(JdAccountData.getHolderMobileNo());
			AcDtoList.add(RepayAcDto);
			
			// List(All)
			Request2101ListDto cblistDto = new Request2101ListDto();
			List<LnComDto> listCom = new ArrayList<LnComDto>();
			List<LnGageDto> listGage = new ArrayList<LnGageDto>();
			cblistDto.setListCom(listCom);
			cblistDto.setListGage(listGage);
			cblistDto.setListRel(cblistRelDtoList);
			cblistDto.setListAc(AcDtoList);
			
			cblistDto.setPactNo(jdLoanApplyInfoData.getApplicationNo());
			cblistDto.setCustName(jdLoanUserData.getName());
			cblistDto.setIdType(jdLoanUserData.getCeritificateType());
			cblistDto.setIdNo(jdLoanUserData.getCeritificateNo());
			cblistDto.setIdPreDate(jdLoanUserData.getEffstartdate().replace("-", ""));
			cblistDto.setIdEndDate(jdLoanUserData.getEffenddate().replace("-", ""));
			cblistDto.setTrade("26");
			//获取利率类型用来计算发送给小微的利率(月)
			cblistDto.setRateType(jdLoanApplyInfoData.getPrimaryRateType());
			//获取期限类型用来计算申请期限的值
			cblistDto.setPeriodType(jdLoanApplyInfoData.getPeriodType());
			
			Map<String, String> SexAndBirth = getBirSex(jdLoanUserData.getCeritificateNo());
			//身份证号码中拆解获取
			cblistDto.setSex(SexAndBirth.get("sexCode"));
			cblistDto.setBirth(SexAndBirth.get("birthday"));
			
			//****小微生产上规定为必填,在此添加测试****
			cblistDto.setIfId("2");
			cblistDto.setIfCar("2");
			cblistDto.setIfApp("1");
			cblistDto.setHomeIncome("11000.00");
			cblistDto.setIfRoom("1");
			cblistDto.setHomeCode("100000");
			cblistDto.setIfCard("2");
			cblistDto.setIfMort("2");
			cblistDto.setIfCarCred("2");
			cblistDto.setCardAmt("0.00");
			cblistDto.setIfPact("2");
			//****2101再添
			cblistDto.setCustType("99");
			cblistDto.setTelNo(jdLoanUserData.getTelephone());
			cblistDto.setHomeArea("000000");
			cblistDto.setFeeTotal("0");
			cblistDto.setVouType("4");
			cblistDto.setVouAmt("0");

			//判断关系人类型是否为配偶（10）
			for(LnRelDto RelDto:cblistRelDtoList) {
				if("10".equals(RelDto.getRelType())) {
					// 转换婚姻状况字典项（待处理）
					ParmDicData marriageDic = parmDicService.getParmDic("MARITAL_STATUS",
							jdLoanUserData.getMaritalStatus());
					if (marriageDic != null) {
						cblistDto.setMarriage(marriageDic.getMateCode());
					} else {
						cblistDto.setMarriage(jdLoanUserData.getMaritalStatus());
					}
					if("20".endsWith(cblistDto.getMarriage())||"21".endsWith(cblistDto.getMarriage())||"22".endsWith(cblistDto.getMarriage())||"23".endsWith(cblistDto.getMarriage())) {
						//****2101接口暂定为非必填*****
						//cblistDto.setMateName(jdRelationUserDataList.get(i).getName());
						//cblistDto.setMateIdno(jdRelationUserDataList.get(i).getCertificateNo());
						//cblistDto.setMateIdtype(jdRelationUserDataList.get(i).getCertificateType());
						//cblistDto.setMateTel(jdRelationUserDataList.get(i).getTelephone());
					}
				}else {
					// 转换婚姻状况字典项（待处理）
					ParmDicData marriageDic = parmDicService.getParmDic("MARITAL_STATUS",
							jdLoanUserData.getMaritalStatus());
					if (marriageDic != null) {
						cblistDto.setMarriage(marriageDic.getMateCode());
					} else {
						cblistDto.setMarriage(jdLoanUserData.getMaritalStatus());
					}	
				}
			}
			cblistDto.setEdu(jdLoanUserData.getEducation());
			cblistDto.setDegree("9");
			cblistDto.setPhoneNo(jdLoanUserData.getCellphone());
			cblistDto.setPostAddr(jdLoanUserData.getAddress());
			cblistDto.setIncome(jdLoanUserData.getMonthlyIncome());	
			cblistDto.setProjNo(jdLoanApplyInfoData.getChannelProdNo());
	    	cblistDto.setPrdtNo(prdtNo);
			cblistDto.setPactAmt(jdLoanApplyInfoData.getContractAmount());
			cblistDto.setAppArea("000000");
			cblistDto.setAppUse("07");
			//****2101接口暂定为非必填
			//cblistDto.setPostCode(jdLoanUserDataList.getPostcode());
			//cblistDto.setHomeSts(jdLoanUserData.getRealEstateType().toString());			
			//cblistDto.setWorkType("Z");
			//cblistDto.setWorkName("未知");
			//cblistDto.setWorkWay("Z");
			//cblistDto.setWorkDuty("9");
			//cblistDto.setWorkTitle("0");

			//****待处理
			cblistDto.setHomeAddr(jdLoanUserData.getHomeAddress());
			
				//利率处理("02"为月利率;"01"为年利率;"03"为日利率)
				if("02".equals(cblistDto.getRateType())) {
					cblistDto.setLnRate(jdLoanApplyInfoData.getPrimaryRate());
					}else if("01".equals(cblistDto.getRateType())) {
						BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getPrimaryRate().toString());
						BigDecimal b1 = a1.divide(new BigDecimal(12));
						//四舍五入
						cblistDto.setLnRate(b1.setScale(2, RoundingMode.UP).doubleValue());
					}else if("03".equals(cblistDto.getRateType())) {
						BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getPrimaryRate().toString());
						BigDecimal b1 = a1.multiply(new BigDecimal(365));
						BigDecimal c1 = b1.divide(new BigDecimal(12));
						//日利率*365/12(四舍五入)
						cblistDto.setLnRate(c1.setScale(2, RoundingMode.UP).doubleValue());
					}
				
				//已处理（合同期限）
				if("02".equals(cblistDto.getPeriodType())){
				cblistDto.setTermMon(jdLoanApplyInfoData.getExpiresMonth());
				cblistDto.setTermDay(0);
				}else if("01".equals(cblistDto.getPeriodType())) {
					BigDecimal a1 = new BigDecimal(jdLoanApplyInfoData.getExpiresMonth().toString());
					cblistDto.setTermMon(a1.divide(new BigDecimal(30)).intValue());
					cblistDto.setTermDay(a1.divideAndRemainder(new BigDecimal(30))[1].intValue());
				}
				// 转换扣款日类型字典项
				ParmDicData payTypeDic = parmDicService.getParmDic("PAY_TYPE", jdLoanApplyInfoData.getChargeDateType());
				if (payTypeDic != null) {
					cblistDto.setPayType(payTypeDic.getMateCode());
				} else {
					cblistDto.setPayType(jdLoanApplyInfoData.getChargeDateType());
				}

				cblistDto.setIfLaunder("02");
				cblistDto.setLaunder("03");
				cblistDto.setSales("01");
				cblistDto.setIfAgent("02");
				cblistDto.setCountry("CHN");
				cblistDto.setProfession("12");

				//实际贷款合同号（申请号）
				cblistDto.setFactPactNo(jdLoanApplyInfoData.getApplicationNo());
				cblistDtoList.add(cblistDto);
				
		}
		String batchNo = CmsBusinessStatus.JD.businessCode() + createKeyService.getBatchNoNo();
		String jdId = jdLoanApplyInfoData.getJdId();
		//更新贷款申请信息表中的批次号信息
		JdLoanApplyInfoData jd = new JdLoanApplyInfoData(jdId,batchNo);
		jdLoanApplyInfoDataMapper.updateBatchNo(jd);
		
		// request请求赋值
		Request2101Dto requestDto = new Request2101Dto();
		requestDto.setBatNo(batchNo);
		requestDto.setBrNo(brNo);
		requestDto.setDataCnt(1);
		requestDto.setList(cblistDtoList);

		// 发送request请求给小微
		Request request = new Request();
		request.setTxCode(CmsBusinessStatus.WS_2101.businessCode()); // 接口编号
		request.setBrNo(brNo); // 机构号
		request.setReqDate(DateUtils.nowDateFormat()); // 设置请求日期
		request.setToken("test"); // 设置token
		request.setReqTime(DateUtils.nowTimeFormat()); // 设置请求时间
		request.setReqSerial(tradeNo); // 设置请求流水号
		try {
			request.setContent(objectMapper.writeValueAsString(requestDto));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return request;

	}
			
	 /**
     * 通过身份证号码获取出生日期、性别、
     * @param certificateNo
     * @return 返回的出生日期格式：19900101   性别格式：2-女，1-男
     */
    public Map<String, String> getBirSex(String certificateNo) {
        String birthday = "";
        String sexCode = "";
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) 
                	return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) 
                	return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) 
                    + certificateNo.substring(8, 10) 
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "2" : "1";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) 
                    + certificateNo.substring(10, 12) 
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "2" : "1";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("sexCode", sexCode);
        return map;
    }
	
	/**  
	* @Description 转换京东响应数据
	*/  
	private JdResponseView transLnPaymentsResponse(String tradeNo, Response response, String applicationNo) {
		JdResponseView jdResponseView = null;
		try {
			if (response != null) {
				// 判断业务是否成功
				if (CmsBusinessStatus.MFS_SUCCESS.businessCode().equals(response.getRespCode())) {
					//业务成功后数据库更新【贷款申请放款步骤表】信息中的放款状态与更新日期更新时间
					//更新贷款申请信息表中的批次号信息
					JdLoanPaymentStepData jd = new JdLoanPaymentStepData(applicationNo, JdResponseStatus.LOAN_STS_WORKING.businessCode(), DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
					jdLoanPaymentStepDataMapper.updatePayment(jd);
					
					jdResponseView = new JdResponseView(JdResponseStatus.SUCCESS_FOR_DEAL.businessCode(),
							JdResponseStatus.SUCCESS_FOR_DEAL.businessMessage(), tradeNo, "{}");
					
					/**
					 * 处理成功后发送，将任务放到MQ消息队列中
					 * 
					 * 	1. 创建MQ消息参数对象
					 * 	2. 将消息发送到MQ队列
					 */
					 MQParmsDomain message = new MQParmsDomain();
					 message.setTaskData(applicationNo);
					 message.setTaskType(CmsBusinessStatus.CALLBACK_LOAN_PAYMENTS_RESULT.businessCode());
					 message.setDelayLevel(Integer.parseInt(CmsBusinessStatus.MQ_DELAY_LEVEL_TWO.businessCode()));
					 jDMQProducerManager.send(message);
				
				} else if(CmsBusinessStatus.MFS_HALF_SUCCESS.businessCode().equals(response.getRespCode())) {
					Response2101Dto response2101 =  objectMapper.readValue(response.getContent(), Response2101Dto.class);
					String auditReasion = "";
					if(response2101 != null && response2101.getList().size() >0) {
						auditReasion = response2101.getList().get(0).getDealDesc();
					}
					List<String> list = new ArrayList<String>();
					list.add(auditReasion);
					JdErrorMessage jdErrorMessage = new JdErrorMessage();
					jdErrorMessage.setErrorMessages(list);
					//业务不成功数据库更新【贷款申请放款步骤表】信息中的放款状态与更新日期更新时间
					//更新贷款申请信息表中的批次号信息
					JdLoanPaymentStepData jd = new JdLoanPaymentStepData(applicationNo, JdResponseStatus.LOAN_STS_FAIL.businessCode(), DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
					jdLoanPaymentStepDataMapper.updatePayment(jd);
					
					// 传给JD——业务返回码、业务返回描述、交易流水号
					jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdErrorMessage));
				} else {
					// 如果业务不成功
					// 业务不成功数据库更新【贷款申请放款步骤表】信息中的放款状态与更新日期更新时间
					// 更新贷款申请信息表中的批次号信息
					JdLoanPaymentStepData jd = new JdLoanPaymentStepData(applicationNo, JdResponseStatus.LOAN_STS_FAIL.businessCode(), DateUtils.nowDateFormat(), DateUtils.nowTimeFormat());
					jdLoanPaymentStepDataMapper.updatePayment(jd);
					
					List<String> list = new ArrayList<String>();
					list.add(response.getRespDesc());
					JdErrorMessage jdErrorMessage = new JdErrorMessage();
					jdErrorMessage.setErrorMessages(list);
					// 传给JD——业务返回码、业务返回描述、交易流水号
					jdResponseView = new JdResponseView(JdResponseStatus.DATA_ERROR.businessCode(),
							JdResponseStatus.DATA_ERROR.businessMessage(), tradeNo, objectMapper.writeValueAsString(jdErrorMessage));
				}
			} else {
				jdResponseView = new JdResponseView(JdResponseStatus.UNKNOWN_ERROR.businessCode(), JdResponseStatus.UNKNOWN_ERROR.businessMessage(), tradeNo,
						null);
				}
		} catch (IOException e) {
			throw PlatformExceptionFactory.jsonParseException(CmsBusinessStatus.JSON_PARSE_FAILURE).build(e);
		}
		return jdResponseView;
	}


}