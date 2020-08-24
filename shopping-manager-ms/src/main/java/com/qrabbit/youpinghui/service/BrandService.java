package com.qrabbit.youpinghui.service;


import com.qrabbit.youpinghui.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    public List<TbBrand> findAllBrand();

    public  TbBrand findBrandIdToUpdate(long id);

    public int findBrandToUpdate(TbBrand tbBrand);

    public int toAddBrand(TbBrand tbBrand);

    public int DeleteManyBrand(long[] ids);

    List<Map> findallbrandlist();
}
