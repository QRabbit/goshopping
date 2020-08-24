package com.qrabbit.youpinghui.repository;

import com.qrabbit.youpinghui.document.EsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//类,,,主键的类型
public interface EsItemRepository extends ElasticsearchRepository<EsItem,Long> {



    //接口里面定义特殊查询方法  不用实现      //一一对应  Pageable包含了分页 数据  给null默认不分页 查全部
    public Page<EsItem> findByTitleAndBrand(String Title, String Brand, Pageable pageable);


    //多字段查询
   @Query(                              //?0 代表第一个  ?1代表第二个
       " {\"multi_match\":{\"query\": \"?0\",\"fields\":[\"title\",\"category\",\"brand\"]}}"
   )
    public Page<EsItem> findbykeyword(String keyword, Pageable pageable);

}
