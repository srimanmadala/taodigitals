package com.taodigital.taodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taodigital.taodigital.entity.ApprovalQueue;
import com.taodigital.taodigital.entity.Product;
import com.taodigital.taodigital.exceptions.ProductException;
import com.taodigital.taodigital.service.ProductService;
import com.taodigital.taodigital.validator.ProductValidator;

@RestController
public class ProductController {
	
	@Autowired
	private ProductValidator validator;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/api/products")
	public List<Product> getAllProducts(){
		
		return productService.getActiveProducts();

	}
	
	@PostMapping("/api/products")
	public ResponseEntity<String> createProduct(@RequestBody Product newProduct) {
		Errors errors = validator.validateObject(newProduct);
		if(errors.hasErrors()) {
			return new ResponseEntity<>(
				      "Price canot be greater than $10000", null, HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<String> resp = null; 
		try {
		 if(!productService.createProduct(newProduct)) {
			 resp = new ResponseEntity<>(
				      "Product Requires Approval", null, HttpStatus.PROCESSING);
		 }
		 resp = new ResponseEntity<>(
			      "Product Created Successfully", null, HttpStatus.CREATED);
		}catch(ProductException exp) {
			resp =  new ResponseEntity<>(
				      exp.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@PutMapping("/api/products/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable("productId") long productId,
			@RequestBody Product productTobeUpdated) {
		
		ResponseEntity<String> resp = null;
		try {
			productService.updateProduct(productId, productTobeUpdated);
			resp =  new ResponseEntity<>(
				      "Successfully Updated", null, HttpStatus.OK);
		}catch(ProductException exp) {
			resp =  new ResponseEntity<>(
				      exp.getMessage(), null, HttpStatus.PROCESSING);
		}
		catch(Exception exp) {
			resp =  new ResponseEntity<>(
				      exp.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	
	
	
	@GetMapping("/api/products/approval-queue")
	public List<ApprovalQueue> getAllApprovalqueue(){
		
		return productService.getAllApprovalqueue();
	}
	
	@PutMapping("/api/products/approval-queue/{approvalId}/approve")
	public void approveProductByApprovalID(
			@PathVariable("approvalId") long approvalId){
		
		 productService.approveProductById(approvalId);
	}
	
	
	
	@PutMapping("api/products/approval-queue/{approvalId}/reject")
	public void rejctProductApproval(
			@PathVariable("approvalId") long approvalId){
		
		 productService.rejectProductById(approvalId);
	}
	
	@GetMapping("/api/products/search")
	public List<Product> searchProductsWith(@RequestBody Product searchCriteriaObj){
		
		 return productService.searchProductsWith(searchCriteriaObj.getProductName(),
				 searchCriteriaObj.getPrice(),searchCriteriaObj.getCreatedTime(),
				 searchCriteriaObj.getUpdatedTime());
	}
	
	
	
}


