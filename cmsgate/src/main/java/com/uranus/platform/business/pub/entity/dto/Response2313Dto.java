package com.uranus.platform.business.pub.entity.dto;

import java.util.List;

import com.uranus.tools.beans.annotation.ConvertBean;

import lombok.Data;

/**
 * 
* @ClassName:：Response2313Dto 
* @Description： 2313返回
* @author ：chenwendong
* @date ：2019年8月22日 下午6:16:16 
*
 */
@Data
@ConvertBean
public class Response2313Dto {

	private int pageCounts;
	private List<Response2313List> list;
}
