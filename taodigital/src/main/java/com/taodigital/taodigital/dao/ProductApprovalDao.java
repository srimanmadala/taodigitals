package com.taodigital.taodigital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taodigital.taodigital.entity.ApprovalQueue;

@Repository
public interface ProductApprovalDao extends JpaRepository<ApprovalQueue, Long> {

}

