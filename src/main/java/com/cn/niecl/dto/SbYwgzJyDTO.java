package com.cn.niecl.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SbYwgzJyDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ywgzjyId;

    /** 税务机关代码*/
    private String swjgDm;

    /** 缴费人类型 */
    private String jfrlxDm;

    /** 数据来源*/
    private String sjly;

    /** 申报方式代码*/    
    private String sbfsDm;

    /** 渠道ID*/
    private String qdId;
    
    /** 规则代码*/
    private String gzDm;
    
    private String sxfs;
    
    private String qqdz;
    
    private String qqcs;
}
