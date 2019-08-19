package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIFile {
	//如果文件上传则返回0，有错返回1
	 private Integer error=0;
     private String url;
     private Integer width;
     private Integer height;
}
