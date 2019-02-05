package com.cat.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cat.entities.Users;

/**
 * 
 * @author jebel haythem
 *
 */

public interface UsersRepository extends JpaRepository<Users, Long>  {
	@Query("select u from Users u where u.id = :x and u.password= :y ")
	public Users findusers(@Param("x")Long id,@Param("y")String password);
	@Query("select u from Users u where u.id = :x ")
	public Users findOne(@Param("x")Long id);

}
