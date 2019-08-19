package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

public interface ItemService {

	EasyUIData findItemBypage(Integer page, Integer rows);



	void saveItem(Item item, String desc);



	ItemDesc findItemById(Long itemId);



	void updateItem(Item item, String desc);



	void deleteItem(Long ids);



	void updateStatus(Long[] ids, int status);
    
	
	Item findItemByIds(Long itemId);



	ItemDesc findItemDescById(Long itemId);



}
