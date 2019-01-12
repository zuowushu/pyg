package com.pinyougou.vo;

import com.pinyougou.pojo.TbSeckillGoods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * SecKills class
 *
 * @Author: lonelywind
 * @Date: 2019/1/12 0:17
 */
public class SecKillGoods implements Serializable {
    private Long id;

    private BigDecimal money;

    private String seller;

    private Date createTime;

    private Date payTime;

    private String status;


    private List<TbSeckillGoods> seckillGoods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TbSeckillGoods> getSeckillGoods() {
        return seckillGoods;
    }

    public void setSeckillGoods(List<TbSeckillGoods> seckillGoods) {
        this.seckillGoods = seckillGoods;
    }
}
