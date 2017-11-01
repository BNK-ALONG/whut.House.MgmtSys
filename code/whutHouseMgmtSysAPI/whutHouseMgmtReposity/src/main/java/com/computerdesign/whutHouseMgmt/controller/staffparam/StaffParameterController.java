package com.computerdesign.whutHouseMgmt.controller.staffparam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.paramclass.ParamClass;
import com.computerdesign.whutHouseMgmt.bean.staffparam.StaffParameter;
import com.computerdesign.whutHouseMgmt.service.paramclass.ParamClassService;
import com.computerdesign.whutHouseMgmt.service.staffparam.StaffParameterService;

@RequestMapping("/staffParam/")
@Controller
public class StaffParameterController {

	@Autowired
	private StaffParameterService staffParameterService;

	@Autowired
	private ParamClassService paramClassService;

	// /**
	// * ����������֮ǰִ�У����paramTypeName
	// * ����paramTypeId��ȡParamClass����SpringMVC�������Map�У���������ΪĿ�귽���Ĳ���
	// *
	// * @param paramTypeId
	// */
	// @ModelAttribute
	// public void getParamTypeName(@PathVariable(value = "paramTypeId",
	// required = false) Integer paramTypeId,
	// Map<String, Object> map) {
	// if (paramTypeId != null) {
	// ParamClass paramClass = paramClassService.get(paramTypeId);
	// System.out.println(paramClass.getParamTypeName());
	// map.put("paramClass", paramClass);
	// }
	// }

	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.PUT)
	public Msg modifyStaffParameter(@RequestBody StaffParameter staffParameterModel) {
		// System.out.println(staffParameterModel);
		// ����id��ȡ����Ҫ�޸ĵ�ְ������
		StaffParameter staffParameter = staffParameterService.get(staffParameterModel.getStaffParamId());
		if (staffParameter == null) {
			return Msg.error("���ݿ����Ҳ����ü�¼");
		} else {
			try {
				staffParameterService.update(staffParameterModel);
				return Msg.success().add("data", staffParameterModel);
			} catch (Exception e) {
				return Msg.error();
			}
		}
	}

	/**
	 * ���һ��ְ��������¼
	 * 
	 * @param StaffParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Msg addStaffParameter(@RequestBody StaffParameter staffParameterModel) {
		// System.out.println(StaffParameter);
		// System.out.println(paramClass.getParamTypeName());
		// ��������װ��StaffParameter����
		if (staffParameterModel.getStaffParamName() != null && staffParameterModel.getParamTypeId() != null) {
			staffParameterService.add(staffParameterModel);
			return Msg.success().add("data", staffParameterModel);
		} else {
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}

	}

	/**
	 * ����StaffParamIdɾ����Ӧ��¼
	 * 
	 * @param StaffParamId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete/{staffParamId}", method = RequestMethod.DELETE)
	public Msg deleteStaffParameter(@PathVariable("staffParamId") Integer staffParamId) {
		StaffParameter staffParameter = staffParameterService.get(staffParamId);
		if (staffParameter == null) {
			return Msg.error("���ݿ����޸ü�¼");
		} else {
			try {
				staffParameterService.delete(staffParamId);
				return Msg.success().add("data", staffParameter);
			} catch (Exception e) {
				return Msg.error();
			}
		}

	}

	/**
	 * ����paramTypeId��ȡ��Ӧ����ְ������
	 * 
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get/{paramTypeId}")
	public Msg getStaffParameter(@PathVariable("paramTypeId") Integer paramTypeId) {
		// // ��ȡ���в���
		// List<StaffParameter> staffParams = staffParameterService.getAll();
		//
		// // ���ڷ�װ�������
		// List<StaffParameter> staffParamsResult = new
		// ArrayList<StaffParameter>();
		// for (StaffParameter staffParam : staffParams) {
		// if (staffParam.getParamTypeId() == paramTypeId) {
		// staffParamsResult.add(staffParam);
		// }
		// }

		// ��ȡ��ӦparamTypeId�Ĳ���
		List<StaffParameter> staffParams = staffParameterService.getAllByParamTypeId(paramTypeId);

		if (staffParams != null) {
			return Msg.success().add("data", staffParams);
		} else {
			return Msg.error("������");
		}
	}

}
