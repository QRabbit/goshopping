package com.qrabbit.youpinghui.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qrabbit.youpinghui.pojo.ResultPageSeller;
import com.qrabbit.youpinghui.pojo.TbBrand;
import com.qrabbit.youpinghui.pojo.TbContent;
import com.qrabbit.youpinghui.pojo.TbContentCategory;
import com.qrabbit.youpinghui.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ContentController {

    @Autowired
    RedisTemplate redisTemplate;

    private String  pic_url = null;


    @Autowired
    ContentService contentService;

    @GetMapping("/findAllContent")
    public List<TbContent> findAllContent()
    {
        System.out.println("findAllContent controller===>");
        return  contentService.findAllContent();
    }

    @GetMapping("/findAllContentCategory")
    public List<TbContentCategory> findAllContentCategory()
    {
        System.out.println("findAllContentCategory controller===>");
        return  contentService.findAllContentCategory();
    }

    @PostMapping("/updatePic")
    public  String  updatePic(MultipartFile multipartFile)
    {
        System.out.println("updatePic----->controller");
        System.out.println(multipartFile.getOriginalFilename()+"<<<<<<");
        pic_url =contentService.updatePic(multipartFile);
        System.out.println(pic_url);
        return pic_url;
    }

    /////////////2
    @PostMapping("/uploadFile")
    public  String  uploadFile(MultipartFile file)
    {
        System.out.println("updatePic----->controller");
        System.out.println(file.getOriginalFilename()+"<<<<<<");
        pic_url =contentService.updatePic(file);
        System.out.println(pic_url);
        return pic_url;
    }



    @PostMapping("/addNewContent")
    public  int  addNewContent(@RequestBody TbContent tbContent)
    {
        System.out.println("addNewContent--------------------->");
        System.out.println(tbContent.getTitle());
        return  contentService.addContent(tbContent);
    }

    @GetMapping("/findById")
    public  TbContent findById(Long id)
    {
        return contentService.findByid(id);
    }

    @PostMapping("/updateContent")
    public  int updateContent(@RequestBody  TbContent tbContent)
    {
        System.out.println(tbContent);
        System.out.println(tbContent.getId());
        return contentService.updateContent(tbContent);
    }

    @GetMapping("/DeleteIds")
    public  int DeleteIds(Long []ids)
    {

        System.out.println("DeleteIds----->");
        for (Long id : ids)
        {
            System.out.println(id);
        }
        return contentService.deleteMany(ids);
    }



    @GetMapping("/findContentByPages") //分页
    public ResultPageSeller findSellerByPages(int pageNum, int pageSize)
    {

        System.out.println("findContentByPages------------->");

        PageHelper.startPage(pageNum,pageSize);
        //Page<E> extends ArrayList<E> 拥有pageinfo list 的 封装
        Page<TbContent> page = (Page<TbContent>) contentService.findAllContent();
        ResultPageSeller resultPage = new ResultPageSeller();
        //从page 中 获取  list
        resultPage.setRow(page.getResult());
        //从page 中 获取  总数
        resultPage.setTotal(page.getTotal());
        System.out.println(resultPage.getTotal()+"------total");

        return  resultPage;
    }

    //前端轮播图
    @GetMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId)
    {
        System.out.println("findByCategoryId--controller---->");
       return contentService.findByCategoryId(categoryId);
    }

    ///contentCategory
    @GetMapping("/findAllcontentCategorylist")
    public List<TbContentCategory> findAllcontentCategorylist(){
        return contentService.findAllcontentCategorylist();
    }



}
