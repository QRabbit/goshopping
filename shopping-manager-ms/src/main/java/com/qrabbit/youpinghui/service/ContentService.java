package com.qrabbit.youpinghui.service;


import com.qrabbit.youpinghui.pojo.TbContent;
import com.qrabbit.youpinghui.pojo.TbContentCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContentService {

    List<TbContent> findAllContent();

    List<TbContentCategory> findAllContentCategory();

    String updatePic(MultipartFile multipartFile);

    int addContent(TbContent tbContent);

    TbContent findByid(Long id);

    int updateContent(TbContent tbContent);

    int deleteMany(Long[] ids);

    List<TbContent> findByCategoryId(Long categoryId);

    List<TbContentCategory> findAllcontentCategorylist();
}
