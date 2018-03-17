package com.computerdesign.whutHouseMgmt.controller.houseparam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.computerdesign.whutHouseMgmt.bean.Msg;
import com.computerdesign.whutHouseMgmt.bean.param.houseparam.HouseParameter;
import com.computerdesign.whutHouseMgmt.service.houseparam.HouseParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/houseParam/")
@Controller
@Api(value = "ס������Controller", description = "ס�������ӿ�")
public class HouseParameterController {

	@Autowired
	private HouseParamService houseParamService;

	/**
	 * ��ȡȫ����houseParamId
	 * 
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getHouseParamId/{paramTypeId}", method = RequestMethod.GET)
	@ApiOperation(value = "��ȡĳһ�ֲ������͵�ȫ��id", notes = "���ݷ��ݲ������͵�id��ȡ�������͵����з��ݲ�����Ϣ��id", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg getHouseTypePar(@PathVariable("paramTypeId") Integer paramTypeId) {
		List<Integer> houseParamIds = houseParamService.getHouseParamId(paramTypeId);
		return Msg.success().add("data", houseParamIds);
	}

	/**
	 * ����ס��������ȡȫ����ס��������Ϣ
	 * 
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWithoutPage/{paramTypeId}", method = RequestMethod.GET)
	@ApiOperation(value = "��ȡĳһ�����͵ķ��ݲ���", notes = "����paramTypeId��ȡĳһ�����͵�ȫ�����ݲ���,����ҳ", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg getHouseParameter(@PathVariable("paramTypeId") Integer paramTypeId) {
		List<HouseParameter> houseParams = houseParamService.getbyParamTypeId(paramTypeId);

		if (houseParams != null) {
			return Msg.success().add("data", houseParams);
		} else {
			return Msg.error("�����ڶ��ڵ�ס����Ϣ");
		}
	}

	/**
	 * paramTypeId�� 1-ס������ 2-���� 3-ʹ��״̬ 4-ס���ṹ
	 * 
	 * @param paramTypeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get/{paramTypeId}", method = RequestMethod.GET)
	@ApiOperation(value = "��ȡĳһ�����͵ķ��ݲ���", notes = "����paramTypeId��ȡĳһ�����͵�ȫ�����ݲ���,��ҳ", httpMethod = "GET", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg getHouseParameter(@PathVariable("paramTypeId") Integer paramTypeId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		PageHelper.startPage(page, size);
		List<HouseParameter> houseParams = houseParamService.getbyParamTypeId(paramTypeId);
		//
		PageInfo pageInfo = new PageInfo(houseParams);

		if (houseParams != null) {
			return Msg.success().add("data", pageInfo);
		} else {
			return Msg.error("�����ڶ��ڵ�ס����Ϣ");
		}
	}

	/**
	 * @param houseParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ApiOperation(value = "����һ��ס��������Ϣ", notes = "����һ��ס��������Ϣ", httpMethod = "POST", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg addHouseParameter(@RequestBody HouseParameter houseParameter) {
		if (houseParameter.getHouseParamName() == null) {
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}
		if (houseParameter.getParamTypeId() == null) {
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}
		if (houseParameter.getParamTypeId() == 4 && houseParameter.getStructRent() == null) {// ס���ṹ��Ϣ
			return Msg.error("ס���ṹ��Ϣ�������÷���ÿƽ��������");
		}
		// ����ȫ�����еķ��ݲ�����Ϣ
		List<HouseParameter> listHouseParameter = houseParamService.getbyParamTypeId(houseParameter.getParamTypeId());
		for (HouseParameter houseParamAlready : listHouseParameter) {
			// ��鷿�ݲ�����Ϣ�������Ƿ��Ѿ�����
			if (houseParameter.getHouseParamName().equals(houseParamAlready.getHouseParamName())) {
				return Msg.error("�������Ѵ��ڣ��޷����");
			}
		}
		houseParamService.add(houseParameter);
		return Msg.success().add("data", houseParameter);
	}

	/**
	 * @param houseParamId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete/{houseParamId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "ɾ��һ��������Ϣ", notes = "ɾ��һ��������Ϣ", httpMethod = "DELETE", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg deleteHouseParam(@PathVariable("houseParamId") Integer houseParamId) {
		HouseParameter houseParameter = houseParamService.get(houseParamId);
		if (houseParameter == null) {
			return Msg.error("�Ҳ�����id��ɾ������");
		}
		try {
			houseParameter.setIsDelete(true);
			houseParamService.update(houseParameter);
			return Msg.success().add("data", houseParameter);
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.error("��ס�������޷�ɾ��");
		}
	}

	/**
	 * @param houseParameter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.PUT)
	@ApiOperation(value = "�޸�һ�����ݲ�����Ϣ", httpMethod = "PUT", response = com.computerdesign.whutHouseMgmt.bean.Msg.class)
	public Msg modifyHouseParam(@RequestBody HouseParameter houseParameter) {
		if (houseParameter.getHouseParamName() == null) {
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}
		if (houseParameter.getParamTypeId() == null) {
			return Msg.error("��Ҫ��Ϣ�����������ʧ��");
		}
		if (houseParameter.getParamTypeId() == 4 && houseParameter.getStructRent() == null) {// ס���ṹ��Ϣ
			return Msg.error("ס���ṹ��Ϣ�������÷���ÿƽ��������");
		}
		// ����ȫ�����еķ��ݲ�����Ϣ
		List<HouseParameter> listHouseParameter = houseParamService.getbyParamTypeId(houseParameter.getParamTypeId());
		// ������Щ��Ϣ
		Iterator iterator = listHouseParameter.iterator();
		while (iterator.hasNext()) {
			HouseParameter houseParamAlready = (HouseParameter) iterator.next();
			if (houseParamAlready.getHouseParamId() != houseParameter.getHouseParamId()
					&& houseParameter.getHouseParamName().equals(houseParamAlready.getHouseParamName())) {
				return Msg.error("�������Ѵ��ڣ��޷��޸�");
			}
		}
		
		try {
			houseParamService.update(houseParameter);
			return Msg.success().add("data", houseParameter);
		} catch (Exception e) {
			// TODO: handle exception
			return Msg.error("���ݿ���û���ҵ�������¼���޸�ʧ�� ");
		}
	}
}
