package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
//spring4的版本需要立即重写三个拦截器的方法
//spring5的版本 需要那个自己从重写即可
@Component  //将对象交给spring容器管理
public class UserInterceptor implements HandlerInterceptor{
     @Autowired
     private JedisCluster jedisCluster;
	
	//实现用户请求的拦截
	//如果用户满足登陆要求，则跳转页面，如果用户没有登录则重定向登录页面
	//验证用户是否登录步骤：
	 //1.获取cookie数据  2.从cookie获取teken
	  //3.通过token查询redis
	//4，判断是否含有数据
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Cookie[] cookies = request.getCookies();
		String token=null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token=cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(token)) {
			//表示token数据有值，查询redis缓存服务器
			String userJson = jedisCluster.get(token);
		   if(!StringUtils.isEmpty(userJson)) {
			   //表示Reids服务器中有数据，否则表名用户登录成功，给予放行
			   System.out.println("用户以登录");
			   
			   User user= ObjectMapperUtil.toObject(userJson, User.class);
			   request.setAttribute("jt_user",user);
			 //2.将数据保存到threadLocal中
				UserThreadLocal.setUser(user);
		      return true;
		   }
		}
		System.out.println("用户没有登录");
		response.sendRedirect("/user/login.html");
		//true 代表方向
		//false代表拦截
		return false;
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//为了防止内存泄露
		UserThreadLocal.remove();
	}
}
