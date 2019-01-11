package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/itemSearch")
@RestController
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;

    /**
     * 根据搜索关键字和其它条件到solr中查询数据
     * @param searchMap 搜索条件
     * @return 查询结果
     */
    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody Map<String, Object> searchMap){
        return itemSearchService.search(searchMap);
    }

    /**
     * 根据商家id商家商品列表
     * @param sellerId 商家id
     * @return 商家商品列表
     */
    @GetMapping("/findItemList")
    public List<TbItem> findSellerList(String sellerId){
        List<TbItem> sellerList = itemSearchService.findSellerList(sellerId);

        return sellerList;
    }
    @GetMapping("/toCart")
    public List<TbItem> shopCart(String sellerId){
        List<TbItem> sellerList = itemSearchService.findSellerList(sellerId);
        return sellerList;
    }

}
