/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dz.gosniias.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
@ApiModel
public class PageWrapper<T> implements Serializable{

    private static final long serialVersionUID = 1556252374982728430L;

    @ApiModelProperty(value = "Общее кол-во")
    private int totalCount;

    @ApiModelProperty(value = "Список объектов")
    private List<T> data;

    public PageWrapper() {
        this.data = new ArrayList<>(0);
        this.totalCount = 0;
    }

    public PageWrapper(int totalCount, List<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = new ArrayList<>(data);
    }
    
}
