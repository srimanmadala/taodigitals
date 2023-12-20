package com.taodigital.taodigital.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taodigital.taodigital.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	@Query("select a from PRODUCT a where a.productName=COALESCE(:productName,a.productName) \r\n"
			+ "			and a.maxPrice=COALESCE(:maxPrice,a.maxPrice) \r\n"
			+ "			and a.createdTime=COALESCE(:createdTime,a.createdTime) \r\n"
			+ "			and a.updatedTime=COALESCE(:updatedTime,a.updatedTime) ")
	List<Product> searchProductsWith(@Param("productName") String productName, @Param("maxPrice") Double maxPrice,
			@Param("createdTime") Date createdTime, @Param("updatedTime") Date updatedTime);

}
