package com.jt.server;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import com.jt.service.DubboOrderService;
@Service
public class DubboOrderServerImpl implements DubboOrderService{
      @Autowired
      private OrderMapper orderMapper;
      @Autowired
      private OrderItemMapper orderItemMapper;
      @Autowired
      private OrderShippingMapper orderShippingMapper;
     
      @Transactional
      @Override
  	public String saveOrder(Order order) {
    	  String totalprice=totalPrice(order);
    	  
    	  String orderId=""+order.getUserId()+System.currentTimeMillis();
  		Date data=new Date();
  		//1.订单入库
  		order.setOrderId(orderId).setStatus(1).setPayment(totalprice).setCreated(data).setUpdated(data);
  		orderMapper.insert(order);
  		System.out.println("订单入库成功！！！！");
  		//2.入库订单物流
  		OrderShipping orderShipping=new OrderShipping();
  		orderShipping.setOrderId(orderId).setCreated(data).setUpdated(data);
  		orderShippingMapper.insert(orderShipping);
  		//3.订单商品入库一对多
  		List<OrderItem> list=order.getOrderItems();
  		for (OrderItem orderItem : list) {
  			orderItem.setOrderId(orderId)
  			         .setCreated(data)
  			         .setUpdated(data);
  			orderItemMapper.insert(orderItem);
		}
  		System.out.println("订单入库全部成功");
  		return orderId;
  	}
      
      
      @Override
	public Order findOrderById(String id) {
		Order order=orderMapper.selectById(id);
		OrderShipping orderShipping=orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("order_id", id);
		
		List<OrderItem> items=orderItemMapper.selectList(queryWrapper);
    	  
		order.setOrderShipping(orderShipping).setOrderItems(items);
    	  
    	  return order;
	}
	//计算订单的总价
      //价格*总数
      public String totalPrice(Order order) {
    	  
    	  
    	  List<OrderItem> orderItems = order.getOrderItems();
    	  Long totalprice=0L;
    	  for (OrderItem orderItem : orderItems) {
    		  totalprice+= orderItem.getPrice()/100*orderItem.getNum();
		}
		return ""+totalprice;
      }
}
