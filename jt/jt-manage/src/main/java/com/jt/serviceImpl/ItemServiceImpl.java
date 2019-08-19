package com.jt.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
	private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
   //根据页面的要求分页查询数据
	@Override
	public EasyUIData findItemBypage(Integer page, Integer rows) {
		//1.查询商品列表总记录数
		int total= itemMapper.selectCount(null); 
		//2.根据分页查询数据
		/**
		 * 参数说明： page：当前查询的页面
		 */
		int start =(page-1)*rows;
		List<Item> itemList= itemMapper.selectItemByPage(start,rows);
		return new EasyUIData(total,itemList);
	}
	@Override
	@Transactional
	public void saveItem(Item item,String desc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		ItemDesc itemdesc = new ItemDesc();
		itemdesc.setItemId(item.getId());
		itemdesc.setItemDesc(desc);
		itemdesc.setCreated(item.getCreated());
		itemdesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemdesc);
	}
	@Override
	public ItemDesc findItemById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}
	@Override
	@Transactional
	public void updateItem(Item item,String desc) {
		
		item.setUpdated(item.getCreated());
		itemMapper.updateById(item);
		
		ItemDesc itemdesc = new ItemDesc();
		itemdesc.setItemId(item.getId());
		itemdesc.setItemDesc(desc);
		itemdesc.setUpdated(item.getCreated());
		itemDescMapper.updateById(itemdesc);
	}
	
	@Override
	@Transactional
	public void deleteItem(Long ids) {
		List<Long> idList=Arrays.asList(ids);
		
		itemMapper.deleteBatchIds(idList);
		 itemDescMapper.deleteBatchIds(idList);
	}
	@Override
	public void updateStatus(Long[] ids, int status) {
		
		Item item=new Item();
		item.setStatus(status)
		    .setUpdated(new Date());
		//将数组转换为集合数据List
		List<Long> idList=Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}
	@Override
	public Item findItemByIds(Long itemId) {
		return itemMapper.selectById(itemId);
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}
}
