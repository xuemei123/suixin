package com.jt.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;
//当前接口没有交给spring管理
public interface UserMapper extends BaseMapper<User>{
   //添加查询操作
	List<User> findAll();
}
