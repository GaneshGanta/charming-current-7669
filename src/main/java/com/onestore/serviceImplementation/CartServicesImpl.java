package com.onestore.serviceImplementation;

import java.lang.StackWalker.Option;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onestore.exception.CustomerException;
import com.onestore.exception.LoginException;
import com.onestore.exception.ProductException;
import com.onestore.model.Cart;
import com.onestore.model.CurrentUserSession;
import com.onestore.model.Customer;
import com.onestore.model.Product;
import com.onestore.model.ProductDto;
import com.onestore.repository.CartDao;
import com.onestore.repository.CustomerDao;
import com.onestore.repository.ProductDao;
import com.onestore.repository.ProductDtoDao;
import com.onestore.repository.UserSessionDao;
import com.onestore.service.CartServices;


@Service
public class CartServicesImpl implements CartServices{

	@Autowired
	private ProductDtoDao productDao;
	
	@Autowired
	private CartDao cartD;
	
	@Autowired
	private UserSessionDao currentuser;
	
	@Autowired
	private CustomerDao custDao;
	
	@Autowired
	private Validation valid;
	
	@Override
	public ProductDto removeproductFromCart(Integer pid, String key,Integer quantity) throws CustomerException, LoginException{
		  
		Customer customer = valid.validateLogin(key);
		

		if(customer==null)
		{
			
			
			Cart customer_cart =customer.getCart();
			
			List<ProductDto> productList=  customer_cart.getProducts();
			
			
			
			boolean flag = false;
			
			for(int i=0;i<productList.size();i++)
			{
				if(productList.get(i).getProductId()==pid)
				{
					productList.remove(productList.get(i));
					flag=true;
					break;
					
				}
			}
		
//		Here customer productlist get updated with new product....
			customer_cart.setProducts(productList);
			
			
			Optional<ProductDto> optp = productDao.findById(pid);
			if(optp.isPresent())
			{
				ProductDto product =optp.get();
				if(quantity<1)
				{
					throw new CustomerException("Please provide valid quantity");
				}
				else
				{
					  product.setQuantity(product.getQuantity()-quantity);
					  productDao.save(product);
				}
				
			}
			else
			{
				throw new CustomerException("Invalid product id:"+pid);
			}
			
		}
		
		
		throw new CustomerException("Invalid customerId:"+customer.getCustomerId());
	}
	
	
	
	
	
	

	@Override

	public ProductDto updateProductQuantity(Integer pDtoId, Integer quantity, String key) throws CustomerException,LoginException {

				Customer customer = valid.validateLogin(key);
				if(customer==null)throw new CustomerException("customer not found with uuid:"+key);
				
				List<ProductDto> productDtolist =    customer.getCart().getProducts();
				
				ProductDto product = null;
				
				
				boolean flag=false;
				
				for(int i=0;i<productDtolist.size();i++)
				{
					if(productDtolist.get(i).getId()==pDtoId)
					{
						productDtolist.get(i).setQuantity(productDtolist.get(i).getQuantity()+quantity);
						
						product =   productDtolist.get(i);
						flag = true;
					    break;
					}
				}
				
				if(flag==false)throw new CustomerException("Product not found with productDtoId: "+pDtoId);
				
			     Cart customerCart =	 customer.getCart();
			      customerCart.setProducts(productDtolist);
			
			       customer.setCart(customerCart);
			       custDao.save(customer);
			       return product;
			}
		
			
			
		
	

	
		 
	

	@Override
	public Cart addProductToCart(Integer pid,  String key) throws CustomerException, LoginException, ProductException {
		    
		Optional<ProductDto> prodopt =productDao.findById(pid);
		
		Customer customer = valid.validateLogin(key);
		if(prodopt.isEmpty()) {
			throw new ProductException("Product Not Available!");
		}
		ProductDto product =prodopt.get();
		  
		Cart cust_cart =  customer.getCart();
		System.out.println(cust_cart+" "+cust_cart.getProducts().size());
		cust_cart.getProducts().add(product);
		Cart updatedCart = cartD.save(cust_cart);
		System.out.println(updatedCart.getProducts().size());
		customer.setCart(updatedCart);
		
		return updatedCart;
	}



	@Override
	public List<ProductDto> viewAllProductsFromCart(String key) throws CustomerException, LoginException {
		Customer customer = valid.validateLogin(key);
		  
	
			Cart cart=null;
			if(customer==null)
			{
			    cart  =customer.getCart();
		
			}
			if(cart==null)
			{
				throw new CustomerException("Your cart is empty with cardId:"+cart.getCartId());
			}
			else
			{
				 return cart.getProducts();
			}
		
	}

}
