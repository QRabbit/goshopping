package com.qrabbit.youpinghui.controller;
import java.util.List;
import java.util.Map;

import com.qrabbit.youpinghui.pojo.Result;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.Spectification;
import com.qrabbit.youpinghui.pojo.TbSpecification;
import com.qrabbit.youpinghui.service.SpecificationService;
import com.qrabbit.youpinghui.service.SpecificationServicez;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specification-Ms")
public class SpecificationControllerz {

	@Autowired
	private SpecificationServicez specificationService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){
		
		return specificationService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public ResultPageSeller findPage(int page, int rows){
		return specificationService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Spectification specification){
		try {
			specificationService.add(specification);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param specification
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Spectification specification){
		try {
			specificationService.update(specification);
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
	public Spectification findOne(Long id){
		return specificationService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			specificationService.delete(ids);
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
	public ResultPageSeller search(@RequestBody TbSpecification specification, int page, int rows  ){
		System.out.println("###############################################");
		System.out.println("##"+page+"##");
		System.out.println("##"+specification+"##");
		System.out.println("#############################################");
		return specificationService.findPage(specification, page, rows);		
	}
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return specificationService.selectOptionList();
	}
	
}
