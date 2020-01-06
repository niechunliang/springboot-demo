package com.cn.niecl.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.cn.niecl.cmp.SbYwgzJyCMP;

public interface SbYwgzRepository extends JpaRepository<SbYwgzJyCMP, String>, JpaSpecificationExecutor<SbYwgzJyCMP>,
		CrudRepository<SbYwgzJyCMP, String>, Serializable {

}
