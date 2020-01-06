package com.cn.niecl.cmp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
@Table(name = "CS_SBF_SB_YWGZJY")
public class SbYwgzJyCMP implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerated" ,strategy="uuid")
	@GeneratedValue(generator="idGenerated")
	@Column(name="YWGZJY_ID")
	private String ywgzjyId;

	/** 税务机关代码*/
	@Column(name = "swjg_dm")
    private String swjgDm;

    /** 缴费人类型 */
	@Column(name = "jfrlx_dm")
    private String jfrlxDm;

    /** 数据来源*/
	@Column(name = "sjly")
    private String sjly;

    /** 申报方式代码*/   
	@Column(name = "sbfs_dm")
    private String sbfsDm;

    /** 渠道ID*/
	@Column(name = "qd_id")
    private String qdId;
    
    /** 规则代码*/
	@Column(name = "gzdm")
    private String gzDm;
    
	@Column(name = "sxfs")
    private String sxfs;
    
	@Column(name = "qqdz")
    private String qqdz;
	
	@Column(name = "qqcs")
    private String qqcs;
	
	@Column(name = "lrrq")
    private Date lrrq;
	
}
