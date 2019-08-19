package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
@Controller
@RequestMapping("/cart")
public class CartController {
	  @Reference(timeout=3000,check=false)
      private DubboCartService cartService;
	  @RequestMapping("/show")
	  public String findCartListByUserId(Model model,HttpServletRequest request) {
		/*
		 * User user = (User) request.getAttribute("JT_USER"); Long userId=user.getId();
		 */
		  Long userId=UserThreadLocal.getUser().getId();
		  List<Cart> cartList=cartService.findCartListByUserId(userId);
		  model.addAttribute("cartList",cartList);
		  return "cart";
	  }
	  @RequestMapping("add/{itemId}")
	  public String savaCart(Cart cart,HttpServletRequest request) {
		  
		/*
		 * User user = (User) request.getAttribute("JT_USER"); Long userId=user.getId();
		 */
		  Long userId=UserThreadLocal.getUser().getId();
		  cart.setUserId(userId);
		  cartService.saveCart(cart);
		  
		  return "redirect:/cart/show";
	  }
	  @RequestMapping("/update/num/{itemId}/{num}")
	  public SysResult updateCartNum(Cart cart,HttpServletRequest request) {
		  try {
			/*
			 * User user = (User) request.getAttribute("JT_USER"); Long userId=user.getId();
			 */
			  
			  Long userId=UserThreadLocal.getUser().getId();
			  
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
		e.printStackTrace();
		return SysResult.fail();
		}
	  }
}
