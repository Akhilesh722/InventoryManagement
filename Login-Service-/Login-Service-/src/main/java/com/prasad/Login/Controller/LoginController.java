package com.prasad.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.Login.Entity.Login;
import com.prasad.Login.Service.LoginServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
public class LoginController {
	
	@Autowired
	LoginServiceImpl impl;
	
	
	@PostMapping("/loginC")
	public boolean login(@RequestBody Login loginc){
			
			return impl.login(loginc);
			
		}
		
	
	@PostMapping("/AdminLogin")
	public boolean AdminLogin(@RequestBody Login loginc){
		
		return impl.AdminLogin(loginc);
		
	}

}