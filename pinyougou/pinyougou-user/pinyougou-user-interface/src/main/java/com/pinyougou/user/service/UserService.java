package com.pinyougou.user.service;

import com.pinyougou.pojo.TbUser;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

public interface UserService extends BaseService<TbUser> {

    PageResult search(Integer page, Integer rows, TbUser user);

    /**
     * 发送短信验证码
     * @param phone 手机号
     */
    void sendSmsCode(String phone);

    /**
     * 校验redis中保存的验证码
     * @param phone 手机号
     * @param smsCode 用户输入的验证码
     * @return 校验结果 true or false
     */
    boolean checkSmsCode(String phone, String smsCode);
}