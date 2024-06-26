package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Admin;
import com.example.service.AdminService;

@RestController
@RequestMapping("/api/v1")
public class AdminController {
	@Autowired
	AdminService service;

	@PostMapping("/addAdmin")
	public ResponseEntity<Object> addAdmin(@RequestBody Admin admin){
		service.addAdmin(admin);
		return new ResponseEntity<>("Admin added",HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/adminLogin")
	public ResponseEntity<Object> adminLogin(@RequestBody Admin admin){
		boolean flag = service.loginValidate(admin);
		return new ResponseEntity<>(flag,HttpStatus.OK);
	}


}
