package com.taodigital.taodigital.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.taodigital.taodigital.dao.ProductApprovalDao;
import com.taodigital.taodigital.dao.ProductDao;
import com.taodigital.taodigital.entity.ApprovalQueue;
import com.taodigital.taodigital.entity.Product;
import com.taodigital.taodigital.exceptions.ProductException;
import com.taodigital.taodigital.util.ProductUtil;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductApprovalDao productApprovalDao;

	public static final int PRICE = 5000;

	public List<Product> getActiveProducts() {

		List<Product> products = productDao.findAll();

		return products.stream().filter(product -> product.getApprovalSatus() == 1).sorted((a, b) -> {
			return a.getCreatedTime().compareTo(b.getCreatedTime());
		}).collect(Collectors.toList());

	}

	public boolean createProduct(Product newProduct) {
		try {

			if (newProduct.getPrice() > PRICE) {
				// Move to approval quue.
				productApprovalDao.save(ProductUtil.getproductApprovalQueue(newProduct));
				return false;
			}
			newProduct.setCreatedTime(new Date());
			newProduct.setUpdatedTime(new Date());
			productDao.save(newProduct);

		} catch (Exception exp) {
			throw new ProductException(" Error while saving product Details.");
		}
		return true;
	}

	public void updateProduct(long productId, Product product) {

		Optional<Product> optionalExistingProduct = productDao.findById(productId);
		if (!optionalExistingProduct.isPresent()) {
			throw new ProductException(" No Product exists with id." + productId);
		}

		Product existingproduct = optionalExistingProduct.get();
		if (product.getPrice() > existingproduct.getPrice() * 1.5) {
			existingproduct.setApprovalSatus(0);
			productDao.save(existingproduct);
			existingproduct.setPrice(product.getPrice());
			productApprovalDao.save(ProductUtil.getproductApprovalQueue(existingproduct));
			throw new ProductException("Product price is more than 50% of existing price");
		}
		existingproduct.setPrice(product.getPrice());
		productDao.save(existingproduct);
	}

	public List<ApprovalQueue> getAllApprovalqueue() {

		List<ApprovalQueue> approvalQueue = productApprovalDao.findAll();

		return approvalQueue.stream().sorted((a, b) -> {
			return a.getCreatedTime().compareTo(b.getCreatedTime());
		}).collect(Collectors.toList());
	}
	
	
	public void approveProductById(Long id) {

		Optional<ApprovalQueue> optionalApprovalQueue = productApprovalDao.findById(id);
		if(!optionalApprovalQueue.isPresent()) {
			throw new ProductException(" No Product with approvalId exists ." + id);
		}

		ApprovalQueue approvalQueue = optionalApprovalQueue.get();
		Long productId = approvalQueue.getProductId();
		Optional<Product> optionalProduct = productDao.findById(productId);
		Product existingPrduct = optionalProduct.get();
		existingPrduct.setApprovalSatus(1);
		productDao.save(existingPrduct);
		productApprovalDao.deleteById(id);
		
	}
	
	public void rejectProductById(Long id) {

		Optional<ApprovalQueue> optionalApprovalQueue = productApprovalDao.findById(id);
		if(!optionalApprovalQueue.isPresent()) {
			throw new ProductException(" No Product with approvalId exists ." + id);
		}
		productApprovalDao.deleteById(id);
	}
	
	
	public List<Product> searchProductsWith(String productName,Double maxPrice, Date createdTime,
			 Date updatedTime) {
			return productDao.searchProductsWith(productName, maxPrice, createdTime, updatedTime);
	}
	
	

}
