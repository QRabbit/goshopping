package com.qrabbit.youpinghui.service.impl;

import java.util.List;

import com.qrabbit.youpinghui.mapper.TbSellerMapper;
import com.qrabbit.youpinghui.pojo.TbSeller;
import com.qrabbit.youpinghui.pojo.TbSellerExample;
import com.qrabbit.youpinghui.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional  //事务环境
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	@Override
	public TbSeller findSellerById(String sellerid) {
		TbSeller tbSeller = sellerMapper.selectByPrimaryKey(sellerid);
		System.out.println("findSellerById---->"+tbSeller.getSellerId());
		return tbSeller;
	}

	@Override
	public List<TbSeller> findSellerByExample(TbSeller tbSeller) {
		System.out.println("findSellerByExample----> service"+tbSeller.getName());
		TbSellerExample example = new TbSellerExample();
		TbSellerExample.Criteria criteria = example.createCriteria();
		if (tbSeller.getName()!=null&&!"".equals(tbSeller.getName()))
		{
			criteria.andNameLike("%"+tbSeller.getName()+"%");
		}
		if (tbSeller.getNickName()!=null&&!"".equals(tbSeller.getNickName()))
		{
			criteria.andNickNameLike("%"+tbSeller.getNickName()+"%");
		}

		List<TbSeller> tbSellerslist = sellerMapper.selectByExample(example);
		for (TbSeller seller : tbSellerslist) {
			System.out.println("seller---------->"+seller.getNickName()+"   "+seller.getName());
		}

		return tbSellerslist ;
	}


}
