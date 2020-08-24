package com.qrabbit.youpinghui.service;

import com.qrabbit.youpinghui.pojo.TbSeller;

import java.util.List;


/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SellerService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeller> findAll();

	//按 id 查 seller

	public TbSeller findSellerById(String sellerid);
	
	//模糊查询

	public List<TbSeller> findSellerByExample(TbSeller tbSeller);

}
