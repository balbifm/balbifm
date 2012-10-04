package edu.java.neo4jProject.service;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.java.neo4jProject.entity.Friendship;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class UserServiceTest {

	private static final String USERNAME = "user 1";
	private static final String USERNAME_2 = "user 2";
	private static final String USERNAME_3 = "user 3";
	private static final String USERNAME_4 = "user 4";

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	Neo4jOperations template;

	@Test
	public void shallGetMutualFriends() {
		User user1 = userRepository.save(new User(USERNAME));
		User user2 = userRepository.save(new User(USERNAME_2));
		User user3 = userRepository.save(new User(USERNAME_3));
		// User user4 = userRepository.save(new User(USERNAME_4));

		Friendship friendship1 = template.save(new Friendship(user1, user2));
		Friendship friendship2 = template.save(new Friendship(user2, user3));
		Friendship friendship3 = template.save(new Friendship(user1, user3));

		//List<User> mutualFriends = userService.getMutualFriends(user1, user2);

	}
}
