package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.json.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 需求：根据用户传递的数据进行校验
	 * 返回值：
	 * Sysresult 其中data表示boolean值
	 *           true 表示数据已经存在
	 *           false表示数据可以使用
	 * 调用方法：
	 * 前台采用jsonp方法调用
	 * 最终返回值必须经过特殊格式分装JSONPObject
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject chackUser(@PathVariable String param,@PathVariable Integer type,String callback) {
		boolean flag=userService.chackUser(param,type);
		
		return new JSONPObject(callback, SysResult.ok(flag));
		
	}
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,String callback) {
		String userJSON = jedisCluster.get(token);
		if (StringUtils.isEmpty(userJSON)) {
			return new JSONPObject(callback, SysResult.fail());
		}
		return new JSONPObject(callback, SysResult.ok(userJSON));
	}
}
