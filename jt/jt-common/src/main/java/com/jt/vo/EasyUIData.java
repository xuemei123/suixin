package com.jt.vo;

import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//封装EasyUI表格数据
@Data
@Accessors(chain=true)
@NoArgsConstructor //定义无参构造
@AllArgsConstructor  //定义有参构造
public class EasyUIData {
     private Integer total;  // 记录总数
     private List<Item> rows;
     
     
     
}
