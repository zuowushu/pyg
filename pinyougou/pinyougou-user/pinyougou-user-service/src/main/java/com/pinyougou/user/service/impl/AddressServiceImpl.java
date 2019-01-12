package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.AddressMapper;
import com.pinyougou.mapper.AreasMapper;
import com.pinyougou.mapper.CitiesMapper;
import com.pinyougou.mapper.ProvincesMapper;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbAreas;
import com.pinyougou.pojo.TbCities;
import com.pinyougou.pojo.TbProvinces;
import com.pinyougou.user.service.AddressService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl extends BaseServiceImpl<TbAddress> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ProvincesMapper provincesMapper;
    @Autowired
    private CitiesMapper citiesMapper;
    @Autowired
    private AreasMapper areasMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbAddress address) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbAddress.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(!StringUtils.isEmpty(address.get***())){
            criteria.andLike("***", "%" + address.get***() + "%");
        }*/

        List<TbAddress> list = addressMapper.selectByExample(example);
        PageInfo<TbAddress> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 查询所有的省份列表
     *
     * @return 省份列表
     */
    @Override
    public List<TbProvinces> findProvincesList() {
        return provincesMapper.selectAll();
    }

    /**
     * 根据省份id查找市
     * @param provinceid
     * @return List<TbCities>
     */
    @Override
    public List<TbCities> findByCityList(String provinceid) {
        TbCities citys=new TbCities();
        citys.setProvinceid(provinceid);
        List<TbCities> citiesList = citiesMapper.select(citys);
        return citiesList;
    }

    /**
     * 根据市级id查找县、区
     * @param cityid
     * @return List<TbCities>
     */
    @Override
    public List<TbAreas> findAreaByCityId(String cityid) {
        TbAreas area=new TbAreas();
        area.setCityid(cityid);
        return areasMapper.select(area);
    }

    @Override
    public TbAddress findOneAddress(String contact) {
        TbAddress address = new TbAddress();
        address.setContact(contact);
        address = addressMapper.selectOne(address);
        return address;
    }
    /**
     * 设置为默认地址
     * @param isDefault
     * @return
     */
    @Override
    public int updateIsDefault(String isDefault) {
        if ("0".equals(isDefault)){
            String param = 1+"";
            TbAddress address = new TbAddress();
            address.setIsDefault(param);
            return addressMapper.updateByExample(address,param);
        }
        return 0;

    }


}
