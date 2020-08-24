package com.qrabbit.youpinghui.service;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;



/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface TypeTemplateServicez {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResultPageSeller findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
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
	public ResultPageSeller findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize);
	

	//根据模板ID查询规格列表
	public List<Map> findSpecList(Long id);
}
