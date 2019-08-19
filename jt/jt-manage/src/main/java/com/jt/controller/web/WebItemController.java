package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService ;
   //1.1根据商品信息查询商品业务
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable Long itemId) {
		return itemService.findItemByIds(itemId);
	}
	//2.根据商品id获取商品详细信息
	@RequestMapping("/findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		return itemService.findItemDescById(itemId);
	}
}




