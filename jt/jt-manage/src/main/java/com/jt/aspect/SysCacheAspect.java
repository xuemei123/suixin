package com.jt.aspect;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.mapper.ItemCatMapper;
import com.jt.service.ItemCatService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Aspect
@Service
public class SysCacheAspect {
	/*
	 * @Autowired private Jedis jedis;
	 * 
	 * @Autowired private ItemCatService itemCatService;
	 * 
	 * @Around("@annotation(com.jt.aspect.CacheAspect)") public List<EasyUITree>
	 * findCacheItemCatByParentId(Long parendId) { List<EasyUITree> treeList =new
	 * ArrayList<>(); String key="ITEM_CAT_"+parendId; String
	 * jsonResult=jedis.get(key); if(StringUtils.isEmpty(jsonResult)) {
	 * //缓存中没有数据，就从数据库中查 treeList=itemCatService.findItemCatByParentId(parendId);
	 * //将list集合转换为json串,将对象转换为串，必然调用get方法 String json =
	 * ObjectMapperUtil.toJSON(treeList); //赋值操作并添加超时时间 jedis.setex(key, 3600*24*7,
	 * json); System.out.println("查询数据库"); }else { treeList =
	 * ObjectMapperUtil.toObject(jsonResult,treeList.getClass());
	 * System.out.println("查询缓存"); } return treeList; }
	 */
}
