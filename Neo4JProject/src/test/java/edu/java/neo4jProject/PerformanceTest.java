package edu.java.neo4jProject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.java.neo4jProject.entity.Friendship;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PerformanceTest {

	private static final int N_NODES = 10000;
	private static final int N_RELATIONS = 1000000;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shallPerformTest() {

		int random, random2;
		User userOne, userTwo;

		userOne = userRepository.findOne(3000L);
		userTwo = userRepository.findOne(7000L);

		long startTime = System.currentTimeMillis();

		List<User> mutualFriends = userRepository.getMutualFriends(
				userOne.getId(), userTwo.getId());

		long finishTime = System.currentTimeMillis();

		System.out.println("Mutual friends: " + (finishTime - startTime)
				+ " ms");

//		startTime = System.currentTimeMillis();
//
//		List<User> recommendedFriends = userRepository
//				.getRecommendationFriends(userOne.getId());
//
//		finishTime = System.currentTimeMillis();
//
//		System.out.println("Recommended friends: " + (finishTime - startTime)
//				+ " ms");
	}

	// @Test
	// public void setUpDB() {
	//
	// createNodes();
	// createRelationships();
	// }

	private void createRelationships() {
		int random, random2;
		Friendship friendship;
		User userOne, userTwo;

		for (int i = 0; i < N_RELATIONS; i++) {
			random = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			random2 = (int) (Math.random() * ((N_NODES) + 1)) + 1;
			if (random <= N_NODES && random2 <= N_NODES) {
				userOne = userRepository.findOne((long) random);
				userTwo = userRepository.findOne((long) random2);
				friendship = new Friendship(userOne, userTwo);
				userOne.addFriendship(friendship);
			}
			if (i % 5000 == 0)
				System.out.println(i);
		}

	}

	private void createNodes() {
		User user;

		for (int i = 1; i <= N_NODES; i++) {
			user = new User("" + i);
			userRepository.save(user);
		}

		System.out.println("FINISH USERS");

	}

}
