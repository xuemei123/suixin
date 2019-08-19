package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.User;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;
@SuppressWarnings("resource")
public class TestRedis {
	//测试string

	@Test
	public void  testString() throws InterruptedException {
		String host="192.168.81.117";
		int port=6379;
		Jedis jedis = new Jedis(host, port);
		jedis.set("1901", "redis单台操作");
		jedis.append("1901", "123");
		jedis.expire("1901", 10);
		Thread.sleep(3000);
		System.out.println(jedis.ttl("1901"));
		System.out.println(jedis.get("1901"));
	}
	/**
	 * 测试hash 保存对象，但是返回值需要手动转化
	 * setnx作用：
	 *    如果redis缓存中没有数据，就用set方法，把数据保存起来，
	 *    如果已经有该数据.则set操作省略.
	 * 作用:在高并发下,减少脏数据.   
	 *    
	 * @throws InterruptedException
	 */
	@Test
	public void  testHash() throws InterruptedException {
		String host="192.168.81.117";
		int port=6379;
		Jedis jedis = new Jedis(host, port);
		//测试前,先删除数据
		jedis.hsetnx("Student", "id", "101");
		jedis.hsetnx("Student", "age", "18");
		jedis.hsetnx("Student", "age", "20");
		/*
		 * jedis.hset("student", "id", "101"); jedis.hset("student", "age", "18");
		 */
		Map<String, String> map = jedis.hgetAll("student");
		System.out.println(map);
	}
	@Test
	public void  testTx() throws InterruptedException {
		String host="192.168.81.117";
		int port=6379;
		Jedis jedis = new Jedis(host, port);
		Transaction transation = jedis.multi();
		try {
			transation.set("qqq", "qqqq");
			transation.set("www", "wwwww");
			transation.exec();
		} catch (Exception e) {
			e.printStackTrace();
			transation.discard();
		}

	}
	@Test
	public void testObject() throws IOException {
		String host="192.168.81.117";
		int port=6379;
		Jedis jedis = new Jedis(host, port);

		//实现对象于json互转objectMapper对象
		ObjectMapper objectMapper = new  ObjectMapper();
		//对象转化为json
		User user=new User();
		user.setAge(18).setId(10).setName("wjc").setSex("nv");
		String userJSON = objectMapper.writeValueAsString(user);
		System.out.println(userJSON);

		//将string转换为对象
		User user2 = objectMapper.readValue(userJSON, User.class);
		System.out.println(user2);

		//将list集合于JSON互转
		List<User> userList=new ArrayList<>();
		User user1=new User();
		user1.setAge(18).setId(10).setName("wjc").setSex("nv");
		User user3=new User();
		user3.setAge(18).setId(10).setName("wjc").setSex("nv");
		User user4=new User();
		user4.setAge(18).setId(10).setName("wjc").setSex("nv");
		userList.add(user1);
		userList.add(user3);
		userList.add(user4);
		String listJSON = objectMapper.writeValueAsString(userList);
		System.out.println(listJSON);

		//json转对象
		List<User> userList2 = objectMapper.readValue(listJSON, userList.getClass());
		System.out.println(userList2);

	}
    //实现redis分片
	@Test
	public void testShards() {
		List<JedisShardInfo> shards=new ArrayList<>();
		JedisShardInfo Info1=new JedisShardInfo("192.168.81.117", 6379);
		JedisShardInfo Info2=new JedisShardInfo("192.168.81.117", 6380);
		JedisShardInfo Info3=new JedisShardInfo("192.168.81.117", 6381);
		shards.add(Info1);
		shards.add(Info2);
		shards.add(Info3);
		ShardedJedis shardedJedis=new ShardedJedis(shards);
		shardedJedis.set("xiaoluo", "是个小狗");
		System.out.println(shardedJedis.get("xiaoluo"));
	}
	
	//实现reidis哨兵请求 IP：端口
	@Test
	public void testSentinel() {
		String masterName="mymaster";
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.81.119:26379");
		JedisSentinelPool pool= new JedisSentinelPool(masterName, sentinels);
		Jedis jedis=pool.getResource();
		jedis.set("1901","哨兵搭建完成");
		System.out.println(jedis.get("1901"));
		jedis.close();
	}
	//redis集群测试
	@Test
	public void testRedisCluster() {
		
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.81.117", 7000));
		nodes.add(new HostAndPort("192.168.81.117", 7001));
		nodes.add(new HostAndPort("192.168.81.117", 7002));
		nodes.add(new HostAndPort("192.168.81.117", 7003));
		nodes.add(new HostAndPort("192.168.81.117", 7004));
		nodes.add(new HostAndPort("192.168.81.117", 7005));
		JedisCluster	jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("19001", "redis集群配置完成");
		System.out.println(jedisCluster.get("19001"));
	}
	
	
}
