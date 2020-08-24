package com.qrabbit.youpinghui.pojo;

import java.util.List;

public class ResultPageSeller {
    private List row; //查询分页区间数
    private Long total; //总记录数

    public ResultPageSeller() {
    }

    public ResultPageSeller( Long total,List row) {
        this.row = row;
        this.total = total;
    }

    public List getRow() {
        return row;
    }

    public void setRow(List row) {
        this.row = row;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
