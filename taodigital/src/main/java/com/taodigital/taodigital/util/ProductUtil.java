package com.taodigital.taodigital.util;

import java.util.Date;

import com.taodigital.taodigital.entity.ApprovalQueue;
import com.taodigital.taodigital.entity.Product;

public class ProductUtil {
	
	public static ApprovalQueue getproductApprovalQueue(Product product) {
		ApprovalQueue approvalQueue = new ApprovalQueue();
		approvalQueue.setProductId(product.getId());
		approvalQueue.setProductName(product.getProductName());
		approvalQueue.setCreatedTime(new Date());
		approvalQueue.setUpdatedTime(new Date());
		return approvalQueue;
	}

}
 