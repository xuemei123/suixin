package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_cart")
public class Cart extends BasePojo{
     @TableId(type=IdType.AUTO)
	 private Long id;
     private Long UserId;
     private Long itemId;
     private String  itemTitle;
     private String  itemImage;   //购物车第一张图片
     private Long  itemPrice;
     private Integer num;
}
