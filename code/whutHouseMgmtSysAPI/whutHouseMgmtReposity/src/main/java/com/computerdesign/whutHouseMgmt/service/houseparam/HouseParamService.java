package com.computerdesign.whutHouseMgmt.service.houseparam;

import java.lang.reflect.Parameter;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.computerdesign.whutHouseMgmt.bean.param.houseparam.HouseParameter;
import com.computerdesign.whutHouseMgmt.bean.param.houseparam.HouseParameterExample;
import com.computerdesign.whutHouseMgmt.bean.param.houseparam.HouseParameterExample.Criteria;
import com.computerdesign.whutHouseMgmt.dao.param.houseparam.HouseParameterMapper;
import com.computerdesign.whutHouseMgmt.service.base.BaseService;

@Service
public class HouseParamService implements BaseService<HouseParameter>{

	@Autowired
	HouseParameterMapper houseParameterMapper;
	
	public List<Integer> getHouseParamId(Integer paramTypeId) {
		return houseParameterMapper.selectHouseParamId(paramTypeId);
	}
	
	/**
	 * ��ȡһ��houseParameter����
	 * @return
	 */
	public HouseParameter get(Integer houseParamId){
		return houseParameterMapper.selectByPrimaryKey(houseParamId);
	}
	/**
	 * ��ȡȫ���ķ��ݲ�����Ϣ
	 */
	@Override
	public List<HouseParameter> getAll(){
		List<HouseParameter> houseParameters=houseParameterMapper.selectByExample(null);
		return houseParameters;
	}
	
	/**
	 * ����paramTypeId����ѡ��
	 * @param paramTypeId
	 * @return
	 */
	public List<HouseParameter> getbyParamTypeId(Integer paramTypeId){
		HouseParameterExample example=new HouseParameterExample();
		Criteria criteria=example.createCriteria();
		criteria.andParamTypeIdEqualTo(paramTypeId);
		List<HouseParameter> houseParameters=houseParameterMapper.selectByExample(example);
		return houseParameters;
	}
	
	@Override
	public void add(HouseParameter houseParameter){
		houseParameterMapper.insertSelective(houseParameter);
	}
	
	
	@Override
	public void delete(Integer houseParamId){
		houseParameterMapper.deleteByPrimaryKey(houseParamId);
	}
	
	@Override
	public void update(HouseParameter houseParameter){
		houseParameterMapper.updateByPrimaryKeySelective(houseParameter);
	}
}
