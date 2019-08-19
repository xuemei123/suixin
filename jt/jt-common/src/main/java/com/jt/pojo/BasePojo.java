package com.jt.pojo;
import java.io.Serializable;
//定义父集的pojo
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class BasePojo implements Serializable{
     private Date created;
     private Date updated;
}
