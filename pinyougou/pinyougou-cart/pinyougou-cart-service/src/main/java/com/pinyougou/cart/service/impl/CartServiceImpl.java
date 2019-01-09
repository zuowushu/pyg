package com.pinyougou.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = CartService.class)
public class CartServiceImpl implements CartService {

    //所有用户的购物车数据对应在redis中的key名称
    private static final String CART_LIST = "CART_LIST";

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //判断是否是否存在和状态是已启用；
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!"1".equals(item.getStatus())) {
            throw new RuntimeException("商品非法");
        }

        Cart cart = findCartInCartListBySellerId(cartList, item.getSellerId());

        if (cart == null) {
            //商家(cart)不存在；创建一个商家（cart）添加购买的商品到其商品列表；
            if (num > 0) {
                cart = new Cart();
                cart.setSellerId(item.getSellerId());
                cart.setSeller(item.getSeller());
                //创建订单商品列表
                List<TbOrderItem> orderItemList = new ArrayList<>();

                TbOrderItem orderItem = createOrderItem(item, num);

                orderItemList.add(orderItem);

                cart.setOrderItemList(orderItemList);
                cartList.add(cart);
            } else {
                throw new RuntimeException("购买数量非法");
            }
        } else {
            TbOrderItem orderItem = findOrderItemByItemId(cart.getOrderItemList(), itemId);
            if (orderItem != null) {
                //商家（cart）存在；商品(tbOrderItem)存在的话；那么购买数量叠加并更新总价；
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));

                //如果更新后商品购买数量为0则将该商品从商品列表中移除
                if (orderItem.getNum() == 0) {
                    cart.getOrderItemList().remove(orderItem);
                }
                //如果商家的商品列表为0则将该商家对应的购物车从购物车列表移除
                if (cart.getOrderItemList().size() == 0) {
                    cartList.remove(cart);
                }
            } else {
                //商家（cart）存在；商品(tbOrderItem)不存在的话；重新创建商品加入商品列表
                if (num > 0) {
                    TbOrderItem tbOrderItem = createOrderItem(item, num);
                    cart.getOrderItemList().add(tbOrderItem);
                } else {
                    throw new RuntimeException("购买数量非法");
                }
            }
        }
        return cartList;
    }

    @Override
    public List<Cart> findCartListInRedisByUsername(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps(CART_LIST).get(username);
        if (cartList != null) {
            return cartList;
        }
        return new ArrayList<>();
    }

    @Override
    public void saveCartListInRedisByUsername(List<Cart> cartList, String username) {
        redisTemplate.boundHashOps(CART_LIST).put(username, cartList);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cookieCartList, List<Cart> redisCartList) {
        for (Cart cart : cookieCartList) {
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                addItemToCartList(redisCartList, orderItem.getItemId(), orderItem.getNum());
            }
        }
        return redisCartList;
    }

    /**
     * 在订单商品列表中根据商品id查询订单商品
     *
     * @param orderItemList 订单商品列表
     * @param itemId        商品sku id
     * @return 订单商品
     */
    private TbOrderItem findOrderItemByItemId(List<TbOrderItem> orderItemList, Long itemId) {
        if (orderItemList != null && orderItemList.size() > 0) {
            for (TbOrderItem orderItem : orderItemList) {
                if (itemId.equals(orderItem.getItemId())) {
                    return orderItem;
                }
            }
        }
        return null;
    }

    /**
     * 创建订单商品
     *
     * @param item 商品sku
     * @param num  购买数量
     * @return 订单商品
     */
    private TbOrderItem createOrderItem(TbItem item, Integer num) {
        TbOrderItem orderItem = new TbOrderItem();
        orderItem.setNum(num);
        orderItem.setItemId(item.getId());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setPrice(item.getPrice());
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setTitle(item.getTitle());
        orderItem.setPicPath(item.getImage());
        //总价 = 单价*数量
        orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));
        return orderItem;
    }

    /**
     * 根据商家id获取购物车列表中对应的购物车对象
     *
     * @param cartList 购物车列表
     * @param sellerId 商家id
     * @return 购物车对象
     */
    private Cart findCartInCartListBySellerId(List<Cart> cartList, String sellerId) {
        if (cartList != null && cartList.size() > 0) {
            for (Cart cart : cartList) {
                if (sellerId.equals(cart.getSellerId())) {
                    return cart;
                }
            }
        }
        return null;
    }
}
