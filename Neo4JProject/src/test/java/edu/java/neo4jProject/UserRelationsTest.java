package edu.java.neo4jProject;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

import edu.java.neo4jProject.entity.Friendship;
import edu.java.neo4jProject.entity.RelationshipsTypes;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;
import edu.java.neo4jProject.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class UserRelationsTest {

	private static final String USERNAME = "user 1";
	private static final String USERNAME_2 = "user 2";
	private static final String USERNAME_3 = "user 3";
	private static final String USERNAME_4 = "user 4";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Neo4jOperations template;

	@Autowired
	private UserService userService;

	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private Friendship friendship;

	@Before
	public void setUp() {
		user1 = userRepository.save(new User(USERNAME));
		user2 = userRepository.save(new User(USERNAME_2));
		user3 = userRepository.save(new User(USERNAME_3));
		user4 = userRepository.save(new User(USERNAME_4));

		// user1 -- friend -- user2
		friendship = new Friendship(user1, user2);
		user1.addFriendship(friendship);

		// user1 -- friend -- user3
		friendship = new Friendship(user1, user3);
		user1.addFriendship(friendship);

		// user2 -- friend -- user4
		friendship = new Friendship(user2, user4);
		user2.addFriendship(friendship);

		// user2 -- friend -- user4
		friendship = new Friendship(user2, user3);
		user2.addFriendship(friendship);

		// user1 -- friend -- user4
		friendship = new Friendship(user1, user4);
		user1.addFriendship(friendship);
	}

	@After
	public void tearDown() {
		// userRepository.delete(user1.getId());
		// userRepository.delete(user2.getId());
		// userRepository.delete(user3.getId());
	}

	@Test
	public void shallCreateRelationshipBetweenUsers() {

		Relationship relation = template.getRelationshipBetween(user1, user2,
				RelationshipsTypes.FRIENDS);

		Assert.assertNotNull(relation);
	}

	@Test
	public void shallGetFriends() {

		Iterable<User> friends = userService.getFriends(user1);
		Assert.assertTrue(Iterables.size(friends) == 3);

		friends = userService.getFriends(user3);
		Assert.assertTrue(Iterables.size(friends) == 2);
	}

	@Test
	public void shallGetMutualFriends() {
		List<User> mutualFriends = userService.getMutualFriends(user3, user4);
		Assert.assertTrue(mutualFriends.size() == 2);

		mutualFriends = userService.getMutualFriends(user1, user3);
		Assert.assertTrue(mutualFriends.size() == 1);
	}
}
