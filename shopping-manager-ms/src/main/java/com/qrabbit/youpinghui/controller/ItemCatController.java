package com.qrabbit.youpinghui.controller;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.Result;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbItemCat;
import com.qrabbit.youpinghui.pojo.TbSeller;
import com.qrabbit.youpinghui.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat-ms")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public ResultPageSeller findPage(int page, int rows){
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.add(itemCat);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.update(itemCat);
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
	public TbItemCat findOne(Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			itemCatService.delete(ids);
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
	public ResultPageSeller search(@RequestBody TbItemCat itemCat, int page, int rows  ){
		return itemCatService.findPage(itemCat, page, rows);		
	}
	
	@RequestMapping("/findByParentId")
	public List<TbItemCat> findByParentId(Long parentId){
		System.out.println(parentId);
		List<TbItemCat> byParentId = itemCatService.findByParentId(parentId);
		for (TbItemCat tbItemCat : byParentId) {
			System.out.println(tbItemCat);
		}
		return itemCatService.findByParentId(parentId);
	}


	@GetMapping("/findSellerByPages") //分页
	public ResultPageSeller findSellerByPages(int pageNum, int pageSize)
	{

		//  PageHelper.startPage(pageNum,pageSize);
		//  List<News> list =  newsService.findAllnews();
		//PageInfo pageInfo = new PageInfo(list);

		System.out.println("findNewsByPages------------->");

		PageHelper.startPage(pageNum,pageSize);
		//Page<E> extends ArrayList<E> 拥有pageinfo list 的 封装
		Page<TbItemCat> page = (Page<TbItemCat>) itemCatService.findAll();
		ResultPageSeller resultPage = new ResultPageSeller();
		//从page 中 获取  list
		resultPage.setRow(page.getResult());
		//从page 中 获取  总数
		resultPage.setTotal(page.getTotal());
		System.out.println(resultPage.getTotal()+"------total");

		return  resultPage;
	}

}
