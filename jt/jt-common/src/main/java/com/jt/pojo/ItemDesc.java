package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{
       //商品描述信息于商品信息一对一，并且id值相同
	   @TableId                   //(type=IdType.AUTO)
	   private Long itemId;
       private String itemDesc;
}
