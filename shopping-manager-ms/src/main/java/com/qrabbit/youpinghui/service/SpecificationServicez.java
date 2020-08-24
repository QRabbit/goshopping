package com.qrabbit.youpinghui.service;

import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.Spectification;
import com.qrabbit.youpinghui.pojo.TbSpecification;

import java.util.List;
import java.util.Map;



/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationServicez {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResultPageSeller findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Spectification specification);
	
	
	/**
	 * 修改
	 */
	public void update(Spectification specification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Spectification findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResultPageSeller findPage(TbSpecification specification, int pageNum, int pageSize);
	
	public List<Map> selectOptionList();
}
