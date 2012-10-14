package edu.java.neo4jProject.service;

import java.util.List;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.java.neo4jProject.entity.RelationshipsTypes;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public Iterable<User> getMutualFriendsCypher(User user1, User user2) {

		return userRepository.getMutualFriends(user1.getId(), user2.getId());
	}

	public List<User> getMutualFriendsTraversal(User user1, User user2) {

		// TraversalDescription description =
		// Traversal.description().breadthFirst()

		return null;
	}

	public Iterable<User> getFriendsTraversal(User user) {
		TraversalDescription description = Traversal
				.description()
				.breadthFirst()
				.relationships(
						DynamicRelationshipType
								.withName(RelationshipsTypes.FRIENDS))
				.evaluator(Evaluators.atDepth(1));

		return userRepository.findAllByTraversal(user, description);
	}

	public Iterable<User> getFriendsCypher(User user) {
		return userRepository.getFriends(user.getId());
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public Iterable<User> getFriendsRecomendations(User user) {
		return userRepository.getRecommendationFriends(user.getId());
	}

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	public int countFriends(User user) {
		return userRepository.countFriends(user.getId());

	}
}
