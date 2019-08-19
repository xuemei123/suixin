package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
   /**
    * 返回值： true   已存在
    *    false 不存在
    */
	@Override
	public boolean chackUser(String param, Integer type) {
		String column="";
		//String c=(type==1)?"username":((type==2)?"phone":"email");
		switch (type) {
		case 1:
			column="userName";
			break;
		case 2:
			column="phone";
			break;
		case 3:
			column="email";
			break;
		}
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count=userMapper.selectCount(queryWrapper);
		return count==0?false:true;
	}
	
	
	
}
