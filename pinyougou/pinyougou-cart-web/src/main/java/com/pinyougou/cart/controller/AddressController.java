package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.user.service.AddressService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        try {
            addressService.add(address);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("增加失败");
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
