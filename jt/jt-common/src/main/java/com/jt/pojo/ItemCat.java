package com.jt.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_item_cat")
public class ItemCat implements Serializable{
	@TableId(type=IdType.AUTO)
     private Long id;    //商品分类id
     private Long parentId;   //父级id
     private String name;    //商品分类名称
     private Integer status;  //分类状态
     private Integer sortOrder;  //商品分类排序号
     private Boolean isParent;   //是否为父级
     
}
