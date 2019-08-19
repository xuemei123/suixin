package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired 
	private ItemCatService itemCatService;
     //根据商品的分类信息查询是商品信息
     @RequestMapping("/queryItemCatNameById")
     public String findItemCatNameById(Long itemCatId ) {
		return itemCatService.findItemCatNameById(itemCatId);
    	 
     }
     /**
      * 
      * @return
      */
     @RequestMapping("/list")
     public List<EasyUITree> findItemCatByParentId(@RequestParam(value="id",defaultValue="0")Long parendId){
    	
    	 return itemCatService.findCacheItemCatByParentId(parendId);
    	 
    	 //return itemCatService.findItemCatByParentId(parendId);
    	 
     }
     
     
     
     
     
}
