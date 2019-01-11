package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.vo.Result;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Reference(timeout = 1000000000)
    private SellerService sellerService;

    /**
     * 获取当前登录的用户名
     * @return 用户名
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername(){
        Map<String, Object> map = new HashMap<String, Object>();
        //获取当前登录的用户名
        //可以获取到角色权限集合
        //SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username", username);
        return map;
    }


    //修改用户密码
    @GetMapping("/updatePassword")
    public Result updatePassword(String newPassword,String oldPassword){
         Result result=Result.fail("修改密码失败");
        try {
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            //查询商家
            TbSeller seller=sellerService.findSellerBySellerId(sellerId);
            //使用BCryptPasswordEncoder加密
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

            //判读旧密码是否正确
            if(bCryptPasswordEncoder.matches(oldPassword,seller.getPassword())){

                //对新密码加密
                seller.setPassword(bCryptPasswordEncoder.encode(newPassword));
                //更新数据
                sellerService.updatePassword(seller);
                return Result.ok("修改密码成功");

            }else{
                return Result.fail("原密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
