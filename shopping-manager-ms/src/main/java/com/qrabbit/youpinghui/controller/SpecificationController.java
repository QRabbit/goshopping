package com.qrabbit.youpinghui.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.*;
import com.qrabbit.youpinghui.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spc")
public class SpecificationController {
    @Autowired
    SpecificationService specificationService;

    @GetMapping("/findAllSpecification")
    public List<TbSpecification> findAllSpecification(){
        System.out.println("findAllSpecification--->");
        List<TbSpecification> allSpectification = specificationService.findAllSpectification();
        return allSpectification;
    }

    @GetMapping("/forSearch")
    public List<TbSpecification> forSearch(String seachtext)
    {
        System.out.println(seachtext+"--------->");
        List<TbSpecification> list =  specificationService.findbyExample(seachtext);
        return list;
    }

    //批量删除
    @GetMapping("/DeleteManySpec")
    public int DeleteManySpec(Long []ids)
    {
        System.out.println("--asd----");
        for (Long id : ids) {
            System.out.println(id+"--------->");
        }
        return specificationService.DeleteManySpec(ids);
    }

    //增加
    @RequestMapping("/findSpectificationAdd")
    public int findSpectificationAdd(@RequestBody Spectification spectifications)
    {
        return specificationService.AddSpc(spectifications);
    }

    //查询修改

    @GetMapping("/findBySpcId")
    public Spectification findBySpcId(Long id)
    {
        System.out.println("findBySpcId"+id);
        return specificationService.findBySpcId(id);
    }

    //修改
    @PostMapping("/findToUpdate")
    public  int findToUpdate(@RequestBody Spectification spectification)
    {
        return specificationService.findToUpdate(spectification);
    }




    @GetMapping("/findSpcByPages") //分页
    public ResultPageSeller findSellerByPages(int pageNum, int pageSize)
    {

        System.out.println("findSpcByPages------------->");

        PageHelper.startPage(pageNum,pageSize);
        //Page<E> extends ArrayList<E> 拥有pageinfo list 的 封装
        Page<TbSpecification> page = (Page<TbSpecification>) specificationService.findAllSpectification();
        ResultPageSeller resultPage = new ResultPageSeller();
        //从page 中 获取  list
        resultPage.setRow(page.getResult());
        //从page 中 获取  总数
        resultPage.setTotal(page.getTotal());
        System.out.println(resultPage.getTotal()+"------total");

        return  resultPage;
    }

    //
    @GetMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        System.out.println("selectOptionList--------->");
        return specificationService.selectOptionList();
    }

    //搜索
    @GetMapping("/search")
    public  ResultPageSeller search(String text){
        System.out.println("00000000000000000000000");
        System.out.println(text);
        PageHelper.startPage(1,3);
        Page<TbSpecification> page = (Page<TbSpecification>)specificationService.search(text);
        ResultPageSeller resultPage = new ResultPageSeller();
        //从page 中 获取  list
        resultPage.setRow(page.getResult());
        //从page 中 获取  总数
        resultPage.setTotal(page.getTotal());
        System.out.println(resultPage.getTotal()+"------total");
        return resultPage;
    }


}
