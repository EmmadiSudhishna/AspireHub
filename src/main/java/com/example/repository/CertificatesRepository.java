package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Certificates;

@Repository
public interface CertificatesRepository extends JpaRepository<Certificates, Integer> {

	 List<Certificates> findByJobseekersUserId(int userId);
	
}
