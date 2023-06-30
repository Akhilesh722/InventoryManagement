package com.example.admin.service;

import com.example.admin.Entity.Admin;
import com.example.admin.exception.NoAdminFoundException;


public interface AdminService {

	public Admin createAdmin(Admin admin);
	public String deleteAdmin(int id);
	public Admin getAdminbyid(int id);
	public Admin getAdminbyEmail(String email) throws NoAdminFoundException;
	
	
	
	
}
