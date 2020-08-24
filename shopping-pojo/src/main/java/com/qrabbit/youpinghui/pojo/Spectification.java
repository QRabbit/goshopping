package com.qrabbit.youpinghui.pojo;

import com.qrabbit.youpinghui.pojo.TbSpecification;
import com.qrabbit.youpinghui.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

public class Spectification implements Serializable {

    private TbSpecification specification;
    private List<TbSpecificationOption> specificationlist;

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationlist() {
        return specificationlist;
    }

    public void setSpecificationlist(List<TbSpecificationOption> specificationlist) {
        this.specificationlist = specificationlist;
    }
}
