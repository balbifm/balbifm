package edu.uca.java.graphHibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.uca.java.graphHibernate.entity.UserGraph;

public interface UserRepository extends JpaRepository<UserGraph, Long> {

	@Query("SELECT u "
			+ "FROM UserGraph u "
			+ "WHERE u.id IN "
			+ "(SELECT CASE WHEN userOne.id = ?1 THEN userTwo ELSE userOne END "
			+ "FROM Friendship where ?1 IN (userOne.id,userTwo.id)) ")
	List<UserGraph> getFriends(Long id);

	@Query("SELECT u "
			+ "FROM UserGraph u "
			+ "WHERE u.id IN "
			+ "(SELECT CASE WHEN userOne.id = ?1 THEN userTwo ELSE userOne END "
			+ "FROM Friendship where ?1 IN (userOne.id,userTwo.id)) "
			+ " AND u IN (	SELECT u "
			+ "FROM UserGraph u "
			+ "WHERE u.id IN "
			+ "(SELECT CASE WHEN userOne.id = ?2 THEN userTwo ELSE userOne END "
			+ "FROM Friendship where ?2 IN (userOne.id,userTwo.id)))")
	List<UserGraph> getMutualFriends(Long userOneId, Long UserTwoId);

}
