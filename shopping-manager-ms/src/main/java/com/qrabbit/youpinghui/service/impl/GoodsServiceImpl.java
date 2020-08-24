package com.qrabbit.youpinghui.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qrabbit.youpinghui.mapper.*;
import com.qrabbit.youpinghui.pojo.*;
import com.qrabbit.youpinghui.pojo.pojogroup.Goods;
import com.qrabbit.youpinghui.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

//	@Autowired
//	SolrTemplate solrTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResultPageSeller findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbGoodsExample example = new TbGoodsExample();
		TbGoodsExample.Criteria criteria =example.createCriteria();
		criteria.andIsDeleteIsNull();
		Page<TbGoods> page= (Page<TbGoods>) goodsMapper.selectByExample(example);
		return new ResultPageSeller(page.getTotal(), page.getResult());
	}

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbSellerMapper sellerMapper;
	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		goods.getGoods().setAuditStatus("0"); // 设置审核的状态
		
		goodsMapper.insert(goods.getGoods()); // 插入商品信息   tb_goods   spu（回填主键id到pojo对象中）
		
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());  //设置tb_goods_desc表的外键和主键
		
		goodsDescMapper.insert(goods.getGoodsDesc()); // 插入商品的扩展信息
		
		setItemList(goods);//保存sku信息
	}
	
	
	private void setItemList(Goods goods){
		System.out.println("是否启用规格：" + goods.getGoods().getIsEnableSpec());
		if("1".equals(goods.getGoods().getIsEnableSpec())){
			// 启用规格
			// 保存SKU列表的信息:
			for(TbItem item:goods.getItemList()){
				// 设置SKU的数据：
				// item.setTitle(title);
				String title = goods.getGoods().getGoodsName();
				Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
				for (String key : map.keySet()) {
					title+= " "+map.get(key);
				}
				item.setTitle(title);
				
				setValue(goods,item);

				itemMapper.insert(item);
			}
		}else{
			// 没有启用规格
			TbItem item = new TbItem();
			
			item.setTitle(goods.getGoods().getGoodsName());
			
			item.setPrice(goods.getGoods().getPrice());
			
			item.setNum(999);
			
			item.setStatus("0");
			
			item.setIsDefault("1");
			item.setSpec("{}");
			
			setValue(goods,item);
			itemMapper.insert(item);
		}
	}

	private void setValue(Goods goods,TbItem item){
		
		List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(),Map.class);

		if(imageList.size()>0){
			item.setImage((String)imageList.get(0).get("url"));
		}
		
		// 保存三级分类的ID:
		item.setCategoryid(goods.getGoods().getCategory3Id());
		item.setCreateTime(new Date());
		item.setUpdateTime(new Date());
		// 设置商品ID
		item.setGoodsId(goods.getGoods().getId());
		item.setSellerId(goods.getGoods().getSellerId());
		TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());

		item.setCategory(itemCat.getName());

		TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());

		item.setBrand(brand.getName());

		TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());

		item.setSeller(seller.getNickName());
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		// 修改商品信息
		goods.getGoods().setAuditStatus("0");
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		// 修改商品扩展信息:
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		// 修改SKU信息:
		// 先删除，再保存:
		// 删除SKU的信息:
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());
		itemMapper.deleteByExample(example);
		// 保存SKU的信息
		setItemList(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods = new Goods();
		// 查询商品表的信息
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		// 查询商品扩展表的信息
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setGoodsDesc(tbGoodsDesc);
		
		// 查询SKU表的信息:
		TbItemExample example = new TbItemExample();
		TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		goods.setItemList(list);
		
		return goods;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
//			goodsMapper.deleteByPrimaryKey(id);
			TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(tbGoods);
		}		
	}
	
	
		@Override
	public ResultPageSeller findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
			System.out.println(pageNum+"_____"+pageSize);
		TbGoodsExample example=new TbGoodsExample();
		TbGoodsExample.Criteria criteria = example.createCriteria();
		
		//criteria.andIsDeleteIsNull();
//			criteria.andIsDeleteIsNull();
		if(goods!=null){
			System.out.println("goods!=null");
			System.out.println(goods);
//			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
//			System.out.println("设置sellerId: " + goods.getSellerId());
//			criteria.andSellerIdEqualTo(goods.getSellerId());
//		}
		if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
			System.out.println("getGoodsName"+goods.getGoodsName());
			criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
		}
		if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
			System.out.println("getAuditStatus"+goods.getAuditStatus());
			criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
		}
		if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
			System.out.println("getIsMarketable"+goods.getIsMarketable());
			criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
		}
		if(goods.getCaption()!=null && goods.getCaption().length()>0){
			System.out.println("getCaption"+goods.getCaption());
			criteria.andCaptionLike("%"+goods.getCaption()+"%");
		}
		if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
			System.out.println("getSmallPic"+goods.getSmallPic());
			criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
		}
		if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
			System.out.println("getIsEnableSpec"+goods.getIsEnableSpec());
			criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
		}
//		if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
//			System.out.println("getIsDelete"+goods.getIsDelete());
//			criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
//		}

			criteria.andIsDeleteIsNull();
		}


		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);
//		System.out.println("##### "+page.getResult());
		System.out.println(page.getTotal()); ///////////////
		return new ResultPageSeller(page.getTotal(), page.getResult());
	}

		@Override
		public void updateStatus(Long[] ids, String status) {
			for (Long id : ids) {
				TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
				
				tbGoods.setAuditStatus(status);
				
				goodsMapper.updateByPrimaryKey(tbGoods);
			}
		}

		@Override
		public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] goodsIds, String status) {
			TbItemExample examples=new TbItemExample();
			TbItemExample.Criteria criterias = examples.createCriteria();
			criterias.andGoodsIdIn(Arrays.asList(goodsIds));
			List<TbItem> tbItemss = itemMapper.selectByExample(examples);
			for (TbItem itemss : tbItemss) {
				itemss.setStatus("1");
			}
			for (TbItem itemss : tbItemss) {
				itemMapper.updateByPrimaryKeySelective(itemss);
			}


			System.out.println("Goods Id列表：" + goodsIds);
			System.out.println("状态：" + status);
			TbItemExample example=new TbItemExample();
			TbItemExample.Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(status);//状态
			criteria.andGoodsIdIn(Arrays.asList(goodsIds));//指定条件：SPUID集合
			List<TbItem> tbItems = itemMapper.selectByExample(example);
			for (TbItem tbItem : tbItems) {
				System.out.println("findItemListByGoodsIdListAndStatus"+tbItem.getTitle());
			}
			return itemMapper.selectByExample(example);
		}

	@Override
	public List<TbGoods> findAllwithoutOne() {
		TbGoodsExample example = new TbGoodsExample();
		TbGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		List<TbGoods> list = goodsMapper.selectByExample(example);
		System.out.println("findAllwithoutOne:"+list.size());
		for (TbGoods tbGoods : list) {
			System.out.println(tbGoods.getGoodsName()+"<<<");
		}
		return list;
	}

}
