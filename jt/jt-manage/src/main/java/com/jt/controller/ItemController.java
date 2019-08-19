package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {
   @Autowired
	private ItemService itemService;
    /**
     * 根据页面的而要求进行分页查询
     */
   @RequestMapping("/query")
     public EasyUIData findItemByPage(Integer page,Integer rows) {
		return itemService.findItemBypage(page,rows);
    	 
     }

    @RequestMapping("/save")
    public SysResult saveItem(Item item ,String desc) {
    	try {
    		//同时入库两张表，完成商品/商品新增入库
    		itemService.saveItem(item,desc);
    		return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
    }

    @RequestMapping("/query/item/desc/{itemId}")
    public SysResult findItemById(@PathVariable Long itemId) {
		
    	try {
    		ItemDesc itemDesc =itemService.findItemById(itemId);
    		return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
    }
    @RequestMapping("/update")
    public SysResult updateItem(Item item ,String desc) {
    	try {
    		//同时入库两张表，完成商品/商品新增入库
    		itemService.updateItem(item,desc);
    		return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
    }
    
    
    @RequestMapping("/delete")
    public SysResult deleteItem(Long ids) {
    	try {
    		itemService.deleteItem(ids);
    		return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
    }
   
    @RequestMapping("/instock")
    public SysResult instock(Long[] ids) {
    	try {
    		int status=2; 
    		itemService.updateStatus(ids,status);
    		return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
    }
}
