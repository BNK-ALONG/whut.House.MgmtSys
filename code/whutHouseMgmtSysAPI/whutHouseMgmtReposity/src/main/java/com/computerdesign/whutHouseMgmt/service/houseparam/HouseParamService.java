package com.computerdesign.whutHouseMgmt.service.houseparam;

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
	 * 获取一个houseParameter对象
	 * @return
	 */
	public HouseParameter get(Integer houseParamId){
		return houseParameterMapper.selectByPrimaryKey(houseParamId);
	}
	/**
	 * 获取全部的房屋参数信息
	 */
	@Override
	public List<HouseParameter> getAll(){
		HouseParameterExample example=new HouseParameterExample();
		Criteria criteria=example.createCriteria();
		criteria.andIsDeleteEqualTo(false);
		List<HouseParameter> houseParameters=houseParameterMapper.selectByExample(example);
		return houseParameters;
	}
	
	/**
	 * 根据paramTypeId进行选择
	 * @param paramTypeId
	 * @return
	 */
	public List<HouseParameter> getbyParamTypeId(Integer paramTypeId){
		HouseParameterExample example=new HouseParameterExample();
		Criteria criteria=example.createCriteria();
		criteria.andParamTypeIdEqualTo(paramTypeId);
		criteria.andIsDeleteEqualTo(false);
		List<HouseParameter> houseParameters=houseParameterMapper.selectByExample(example);
		return houseParameters;
	}
	
	/**
	 * 根据structId获取该结构的每平方米的价格
	 * structId就是houseParamId
	 * 
	 * @param structId
	 * @return
	 */
	public Double getRentalByStruce(Integer structId) {
		HouseParameterExample example=new HouseParameterExample();
		Criteria criteria=example.createCriteria();
		criteria.andHouseParamIdEqualTo(structId);
		return houseParameterMapper.selectByPrimaryKey(structId).getStructRent();
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
