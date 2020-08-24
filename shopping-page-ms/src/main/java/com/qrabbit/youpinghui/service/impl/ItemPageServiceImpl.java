package com.qrabbit.youpinghui.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qrabbit.youpinghui.mapper.TbGoodsDescMapper;
import com.qrabbit.youpinghui.mapper.TbGoodsMapper;
import com.qrabbit.youpinghui.mapper.TbItemCatMapper;
import com.qrabbit.youpinghui.mapper.TbItemMapper;
import com.qrabbit.youpinghui.pojo.TbGoods;
import com.qrabbit.youpinghui.pojo.TbGoodsDesc;
import com.qrabbit.youpinghui.pojo.TbItem;
import com.qrabbit.youpinghui.pojo.TbItemExample;
import com.qrabbit.youpinghui.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	//@Value("${pagedir}")
	private String pagedir = "d:\\item\\";
	
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public boolean genItemHtml(Long goodsId) {
		
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		
		try {
			Template template = configuration.getTemplate("item.ftl");
			//创建数据模型
			Map dataModel=new HashMap<>();
			//1.商品主表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);

			System.out.println("goods:");
			System.out.println(goods);


			dataModel.put("goods", goods);
			//2.商品扩展表数据
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);

			System.out.println("goodsDesc:");
			System.out.println(goodsDesc);


			dataModel.put("goodsDesc", goodsDesc);
			//3.读取商品分类
			
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);


			System.out.println("itemCat1");
			System.out.println(itemCat1);
			System.out.println("itemCat2");
			System.out.println(itemCat2);
			System.out.println("itemCat3");
			System.out.println(itemCat3);

			//4.读取SKU列表
			TbItemExample example=new TbItemExample();
			TbItemExample.Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);//SPU ID
			criteria.andStatusEqualTo("1");//状态有效			
			example.setOrderByClause("is_default desc");//按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
			
			List<TbItem> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);
			
			Writer out=new FileWriter(pagedir+goodsId+".html");
			
			template.process(dataModel, out);//输出
			out.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
	}

	@Override
	public boolean deleteItemHtml(Long[] goodsIds) {
		try {
			for(Long goodsId:goodsIds){
				new File(pagedir+goodsId+".html").delete();		
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}

}
