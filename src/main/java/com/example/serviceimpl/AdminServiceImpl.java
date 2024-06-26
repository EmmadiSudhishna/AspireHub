package com.example.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Admin;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminrepository;
	@Autowired
	public AdminServiceImpl(AdminRepository adminrepository) {
		this.adminrepository=adminrepository;

		}
	@Override
	public void addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		adminrepository.save(admin);
		
	}

	@Override
	public boolean loginValidate(Admin admin) {
		// TODO Auto-generated method stub
		Admin admin1 = adminrepository.findByuserNameAndPassword(admin.getUserName(), admin.getPassword());
		if(admin1==null) {
		return false;
		}
		else 
			return true;
	}

}
