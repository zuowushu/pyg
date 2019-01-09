package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

public interface SellerService extends BaseService<TbSeller> {

    PageResult search(Integer page, Integer rows, TbSeller seller);

    /**
     * 查询商家
     * @param sellerId
     * @return
     */
    TbSeller findSellerBySellerId(String sellerId);

    /**
     * 更新用户密码
     * @param seller
     */
    void updatePassword(TbSeller seller);
}