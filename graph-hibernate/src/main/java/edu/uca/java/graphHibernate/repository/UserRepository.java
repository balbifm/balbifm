package edu.uca.java.graphHibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.uca.java.graphHibernate.entity.UserGraph;

public interface UserRepository extends JpaRepository<UserGraph, Long> {

	@Query("select u "
			+ "from UserGraph u "
			+ "where u.id in "
			+ "(select case when userOne.id = ?1 then userTwo else userOne end "
			+ "from Friendship where ?1 in (userOne.id,userTwo.id)) ")
	List<UserGraph> getFriends(Long id);

}
