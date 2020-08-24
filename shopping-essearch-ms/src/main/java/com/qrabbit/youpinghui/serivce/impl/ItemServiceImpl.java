package com.qrabbit.youpinghui.serivce.impl;


import com.qrabbit.youpinghui.document.EsItem;
import com.qrabbit.youpinghui.repository.EsItemRepository;
import com.qrabbit.youpinghui.serivce.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private EsItemRepository esItemRepository;

    @Override
    public List<EsItem> findAll() {
        Iterator<EsItem> iterator = esItemRepository.findAll().iterator();
        List<EsItem> list = new ArrayList<>();
        while (iterator.hasNext())
        {
            EsItem esItem =iterator.next();
            list.add(esItem);
        }
        for (EsItem esItem : list) {

            System.out.println(esItem);
        }
        return list;
    }

    @Override
    public List<EsItem> findbykeyword(String keyword) {
        Page<EsItem> pages = esItemRepository.findbykeyword(keyword,null);
        System.out.println("条数:"+pages.getTotalElements());
        List<EsItem> list = pages.getContent();
        return list;
    }
}
