package com.qrabbit.youpinghui.service;


import com.qrabbit.youpinghui.pojo.Spectification;
import com.qrabbit.youpinghui.pojo.TbSpecification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    public List<TbSpecification> findAllSpectification();

    List<TbSpecification> findbyExample(String seachtext);

    int DeleteManySpec(Long[] ids);

    int AddSpc(Spectification spectifications);

    Spectification findBySpcId(Long id);

    int findToUpdate(Spectification spectification);

    // int getSpectificationTest(List<Spectification> list);

   List<Map>  selectOptionList();

    List<TbSpecification> search(String text);

}
