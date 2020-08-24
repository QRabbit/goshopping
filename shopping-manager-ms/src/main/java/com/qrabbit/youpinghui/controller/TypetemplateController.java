package com.qrabbit.youpinghui.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbBrand;
import com.qrabbit.youpinghui.pojo.TbTypeTemplate;
import com.qrabbit.youpinghui.service.TypetemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypetemplateController {

    @Autowired
    TypetemplateService typetemplateService;

    @GetMapping("/findAllTypetemplate")
    public List<TbTypeTemplate> findAllTbtypetemplate(){
        System.out.println("findAllTypetemplate-------->");
        return  typetemplateService.findAllTypetemplate();
    }


    //增加
    @PostMapping("/addTypes")
    public  int  addTypes(@RequestBody TbTypeTemplate tbTypeTemplate){
        System.out.println("addTypes----->");
        System.out.println(tbTypeTemplate.getName());
        return typetemplateService.addTypes(tbTypeTemplate);
    }


    //findbyid
    @GetMapping("/findByid")
    public TbTypeTemplate findByid(Long id)
    {
        return typetemplateService.findByid(id);
    }

    //修改
    @PostMapping("/updateType")
    public  int updateType(@RequestBody TbTypeTemplate tbTypeTemplate)
    {
        return typetemplateService.updateType(tbTypeTemplate);
    }

    ////模糊查询
    @GetMapping("/searchlike")
    public List<TbTypeTemplate> searchlike(String text)
    {
        return typetemplateService.searchlike(text);

    }

    //批量删除
    @GetMapping("/deleteMany")
    public int deleteMany(Long ids[])
    {
        return typetemplateService.deleteMany(ids);
    }



    @GetMapping("/findTypeByPages") //分页
    public ResultPageSeller findSellerByPages(int pageNum, int pageSize)
    {

        System.out.println("findTypeByPages------------->");

        PageHelper.startPage(pageNum,pageSize);
        //Page<E> extends ArrayList<E> 拥有pageinfo list 的 封装
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typetemplateService.findAllTypetemplate();
        ResultPageSeller resultPage = new ResultPageSeller();
        //从page 中 获取  list
        resultPage.setRow(page.getResult());
        //从page 中 获取  总数
        resultPage.setTotal(page.getTotal());
        System.out.println(resultPage.getTotal()+"------total");

        return  resultPage;
    }


}
