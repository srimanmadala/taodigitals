package com.taodigital.taodigital.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taodigital.taodigital.entity.Product;

public class ProductValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Product p = (Product) obj;
		if (p.getPrice() > 10000) {
			errors.rejectValue("price", "Price cannot be greater than 10000");
		}
	}

}
