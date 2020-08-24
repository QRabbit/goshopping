package com.qrabbit.youpinghui.pojo.pojogroup;

import com.qrabbit.youpinghui.pojo.TbGoods;
import com.qrabbit.youpinghui.pojo.TbGoodsDesc;
import com.qrabbit.youpinghui.pojo.TbItem;

import java.io.Serializable;
import java.util.List;



/**
 * 商品的组合实体类
 * @author jt
 *
 */

public class Goods implements Serializable{
	
	private TbGoods goods; // 商品信息   spu   tb_goods
	private TbGoodsDesc goodsDesc; // 商品扩展信息  tb_goods_desc
	
	private List<TbItem> itemList; // SKU的列表信息   tb_item
	
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}


	@Override
	public String toString() {
		return "Goods{" +
				"goods=" + goods +
				", goodsDesc=" + goodsDesc +
				", itemList=" + itemList +
				'}';
	}
}
