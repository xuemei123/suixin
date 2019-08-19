package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 
 * @author lenovo
 *
 */
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String redisNodes;
	

	@Bean
	public JedisCluster getCluster() {
		Set<HostAndPort> nodes=new HashSet<>();
		String[] node=redisNodes.split(",");
		for (String nodeInfo : node) {
			String host=nodeInfo.split(":")[0];
			int port=Integer.parseInt(nodeInfo.split(":")[1]);
			
			nodes.add(new HostAndPort(host, port));
		}
		return new  JedisCluster(nodes);
		
	}
/*
 * @Value("${redis.masterName}") private String masterName;
 * 
 * @Value("${redis.sentineNodes}") private String sentineNodes;
 * 
 * //实现redis整合哨兵
 * 
 * @Bean public JedisSentinelPool getPool() { Set<String> sentinels=new
 * HashSet<>(); String[] nodes=sentineNodes.split(","); for (String sNode :
 * nodes) { sentinels.add(sNode); }
 * 
 * return new JedisSentinelPool(masterName, sentinels);
 * 
 * }
 */




/*
 * //测试redis分片机制的操作
 * 
 * @Value("${redis.nodes}") private String nodes;
 * 
 * @Bean public ShardedJedis getShards() { List<JedisShardInfo> shards=new
 * ArrayList<>();
 * 
 * //将nodes中的数据进行分组 String[] node=nodes.split(","); for(String nodeArgs:node) {
 * String[] args=nodeArgs.split(":"); String nodeIp=args[0]; int
 * nodePort=Integer.parseInt(args[1]); JedisShardInfo info=new
 * JedisShardInfo(nodeIp, nodePort); shards.add(info); }
 * 
 * return new ShardedJedis(shards);
 * 
 * }
 */

/*
 * //redis单台测试
 * 
 * @Value("${redis.host}") private String host;
 * 
 * @Value("${redis.port}") private int port;
 * 
 * @Bean public Jedis getJedis() { return new Jedis(host, port);
 * 
 * }
 */




}
