package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Admin;

@Service
public interface AdminService {
	void addAdmin(Admin admin);
	boolean loginValidate(Admin admin);

}
