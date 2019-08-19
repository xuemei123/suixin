package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;

@RestController
public class WebJSONPController {
      @RequestMapping("/web/testJSONP")
      public JSONPObject testJSONP(String callback) {
    	   User user=new User();
    	   user.setId(123);
    	   user.setName("张三");
    	  return new JSONPObject(callback, user);
      }
}
