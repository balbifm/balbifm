package edu.java.neo4jProject.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

import edu.java.neo4jProject.entity.Friendship;
import edu.java.neo4jProject.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class UserServiceTest {

	private static final String USERNAME = "user 1";
	private static final String USERNAME_2 = "user 2";
	private static final String USERNAME_3 = "user 3";
	private static final String USERNAME_4 = "user 4";
	private static final String USERNAME_5 = "user 5";
	private static final String USERNAME_6 = "user 6";

	@Autowired
	private UserService userService;

	private User user1;
	private User user2;
	private User user3;
	private User user4;
	private User user5;
	private User user6;
	private Friendship friendship;

	@Before
	public void setUp() {
		user1 = userService.save(new User(USERNAME));
		user2 = userService.save(new User(USERNAME_2));
		user3 = userService.save(new User(USERNAME_3));
		user4 = userService.save(new User(USERNAME_4));
		user5 = userService.save(new User(USERNAME_5));
		user6 = userService.save(new User(USERNAME_6));

		// user1 -- friend -- user2
		friendship = new Friendship(user1, user2);
		user1.addFriendship(friendship);

		// user1 -- friend -- user3
		friendship = new Friendship(user1, user3);
		user1.addFriendship(friendship);

		// user2 -- friend -- user4
		friendship = new Friendship(user2, user4);
		user2.addFriendship(friendship);

		// user2 -- friend -- user3
		friendship = new Friendship(user2, user3);
		user2.addFriendship(friendship);

		// user1 -- friend -- user4
		friendship = new Friendship(user1, user4);
		user1.addFriendship(friendship);

		// user1 -- friend -- user5
		friendship = new Friendship(user1, user5);
		user1.addFriendship(friendship);
		
		// user5 -- friend -- user6
		friendship = new Friendship(user5, user6);
		user5.addFriendship(friendship);
	}

	@Test
	public void shallGetFriends() {

		Iterable<User> friends = userService.getFriends(user1);
		Assert.assertTrue(Iterables.size(friends) == 4);

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

	@Test
	public void shallGetFriendsRecomendations() {
		List<User> friendsRecomendations = userService
				.getFriendsRecomendations(user3);
		Assert.assertTrue(friendsRecomendations.size() == 2);
		User friendRecomendation = Iterables.getFirst(friendsRecomendations,
				null);
		Assert.assertTrue(friendRecomendation.getUsername().equals(USERNAME_4));
	}
}
