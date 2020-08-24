package com.qrabbit.youpinghui.service.impl;

import com.qrabbit.youpinghui.mapper.TbItemMapper;
import com.qrabbit.youpinghui.pojo.TbItem;
import com.qrabbit.youpinghui.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemMapper itemMapper;

    @Override
    public List<TbItem> findAllitems() {
        return itemMapper.selectByExample(null);
    }
}
