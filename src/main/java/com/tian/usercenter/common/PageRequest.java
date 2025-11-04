package com.tian.usercenter.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -6690280471773729346L;
    protected int pageSize = 10;
    protected int pageNum = 1;
}
