package com.onestore.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.onestore.exception.CustomerException;
import com.onestore.exception.LoginException;
import com.onestore.exception.ProductException;
import com.onestore.model.Cart;
import com.onestore.model.Product;
import com.onestore.model.ProductDto;


@Service
public interface CartServices {

    public ProductDto removeproductFromCart(Integer pid,String key ,Integer quantity)  throws CustomerException, LoginException;
	
	public ProductDto updateProductQuantity( Integer pid, Integer quantity, String key) throws CustomerException, LoginException ;
	
	public Cart addProductToCart(Integer pid,  String key) throws CustomerException, LoginException, ProductException;
	
	public List<ProductDto> viewAllProductsFromCart ( String key)throws CustomerException,LoginException;
	
	}

	

