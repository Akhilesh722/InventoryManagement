package com.example.admin.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.admin.Entity.Admin;


@Repository
public interface AdminRepo extends CrudRepository<Admin, Integer> {
	public Optional<Admin> findByEmail(String email);

}
