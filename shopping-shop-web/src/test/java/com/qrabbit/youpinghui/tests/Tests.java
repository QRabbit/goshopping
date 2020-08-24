package com.qrabbit.youpinghui.tests;

import com.qrabbit.youpinghui.pojo.TbItem;
import com.qrabbit.youpinghui.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.util.List;

@SpringBootTest
public class Tests {

    @Autowired
    SolrTemplate solrTemplate;

    @Autowired
    ItemService itemService;


    @Test
    public void Tests(){
        System.out.println("测试 测试");
    }

    @Test
    public void Testss(){
        List<TbItem> allitems = itemService.findAllitems();
        int size = allitems.size();
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_title").isNotNull();
        query.addCriteria(criteria);
        query.setRows(Integer.MAX_VALUE);
        Page<TbItem> collection1 = solrTemplate.query("collection1", query, TbItem.class);

        List<TbItem> content = collection1.getContent();
       System.out.println(content.size()+"<<<<<<<<<<<<<<<<<<<<<<<<");
//        for (TbItem item : content) {
//            System.out.println(item);
//        }

    }
}
