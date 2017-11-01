package com.computerdesign.whutHouseMgmt.controller.fixparam;

import java.util.List;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.fixparam.FixParameter;
import com.computerdesign.whutHouseMgmt.service.fixparam.FixParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping(value="/fixParam/")
@Controller
public class FixParameterController {

	@Autowired
	FixParamService fixParamService;
	
	/**
	 * ������������һ���ά�޲���
	 * @param paramTypeId
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="get/{paramTypeId}",method=RequestMethod.GET)
	public Msg getFixParameters(@PathVariable("paramTypeId")Integer paramTypeId,
			@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="size",defaultValue="10")Integer size){
		PageHelper.startPage(page,size);
		List<FixParameter> fixParameters=fixParamService.getAll(paramTypeId);
		
		PageInfo pageInfo = new PageInfo(fixParameters);
		if(fixParameters!=null){
			return Msg.success().add("data", pageInfo);
		}else{
			return Msg.error("û����Ϣ");
		}
	}
	
	/**
	 * ����һ�������޸Ĳ���
	 * @param fixParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Msg addFixParameter(@RequestBody FixParameter fixParameter){
		if(fixParameter.getFixParamName()!=null){
			if (fixParameter.getParamTypeId()!=null) {
				fixParamService.add(fixParameter);
				return Msg.success().add("data", fixParameter);
			}else{
				return Msg.error("ParamTypeIdΪ��");
			}
		}else{
			return Msg.error("FixParamNameΪ��");
		}
	}
	
	/**
	 * ɾ��һ������
	 * @param fixParamId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="delete",method=RequestMethod.DELETE)
	public Msg deleteFixParameter(@PathVariable("fixParamId")Integer fixParamId){
		FixParameter fixParameter=fixParamService.get(fixParamId);
		if(fixParameter!=null){
			fixParamService.delete(fixParameter.getFixParamId());
			return Msg.success().add("data", fixParameter);
		}else{
			return Msg.error("�Ҳ�����fixParamId");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="modify",method=RequestMethod.PUT)
	public Msg updateFixParameter(@RequestBody FixParameter fixParameter){
		try {
			fixParamService.update(fixParameter);
			return  Msg.success().add("data", fixParameter);
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.error("�޸�ʧ��");
		}
	}
}
