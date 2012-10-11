package edu.java.neo4jProject.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.repository.query.Param;

import edu.java.neo4jProject.entity.User;

public interface UserRepository extends GraphRepository<User>,
		RelationshipOperationsRepository<User>, NamedIndexRepository<User> {

	@Query("START a=node({user1}), c=node({user2}) " + 
			"MATCH (a)-[:friends]-(b)-[:friends]-(c) " +
			"RETURN b")
	List<User> getMutualFriends(@Param("user1") Long userOneId,
			@Param("user2") Long userTwoId);

	@Query("START a=node({user}) " +
			"MATCH (a)-[:friends*2..2]-(b) " + 
			"WHERE not(a-[:friends]-b) " +
			"RETURN distinct b " +			
			"ORDER BY COUNT(*) DESC " +
			"LIMIT 10" )
	List<User> getRecommendationFriends(@Param("user") Long userId);
}
