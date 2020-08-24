package com.qrabbit.youpinghui.serivce;


import com.qrabbit.youpinghui.document.EsItem;
import com.qrabbit.youpinghui.pojo.TbItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    public List<EsItem> findAll();

    List<EsItem> findbykeyword(String keyword);
}
