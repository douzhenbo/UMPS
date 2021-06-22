package com.douzhenbo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserRole implements Serializable {
    private Long roleId;

    private Long userId;


}
