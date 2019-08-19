package com.jt.order.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;


//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**需求：将超时订单状态修改
	 * 1.当用户订单提交30分钟后,如果还没有支付.则交易关闭
	 * 现在时间 - 订单创建时间 > 30分钟  则超时
	 * new date - 30 分钟 > 订单创建时间
	 * 订单创建时间<new date - 30 分钟
	 * sql:
	 *   update order set status=6,update=new date
	 *   where status=1and creatTime<outTime
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//计算超时时间
		Calendar calendar=Calendar.getInstance();
		calendar.add(calendar.MINUTE, -30);
		Date timeOut=calendar.getTime();
		Order order=new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper=new UpdateWrapper<>();
		updateWrapper.eq("status", 1).lt("created",timeOut );
		orderMapper.update(order, updateWrapper);
		System.out.println("超时任务已经完成");
	}
}
