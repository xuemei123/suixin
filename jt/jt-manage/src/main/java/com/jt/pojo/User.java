package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data    //添加get/set/tostring方法
@Accessors(chain=true)
@TableName
public class User {
	@TableId(type=IdType.AUTO)
	private Integer id;
	private String name;
	private Integer age;
	private String sex;
	
	public User setId(Integer id) {
		this.id=id;
		return this;
		
	}
	
	
    //实现get于set自动生成
}
