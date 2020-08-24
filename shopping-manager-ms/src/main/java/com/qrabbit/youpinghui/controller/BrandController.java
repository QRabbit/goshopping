package com.qrabbit.youpinghui.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbBrand;
import com.qrabbit.youpinghui.pojo.TbSeller;
import com.qrabbit.youpinghui.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("/findAllBrand")
    public List<TbBrand> findAllBrand(){
        System.out.println("controller---->findAllBrand");
        List<TbBrand> list =  brandService.findAllBrand();
        for (TbBrand tbBrand : list) {
            System.out.println(tbBrand.getName());
        }
        return list;
    }

    @GetMapping("/findBrandIdToUpdate")
    public TbBrand findBrandIdToUpdate(long id){
        System.out.println("controller---->findAllBrand");
        return brandService.findBrandIdToUpdate(id);
    }

    @PostMapping("/findBrandToUpdate")
    public int findBrandToUpdate(@RequestBody TbBrand tbBrand)
    {
        return brandService.findBrandToUpdate(tbBrand);
    }

    @PostMapping("/toAddBrand")
    public  int toAddBrand(@RequestBody TbBrand tbBrand)
    {
        return brandService.toAddBrand(tbBrand);
    }

    @GetMapping("/DeleteManyBrand")
    public  int DeleteManyBrand(long ids[])
    {
        for (long id : ids) {
            System.out.println("-----"+id);
        }
        return brandService.DeleteManyBrand(ids);
    }


    @GetMapping("/findBrandByPages") //分页
    public ResultPageSeller findSellerByPages(int pageNum, int pageSize)
    {

        System.out.println("findNewsByPages------------->");

        PageHelper.startPage(pageNum,pageSize);
        //Page<E> extends ArrayList<E> 拥有pageinfo list 的 封装
        Page<TbBrand> page = (Page<TbBrand>) brandService.findAllBrand();
        ResultPageSeller resultPage = new ResultPageSeller();
        //从page 中 获取  list
        resultPage.setRow(page.getResult());
        //从page 中 获取  总数
        resultPage.setTotal(page.getTotal());
        System.out.println(resultPage.getTotal()+"------total");

        return  resultPage;
    }

    //
    @GetMapping("/findallbrandlist")
    public List<Map> findallbrandlist(){
        System.out.println("------------123123123213123123");

        return  brandService.findallbrandlist();
    }

}
