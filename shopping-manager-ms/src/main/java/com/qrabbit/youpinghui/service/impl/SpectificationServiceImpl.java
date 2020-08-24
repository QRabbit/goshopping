package com.qrabbit.youpinghui.service.impl;

import com.qrabbit.youpinghui.mapper.TbSpecificationMapper;
import com.qrabbit.youpinghui.mapper.TbSpecificationOptionMapper;
import com.qrabbit.youpinghui.pojo.*;
import com.qrabbit.youpinghui.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class SpectificationServiceImpl implements SpecificationService {

    @Autowired
    TbSpecificationMapper specificationMapper;
    @Autowired
    TbSpecificationOptionMapper specificationOptionMapper;


    @Override
    public List<TbSpecification> findAllSpectification() {
        List<TbSpecification> tbSpecifications = specificationMapper.selectByExample(null);
        return tbSpecifications;
    }

    @Override
    public List<TbSpecification> findbyExample(String seachtext) {

        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();
        criteria.andSpecNameLike("%"+seachtext+"%");
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        System.out.println(list.get(0).getSpecName()+"--findbyExample-->service");
        return  list;
    }


    //删除
    @Override
    public int DeleteManySpec(Long[] ids)
    {
      try
      {

          for (Long id : ids) {
              // 关系表
              TbSpecificationOptionExample example = new TbSpecificationOptionExample();
              TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
              specificationMapper.deleteByPrimaryKey(id);
              criteria.andSpecIdEqualTo(id);
              specificationOptionMapper.deleteByExample(example);
          }

          return  200;

      }
      catch (Exception e)
      {
          return 404;
      }
    }

    @Override
    public int AddSpc(Spectification spectifications) {
        try
        {
            //SELECT LAST_INSERT_ID() as id;
            specificationMapper.insert(spectifications.getSpecification());
            System.out.println("AddSpc service---->");
            System.out.println(spectifications.getSpecification().getId()+"AddSpc service");
            List<TbSpecificationOption> specificationlist = spectifications.getSpecificationlist();
            for (TbSpecificationOption tbSpecificationOption : specificationlist) {
                tbSpecificationOption.setSpecId(spectifications.getSpecification().getId());
                specificationOptionMapper.insert(tbSpecificationOption);
            }
            System.out.println("Done!!");
            return  200;

        }
        catch (Exception e)
        {
            return 404;
        }

    }

    @Override
    public Spectification findBySpcId(Long id) {

        System.out.println("findBySpcId------------>");
        Spectification spectification = new Spectification();

        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);

        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);

        spectification.setSpecification(tbSpecification);
        spectification.setSpecificationlist(tbSpecificationOptions);

        return spectification;
    }

    @Override
    public int findToUpdate(Spectification spectification) {
        try
        {
            TbSpecification specifications = spectification.getSpecification();
            specificationMapper.updateByPrimaryKeySelective(specifications);

            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(specifications.getId());
            specificationOptionMapper.deleteByExample(example);

            List<TbSpecificationOption> specificationlist = spectification.getSpecificationlist();
            for (TbSpecificationOption tbSpecificationOption : specificationlist) {
                tbSpecificationOption.setSpecId(specifications.getId());
                specificationOptionMapper.insert(tbSpecificationOption);
            }

            System.out.println("Done!!");
            return  200;

        }
        catch (Exception e)
        {
            return 404;
        }


    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

    @Override
    public List<TbSpecification> search(String text) {
        System.out.println("search------------->");
        TbSpecificationExample example= new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();
        criteria.andSpecNameLike("%"+text+"%");
        List<TbSpecification> tbSpecifications = specificationMapper.selectByExample(example);
        return tbSpecifications;
    }
}
