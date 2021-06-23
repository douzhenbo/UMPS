package com.codecow.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUserRole implements Serializable {
    private String roleId;

    private String userId;

    private String id;

    private Date createTime;


}
