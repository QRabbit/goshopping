package com.qrabbit.youpinghui.service.impl;

import com.qrabbit.youpinghui.mapper.TbBrandMapper;
import com.qrabbit.youpinghui.pojo.TbBrand;
import com.qrabbit.youpinghui.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAllBrand() {

        System.out.println("findallbrand service-->");
        List<TbBrand> list =  brandMapper.selectByExample(null);
        for (TbBrand brand : list) {
            System.out.println(brand.getName());
        }
        return list;
    }

    @Override
    public TbBrand findBrandIdToUpdate(long id) {
        TbBrand tbBrand = brandMapper.selectByPrimaryKey(id);
        System.out.println(tbBrand.getName()+"service findBrandIdToUpdate");
        return tbBrand;
    }

    @Override
    public int findBrandToUpdate(TbBrand tbBrand) {
    try
    {
        brandMapper.updateByPrimaryKeySelective(tbBrand);
        return 200;
    }
    catch (Exception e)
    {
          e.printStackTrace();
          return 404;
    }
    }

    @Override
    public int toAddBrand(TbBrand tbBrand) {
        try
        {
            brandMapper.insert(tbBrand);
            return 200;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 404;
        }
    }

    @Override
    public int DeleteManyBrand(long[] ids) {
        System.out.println("DeleteManyBrand service-->"+ids[0]);
        try
        {
            for (long id:ids)
            {
                brandMapper.deleteByPrimaryKey(id);
            }
            return 200;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 404;
        }
    }

    @Override
    public List<Map> findallbrandlist() {
        List<Map> list = brandMapper.selectOptionList();
        for (Map map : list) {
            System.out.println(map);
        }
        return list;
    }
}
