package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService{
    @Autowired
	private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 入库操作步骤
     * 	1.密码应该加密 MD5 MD5HASH
	 * 	2.页面中暂时没有传递email 暂时使用电话代替
	 *  3.添加用户操作的时间
	 *  4.需要控制事务
     */
    @Transactional
	@Override
	public void saveUser(User user) {
		 String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes()); 
	     user.setEmail(user.getPhone())
	     .setPassword(md5Pass)
	     .setCreated(new Date())
	     .setUpdated(user.getCreated());
	     userMapper.insert(user);
	
	}
    /**
     * 1.校验用户名和密码
     *    如果用户名和密码不正确返回null
     * 2. 如果用户名和密码正确
     *    生成token密钥将user对象转化为json数据之后保存到redis
     *  3.将token数据返会
     */
	@Override
	public String doLogin(User user) {
		//1.将密码加密
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		//2.根据用户信息查询数据库
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("username", user.getUsername()).eq("password", md5Pass);
		User userBD = userMapper.selectOne(queryWrapper);
		//3.判断用户是否正确
		if (userBD==null) {
			return null;
		}
		//4.程序执行到这里，表示用户名和密码正确
		String token="JT_TICKET_"+System.currentTimeMillis()+user.getUsername();
		token=DigestUtils.md5DigestAsHex(token.getBytes());
		//5.脱敏处理
		 userBD.setPassword("123456");
		 String userJSON=ObjectMapperUtil.toJSON(userBD);
		 jedisCluster.setex(token, 7*24*3600, userJSON);
	    return token;
	}
    
}
