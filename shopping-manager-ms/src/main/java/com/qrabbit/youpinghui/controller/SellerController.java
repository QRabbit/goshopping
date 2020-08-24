package com.qrabbit.youpinghui.controller;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbSeller;
import com.qrabbit.youpinghui.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
	@Autowired
	private SellerService sellerService;
	

	@GetMapping("/findAll")
	public List<TbSeller> findAll(){
		return sellerService.findAll();
	}


	@GetMapping("/findSellerById/{sellerid}")
	public TbSeller findSellerById(@PathVariable String sellerid)
	{

		System.out.println("controller findsellerbyid--->"+sellerid);
		return  sellerService.findSellerById(sellerid);
	}

	@GetMapping("/findSellerByExample")
	public List<TbSeller> findSellerByExample(String name,String nickName)
	{
		System.out.println("findSellerByExample-->");
		TbSeller tbSeller = new TbSeller();
		tbSeller.setName(name);
		tbSeller.setNickName(nickName);
		System.out.println("controller findSellerByExample--->"+tbSeller.getName()+"--?"+tbSeller.getNickName());
		return  sellerService.findSellerByExample(tbSeller);
	}


	@GetMapping("/findSellerByExamples")
	public List<TbSeller> findSellerByExamples(String name)
	{
		System.out.println("findSellerByExample-->");
		TbSeller tbSeller = new TbSeller();
		tbSeller.setName(name);
		System.out.println("controller findSellerByExample--->"+tbSeller.getName()+"--?"+tbSeller.getNickName());
		return  sellerService.findSellerByExample(tbSeller);
	}

	@GetMapping("/findSellerByExampless")
	public List<TbSeller> findSellerByExampless(String nickName)
	{
		System.out.println("findSellerByExample-->");
		TbSeller tbSeller = new TbSeller();
		tbSeller.setNickName(nickName);
		System.out.println("controller findSellerByExample--->"+tbSeller.getName()+"--?"+tbSeller.getNickName());
		return  sellerService.findSellerByExample(tbSeller);
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
		Page<TbSeller> page = (Page<TbSeller>) sellerService.findAll();
		ResultPageSeller resultPage = new ResultPageSeller();
		//从page 中 获取  list
		resultPage.setRow(page.getResult());
		//从page 中 获取  总数
		resultPage.setTotal(page.getTotal());
		System.out.println(resultPage.getTotal()+"------total");

		return  resultPage;
	}
	

}
