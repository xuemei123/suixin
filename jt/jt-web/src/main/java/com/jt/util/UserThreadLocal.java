package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
     private static ThreadLocal<User> thread=new ThreadLocal<>();
     
     public static void setUser(User user) {
    	 thread.set(user);
     }
     
     public static User getUser() {
    	 return thread.get();
     }
   //为了防止内存泄漏关闭ThreadLocal
     public static void remove() {
    	 thread.remove();
     }
}
