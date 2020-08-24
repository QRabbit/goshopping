package com.qrabbit.youpinghui.service.impl;


import com.qrabbit.youpinghui.mapper.TbContentCategoryMapper;
import com.qrabbit.youpinghui.mapper.TbContentMapper;
import com.qrabbit.youpinghui.pojo.TbContent;
import com.qrabbit.youpinghui.pojo.TbContentCategory;
import com.qrabbit.youpinghui.pojo.TbContentExample;
import com.qrabbit.youpinghui.service.ContentService;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    /*
    * # MinIO对象存储相关配置
   minio:
  endpoint:  http://127.0.0.1:9000 #MinIO服务所在地址
  bucketName: mall #存储桶名称
  accessKey: minioadmin #访问的key
  secretKey: minioadmin #访问的秘钥
    *
    * */

    @Value("${minio.endpoint}")
    private String endpoint;     //连接url
    @Value("${minio.bucketName}")
    private String bucketName;   //桶名（上传文件存储在桶中）
    @Value("${minio.accessKey}")
    private String accessKey;      //账号
    @Value("${minio.secretKey}")
    private String secretKey;     //密码





    @Autowired
    TbContentMapper contentMapper;
    @Autowired
    TbContentCategoryMapper contentCategoryMapper;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public List<TbContent> findAllContent() {
        System.out.println("service findAllContent---->");
        return contentMapper.selectByExample(null);
    }

    @Override
    public List<TbContentCategory> findAllContentCategory() {
        return contentCategoryMapper.selectByExample(null);
    }

    @Override
    public String updatePic(MultipartFile multipartFile) {
        System.out.println("updatePic----->service");
        System.out.println("updatePic---->"+multipartFile.getOriginalFilename());
        try
        {   //创建 Minio文件上传的客户端      //连接url ， 账号   ，密码
            MinioClient minioClient = new MinioClient(endpoint,accessKey,secretKey);

            //判断 服务器中是否有 该名称的桶
            boolean isExist  = minioClient.bucketExists(bucketName);

            if(!isExist)
            {
                //如果没有桶 创建一个
                minioClient.makeBucket(bucketName);
                //设置桶的方针(政策)         名称   -允许所有文件-     -只读-
                minioClient.setBucketPolicy(bucketName,"*.*", PolicyType.READ_ONLY);
            }

            //设置存储对象的名称（文件夹以时间为名称）
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            //获取上传文件对象的名称
            String filename = multipartFile.getOriginalFilename();
            //设置上传文件名称
            String objname = df.format(new Date()) + "/" + filename;

            //把文件存储到存储桶中       桶名,上传文件名，上传文件的输入流 二进制，上传文件的格式
            minioClient.putObject(bucketName,objname,multipartFile.getInputStream(),multipartFile.getContentType());

            System.out.println("上传成功！");        //桶名,上传文件名   获取url 用于添加显示
            String objectUrl = minioClient.getObjectUrl(bucketName, objname);
            System.out.println("连接url:"+objectUrl);
            return objectUrl;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "null";
        }

    }

    @Override
    public int addContent(TbContent tbContent) {
        System.out.println("servlet -->");
        System.out.println(tbContent);
        try
        {
            contentMapper.insert(tbContent);
            return 200;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  400;
        }
    }



    @Override
    public TbContent findByid(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateContent(TbContent tbContent) {
        System.out.println("service tbcontent update");
        System.out.println(tbContent);
        try
        {
            contentMapper.updateByPrimaryKeySelective(tbContent);
            return 200;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  400;
        }
    }

    @Override
    public int deleteMany(Long[] ids)
    {
        try
        {
            for (Long id : ids) {
              contentMapper.deleteByPrimaryKey(id);
            }
            return 200;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 400;
        }
    }


    //前端 轮播图  以categoryId 作为key    categoryId  对应每一个List<Content>;
    //         categoryId    List
    //          1(轮播图)     List<Tbcontent>
    //          2(今日推荐)   List<Tbcontent>  ........
    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        System.out.println("service findByCategoryId ----->");
        List<TbContent>  list = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
        if (list==null)
        {
            TbContentExample example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");
            criteria.andCategoryIdEqualTo(categoryId);
            List<TbContent> contentList  = contentMapper.selectByExample(example);

            //将获取的 list<tbcontent> 放入 redis
            redisTemplate.boundHashOps("content").put(categoryId,contentList);
            System.out.println("数据放入--》");
            System.out.println("--service ----findbycategoryid--》");
            for (TbContent tbContent : contentList) {
                System.out.println(tbContent.getUrl());
            }

            return contentList;
        }
        else
        {
            System.out.println("redis 有数据  key="+categoryId);
        }
        return list;
    }

    @Override
    public List<TbContentCategory> findAllcontentCategorylist() {
        System.out.println("findAllcontentCategorylist--service");
        return contentCategoryMapper.selectByExample(null);
    }

    /////
}
