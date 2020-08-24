package com.qrabbit.youpinghui.service;


import com.qrabbit.youpinghui.pojo.TbTypeTemplate;

import java.util.List;

public interface TypetemplateService {

    List<TbTypeTemplate> findAllTypetemplate();

    int addTypes(TbTypeTemplate tbTypeTemplate);

    TbTypeTemplate findByid(Long id);

    int updateType(TbTypeTemplate tbTypeTemplate);

    List<TbTypeTemplate> searchlike(String text);

    int deleteMany(Long[] ids);
}
