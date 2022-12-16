package com.onestore.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.onestore.exception.CustomerException;
import com.onestore.exception.LoginException;
import com.onestore.model.Order;
@Service
public interface OrderService {
	public Order addOrder(Order order, String key) throws LoginException, CustomerException;
	public Order updateOrder(Order order, String key) throws LoginException, CustomerException;
	public Order removeOrder(Integer orderId, String key) throws LoginException, CustomerException;
	public Order viewOrder(Integer orderId);
	public List<Order> viewAllOrderByDate(LocalDate date);
	public List<Order> viewAllOrderByLocation(String location);
	public List<Order> viewAllOrderByUserId(Integer userid) throws CustomerException;
}
