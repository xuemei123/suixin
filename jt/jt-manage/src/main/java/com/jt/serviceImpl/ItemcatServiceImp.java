package com.jt.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.service.RedisService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemcatServiceImp implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}
	/**
	 * 根据parentid查询ItemCatList
	 * 循环遍历
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new  QueryWrapper<>();		
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> itemCatList=itemCatMapper.selectList(queryWrapper);
		ArrayList<EasyUITree> treeList = new ArrayList<>();
		for(ItemCat itemCat:itemCatList) {
			Long id= itemCat.getId();
			String text=itemCat.getName();
			String state=itemCat.getIsParent()?"closed":"open";
			EasyUITree tree = new EasyUITree(id, text, state);
			treeList.add(tree);
		}
		return treeList;
	}
	@Override
	public List<EasyUITree> findCacheItemCatByParentId(Long parendId) {
		List<EasyUITree> treeList =new ArrayList<>();
		String key="ITEM_CAT_"+parendId;
		String jsonResult=jedisCluster.get(key);
		if(StringUtils.isEmpty(jsonResult)) {
			//缓存中没有数据，就从数据库中查
			treeList=findItemCatByParentId(parendId);
			//将list集合转换为json串,将对象转换为串，必然调用get方法
			String json = ObjectMapperUtil.toJSON(treeList);
			//赋值操作并添加超时时间
			jedisCluster.setex(key,3600*24*7,json);
			System.out.println("查询数据库");
		}else {
			treeList = ObjectMapperUtil.toObject(jsonResult,treeList.getClass());     
			System.out.println("查询缓存");
		}
		return treeList;
	}

}
