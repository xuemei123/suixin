package com.jt.pojo;
//创建商品对象，全部属性定义使用包装类型

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("tb_item")
@JsonIgnoreProperties(ignoreUnknown=true) //排除位置属性，适用于对象转换时有多余的属性
public class Item extends BasePojo{
	@TableId(type=IdType.AUTO)
     private Long id;       //商品id号
     private String title;  //商品标题信息
     private String sellPoint;  //商品买点信息
     private Long   price;     //double计算速度慢还有精度问题，所以用long
     private Integer num;   
     private String image;    //保存图片url地址
     private Long cid;      //商品分类信息
     private Integer status;  //正常  下架 删除
     
     
     //为了实现页面的图片的正确回显
     public String[] getImages() {
		return image.split(",");
     }
     //如果只有get方法，那么json转化必然要调用set方法
     
}
