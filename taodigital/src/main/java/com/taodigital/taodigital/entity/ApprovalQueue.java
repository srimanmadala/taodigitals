package com.taodigital.taodigital.entity;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name="Product")
public class ApprovalQueue { 
		

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
		
		@Column
		private Long productId ;
		
		@Column
		private String productName ;

		@Column
		private Integer approvalSatus;
		
		@Column
		private Date createdTime;
		
		@Column
		private Date updatedTime;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
		

		public Integer getApprovalSatus() {
			return approvalSatus;
		}

		public void setApprovalSatus(Integer approvalSatus) {
			this.approvalSatus = approvalSatus;
		}

		public Date getCreatedTime() {
			return createdTime;
		}

		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}

		public Date getUpdatedTime() {
			return updatedTime;
		}

		public void setUpdatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}
		
	}

