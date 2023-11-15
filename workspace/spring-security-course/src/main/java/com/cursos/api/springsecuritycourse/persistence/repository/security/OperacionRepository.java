package com.cursos.api.springsecuritycourse.persistence.repository.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursos.api.springsecuritycourse.persistence.security.Operation;

public interface OperacionRepository  extends JpaRepository<Operation, Long>{
	@Query("SELECT o FROM Operation o where o.permitAll = true")
	List<Operation> findByPubliccAcces();

}
