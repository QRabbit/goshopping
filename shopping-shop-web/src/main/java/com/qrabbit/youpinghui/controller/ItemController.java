package com.qrabbit.youpinghui.controller;

import com.qrabbit.youpinghui.pojo.TbItem;
import com.qrabbit.youpinghui.service.ItemService;
import com.qrabbit.youpinghui.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    ItemService  itemService;
    @Autowired
    SolrTemplate solrTemplate;


    @GetMapping("/findAllItems")
    public List<TbItem> findAllItems(){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_title").isNotNull();
        query.addCriteria(criteria);
        //query.setRows(Integer.MAX_VALUE);
        query.setRows(10);
        Page<TbItem> collection1 = solrTemplate.query("collection1", query, TbItem.class);
        List<TbItem> content = collection1.getContent();
        System.out.println(content.size());
        return content;
    }

    @GetMapping("/search")
    public List<TbItem> findAllItems(String text){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_title").is(text);
        query.addCriteria(criteria);
//        query.setRows(Integer.MAX_VALUE);
        query.setRows(10);
        Page<TbItem> collection1 = solrTemplate.query("collection1", query, TbItem.class);
        List<TbItem> content = collection1.getContent();
        System.out.println(content.size());
        return content;
    }


}
