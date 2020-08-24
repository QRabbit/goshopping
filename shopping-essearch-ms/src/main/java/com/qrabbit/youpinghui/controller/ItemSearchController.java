package com.qrabbit.youpinghui.controller;


import com.alibaba.fastjson.JSON;
import com.qrabbit.youpinghui.document.EsItem;
import com.qrabbit.youpinghui.serivce.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item-essearch")
public class ItemSearchController {


    @Autowired
    ItemService itemService;

//
//    @GetMapping("/findbykey")
//    public List<EsItem> findbykeyword(String keyword) {
////        Page<EsItem> apple = esItemRepository.findbykeyword(keyword,null);
//////        System.out.println("条数:"+apple.getTotalElements());
//////        for (EsItem esItem : apple) {
//////
//////            System.out.println(esItem);
//////        }

    @GetMapping("/findAllitems")
    public List<EsItem> findbykeyword()
    {
        return itemService.findAll();
    }

    @GetMapping("/findbykeyword")
    public List<EsItem> findbykeyword(String keyword)
    {
        System.out.println("keyword:"+keyword);
        if (keyword!=null&&!" ".equals(keyword))
        {
            System.out.println("11");
            return itemService.findbykeyword(keyword);
        }
        else
        {
            return itemService.findAll();
        }
    }

}
