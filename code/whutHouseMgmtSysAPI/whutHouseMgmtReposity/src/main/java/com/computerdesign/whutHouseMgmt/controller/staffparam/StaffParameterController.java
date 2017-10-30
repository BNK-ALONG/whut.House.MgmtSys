package com.computerdesign.whutHouseMgmt.controller.staffparam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.staffparam.StaffParameter;
import com.computerdesign.whutHouseMgmt.service.staffparam.StaffParameterService;

@RequestMapping("/staffParam/")
@Controller
public class StaffParameterController {

	@Autowired
	StaffParameterService StaffParameterService;
	
	@ResponseBody
	@RequestMapping(value="modify", method=RequestMethod.PUT)
	public Msg modifyStaffParameter(@RequestBody StaffParameter StaffParameter){
		System.out.println(StaffParameter);
		try{
			StaffParameterService.update(StaffParameter);
			return Msg.success().add("modifiedStaffParameter", StaffParameter);
		}catch(Exception e) {
			return Msg.error();
		}
		
	}

	/**
	 * ���һ��ְ��������¼
	 * @param StaffParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Msg addStaffParameter(@RequestBody StaffParameter StaffParameter) {
//		System.out.println(StaffParameter);
		if(StaffParameter.getStaffParamName()!=null && StaffParameter.getParamTypeId() != null && StaffParameter.getParamTypeName()!=null){
			StaffParameterService.add(StaffParameter);
			return Msg.success().add("addedEmpParam", StaffParameter);
		}else{
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}
		
	}

	/**
	 * ����StaffParamIdɾ����Ӧ��¼
	 * @param StaffParamId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete/{StaffParamId}", method = RequestMethod.DELETE)
	public Msg deleteStaffParameter(@PathVariable("StaffParamId") Integer StaffParamId) {
		StaffParameter StaffParameter = StaffParameterService.get(StaffParamId);
		if (StaffParameter == null) {
			return Msg.error("���ݿ����޸ü�¼");
		} else {
			try {
				StaffParameterService.delete(StaffParamId);
				return Msg.success().add("deletedStaffParam", StaffParameter);
			} catch (Exception e) {
				return Msg.error();
			}
		}

	}

	/**
	 * ����paramTypeId��ȡ��Ӧ����ְ������
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/{paramTypeId}")
	public Msg getStaffParameter(@PathVariable("paramTypeId") Integer paramTypeId) {
		// ��ȡ���в���
		List<StaffParameter> staffParams = StaffParameterService.getAll();

		// ���ڷ�װ�������
		List<StaffParameter> staffParamsResult = new ArrayList<StaffParameter>();
		for (StaffParameter staffParam : staffParams) {
			if (staffParam.getParamTypeId() == paramTypeId) {
				staffParamsResult.add(staffParam);
			}
		}

		if (staffParamsResult != null) {
			return Msg.success().add("staffParamsResult", staffParamsResult);
		} else {
			return Msg.error();
		}
	}

}
