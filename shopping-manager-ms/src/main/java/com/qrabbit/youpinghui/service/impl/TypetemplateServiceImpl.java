package com.qrabbit.youpinghui.service.impl;

import com.qrabbit.youpinghui.mapper.TbTypeTemplateMapper;
import com.qrabbit.youpinghui.pojo.TbTypeTemplate;
import com.qrabbit.youpinghui.pojo.TbTypeTemplateExample;
import com.qrabbit.youpinghui.service.TypetemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypetemplateServiceImpl implements TypetemplateService {
    @Autowired
    TbTypeTemplateMapper tbTypeTemplateMapper;

    @Override
    public List<TbTypeTemplate> findAllTypetemplate() {
        return tbTypeTemplateMapper.selectByExample(null);
    }

    @Override
    public int addTypes(TbTypeTemplate tbTypeTemplate) {
        return tbTypeTemplateMapper.insert(tbTypeTemplate);
    }

    @Override
    public TbTypeTemplate findByid(Long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateType(TbTypeTemplate tbTypeTemplate) {
        return tbTypeTemplateMapper.updateByPrimaryKeySelective(tbTypeTemplate);
    }

    @Override
    public List<TbTypeTemplate> searchlike(String text) {
        System.out.println("searchlike--->"+text);
        if (text!=null&&!"".equals(text))
        {
            TbTypeTemplateExample example = new TbTypeTemplateExample();
            TbTypeTemplateExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+text+"%");
            List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(example);
            return  tbTypeTemplates;
        }else
        {
            return tbTypeTemplateMapper.selectByExample(null);
        }

    }

    @Override
    public int deleteMany(Long[] ids) {
        try
        {
            for (Long id : ids) {
                tbTypeTemplateMapper.deleteByPrimaryKey(id);
            }
            return 200;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 400;
        }
    }
}
