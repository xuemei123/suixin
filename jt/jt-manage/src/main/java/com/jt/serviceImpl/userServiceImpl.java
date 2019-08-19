package com.jt.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.service.UserService;
@Service
public class userServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
	@Override
	public List<User> findAll() {
		
		//return userMapper.selectList(null);
		return userMapper.findAll();
	}

}
