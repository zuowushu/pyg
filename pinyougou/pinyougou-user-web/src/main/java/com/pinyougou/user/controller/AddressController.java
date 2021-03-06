package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.util.PhoneFormatCheckUtils;
import com.pinyougou.pojo.*;
import com.pinyougou.user.service.AddressService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Reference
    private AddressService addressService;

    /**
     * 查询当前登录用户的地址列表
     * @return 地址列表
     */
    @RequestMapping("/findAddressList")
    public List<TbAddress> findAddressList() {
        //获取当前登录用户名
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        TbAddress address = new TbAddress();
        address.setUserId(userId);
        return addressService.findByWhere(address);
    }
    /**
     * 根据查询所有的省份列表
     * @return 省份列表
     */
    @RequestMapping("/findProvincesList")
    public List<TbProvinces> findProvincesList() {
        return addressService.findProvincesList();
    }

    /**
     * 根据省份id查找市
     * @param provinceid
     * @return List<TbCities>
     */
    @GetMapping("/findByCityList")
    public List<TbCities> findByCityList(String provinceid) {
        return addressService.findByCityList(provinceid);
    }

    /**
     * 设置为默认地址
     * @param isDefault
     * @return TbAddress
     */
    @GetMapping("/updateIsDefault")
    public int updateIsDefault(String isDefault) {
        return addressService.updateIsDefault(isDefault);
    }
    /**
     * 根据市级id查找县、区
     * @param cityid
     * @return List<TbCities>
     */
    @GetMapping("/findByCityId")
    public List<TbAreas> findByCityId(String cityid) {
        return addressService.findAreaByCityId(cityid);
    }

    /**
     * 根据地址的contact查询地址
     * @param contact
     * @return TbAddress
     */
    @GetMapping("/findOneAddress")
    public TbAddress findOneAddress(String contact) {
        return addressService.findOneAddress(contact);
    }

    @RequestMapping("/findAll")
    public List<TbAddress> findAll() {
        return addressService.findAll();
    }
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return addressService.findPage(page, rows);
    }

    @PostMapping("/add")
    public Result add(@RequestBody TbAddress address) {
        //获取当前登录用户名
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Result result = Result.fail("增加失败");
        try {
            if (address.getId()==null) {//新增
                if (PhoneFormatCheckUtils.isPhoneLegal(address.getMobile())) {
                    address.setCreateDate(new Date());
                    address.setIsDefault("0");
                    address.setUserId(userId);
                    addressService.add(address);
                    result = Result.ok("增加成功");
                } else {
                    result = Result.fail("手机号码格式不正确，增加失败");
                }
            }else {
                try {
                    addressService.update(address);
                    return Result.ok("修改成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @GetMapping("/findOne")
    public TbAddress findOne(Long id) {
        return addressService.findOne(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody TbAddress address) {
        try {
            addressService.update(address);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            addressService.deleteByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 分页查询列表
     * @param address 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody  TbAddress address, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return addressService.search(page, rows, address);
    }

}
