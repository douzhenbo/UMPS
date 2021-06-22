package com.codecow.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRole implements Serializable {
    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;


}
