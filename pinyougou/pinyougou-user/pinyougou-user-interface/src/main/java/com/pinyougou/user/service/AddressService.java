package com.pinyougou.user.service;

import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbProvinces;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;


public interface AddressService extends BaseService<TbAddress> {

    PageResult search(Integer page, Integer rows, TbAddress address);


    /**
     * 查询所有的省份列表
     * @return 省份列表
     */
    List<TbProvinces> findProvincesList();


    /**
     * 根据省份id查找市
     * @param provinceid
     * @return List<TbCities>
     */
    List<TbCities> findByCityList(String provinceid);

    /**
     * 根据市级id查找县、区
     * @param cityid
     * @return List<TbCities>
     */
    List<TbAreas> findAreaByCityId(String cityid);


}