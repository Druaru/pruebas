package com.drr.springboot.backend.apirest.persistence.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.drr.springboot.backend.apirest.persistence.security.Operation;


public interface OperacionRepository  extends JpaRepository<Operation, Long>{
	@Query("SELECT o FROM Operation o where o.permitAll = true")
	List<Operation> findByPubliccAcces();

}
