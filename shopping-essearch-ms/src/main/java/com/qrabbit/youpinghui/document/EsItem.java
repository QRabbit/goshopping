package com.qrabbit.youpinghui.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "shoppingmall",type ="items" )
public class EsItem implements Serializable {

   //定义日常  经常搜索的业务字段

    @Id  //唯一标识
    private Long id;  //ik_smart     //Text 分词 不能聚合 //keyword 能聚合不能分词
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;
    private String imge; //String类型 不写field 默认 keyword
    private Double price;
    private Long goodsid;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Keyword)
    private String brand;
    @Field(analyzer = "ik_smart",type = FieldType.Text)
    private String seller;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "商品信息:{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imge='" + imge + '\'' +
                ", price=" + price +
                ", goodsid=" + goodsid +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", seller='" + seller + '\'' +
                '}';
    }
}
