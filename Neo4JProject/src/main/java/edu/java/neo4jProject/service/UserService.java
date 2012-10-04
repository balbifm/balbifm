package edu.java.neo4jProject.service;

import java.util.List;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.java.neo4jProject.entity.RelationshipsTypes;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> getMutualFriends(User user1, User user2) {

		return userRepository.getMutualFriends(user1.getId(), user2.getId());
	}

	public List<User> getFriends(User user) {
		TraversalDescription description = Traversal
				.description()
				.breadthFirst()
				.relationships(
						DynamicRelationshipType
								.withName(RelationshipsTypes.FRIENDS))
				.evaluator(Evaluators.atDepth(1));

		return Lists.newArrayList(userRepository.findAllByTraversal(user,
				description));
	}
}
