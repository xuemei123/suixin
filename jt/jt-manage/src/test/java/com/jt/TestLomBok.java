package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.pojo.User;

//运行器加载的类型 启动spring容器
@RunWith(SpringRunner.class)
@SpringBootTest //效果和主启动类类似
public class TestLomBok {

	@Autowired
	private User user;
	
	@Test
	public void conLomBok() {
		user.setAge(10).setId(1).setName("吴家舂").setSex("女");
	}

}
