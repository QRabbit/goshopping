package com.qrabbit.youpinghui.controller;

import java.util.List;

import javax.jms.*;

import com.alibaba.fastjson.JSON;
import com.qrabbit.youpinghui.activemq.JmsConfig;
import com.qrabbit.youpinghui.pojo.Result;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbGoods;
import com.qrabbit.youpinghui.pojo.TbItem;
import com.qrabbit.youpinghui.pojo.pojogroup.Goods;
import com.qrabbit.youpinghui.service.GoodsService;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.listener.Topic;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods-ms")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	/**
	 * 返回全部列表 delete id  不为1的
	 * @return
	 */
	@RequestMapping("/findAllwithoutOne")
	public List<TbGoods> findAllwithoutOne()
	{
		System.out.println("findAllwithoutOne");
		return goodsService.findAllwithoutOne();
	}

	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public ResultPageSeller findPage(int page, int rows){
		System.out.println("1111111111111");
		ResultPageSeller r = goodsService.findPage(page, rows);
		System.out.println(r.getTotal());
		List<TbGoods> list =  r.getRow();
		for (TbGoods tbGoods : list) {
			System.out.println(tbGoods.getGoodsName());
		}
		return r;
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		System.out.println("#######"+goods.getGoods().getGoodsName()+"#######");
		System.out.println("#######"+goods.getGoodsDesc().getItemImages());
		System.out.println("#######"+goods.getGoodsDesc().getSpecificationItems());
		System.out.println("#######"+goods.getGoodsDesc().getCustomAttributeItems());
		
		try {
			// 获得商家信息:
			//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			String sellerId = "yqtech"; //暂时设定
			goods.getGoods().setSellerId(sellerId);
			
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@PostMapping("/update")
	public Result update(@RequestBody Goods goods){

		System.out.println("update--------------------------->");

		// 获得商家信息:
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		String sellerId = "yqtech"; //暂时设定
		Goods goods2 = goodsService.findOne(goods.getGoods().getId());
		if(!sellerId.equals(goods2.getGoods().getSellerId()) || !sellerId.equals(goods.getGoods().getSellerId())){
			return new Result(false, "非法操作");
		}
		
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		System.out.println("delete------>goods");

		for (Long id : ids) {
			System.out.println(id);
		}


		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
//		/**
//	 * 查询+分页
//	 * @param brand
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
	@RequestMapping("/search")
	public ResultPageSeller search(@RequestBody TbGoods goods, int page, int rows  ){
		
		//String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		String sellerId = "yqtech"; //暂时设定
		
		goods.setSellerId(sellerId);
		System.out.println("controller");
		ResultPageSeller resultPageSeller= goodsService.findPage(goods, page, rows);
		List<TbGoods> row = resultPageSeller.getRow();
		for (TbGoods o : row) {
			System.out.println(o);
		}
		return goodsService.findPage(goods, page, rows);
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids,String status){
		System.out.println("ids: " + ids);
		System.out.println("status: " + status);
		try {
			goodsService.updateStatus(ids, status);  //1. 更改数据库中tb_goods表中audit_status 0-->1
			
			if("1".equals(status)){//如果是审核通过 
				//*****导入到索引库
				//得到需要导入的SKU列表    根据spu的id查询对应的sku的商品
				List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);
				//2. 导入到solr
				//itemSearchService.importList(itemList);	
				final String jsonString = JSON.toJSONString(itemList);//转换为json传输
				
				System.out.println("Goods conroller: " + jsonString);
				
				Topic topicSolrDestination = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
				jmsTemplate.send(topicSolrDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						TextMessage textMessage = session.createTextMessage(jsonString);
						System.out.println("textMessage:"+textMessage.getText());
						return textMessage;
					}
				});
				
				//****3. 生成商品详细页 freemarker
				for(final Long goodsId:ids){
					Topic topicPageDestination = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
					jmsTemplate.send(topicPageDestination, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {							
							return session.createTextMessage(goodsId+"");
						}
					});					
				}
				
			}		
			
			return new Result(true, "修改状态成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改状态失败");
		}
	}
	
}
