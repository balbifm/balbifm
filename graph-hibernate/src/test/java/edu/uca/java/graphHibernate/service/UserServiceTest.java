package edu.uca.java.graphHibernate.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

import edu.uca.java.graphHibernate.config.MainConfig;
import edu.uca.java.graphHibernate.entity.UserGraph;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class UserServiceTest {

	private static final String USERNAME = "user 1";
	private static final String USERNAME_2 = "user 2";
	private static final String USERNAME_3 = "user 3";
	private static final String USERNAME_4 = "user 4";

	@Autowired
	private UserService userService;

	private UserGraph user1;
	private UserGraph user2;
	private UserGraph user3;
	private UserGraph user4;

	@Before
	public void setUp() {
		user1 = userService.save(new UserGraph(USERNAME));
		user2 = userService.save(new UserGraph(USERNAME_2));
		user3 = userService.save(new UserGraph(USERNAME_3));
		user4 = userService.save(new UserGraph(USERNAME_4));

		// user1 -- friend -- user2
		userService.createFriendshipBetween(user1, user2);

		// user1 -- friend -- user3
		userService.createFriendshipBetween(user1, user3);

		// user2 -- friend -- user4
		userService.createFriendshipBetween(user2, user4);

		// user2 -- friend -- user3
		userService.createFriendshipBetween(user2, user3);

		// user1 -- friend -- user4
		userService.createFriendshipBetween(user1, user4);
	}

	@Test
	public void shallGetFriends() {
		List<UserGraph> friends = userService.getFriends(user1);
		Assert.assertTrue(Iterables.size(friends) == 3);

		friends = userService.getFriends(user3);
		Assert.assertTrue(Iterables.size(friends) == 2);
	}

	@Test
	public void shallGetMutualFriends() {
		List<UserGraph> mutualFriends = userService.getMutualFriends(user3,
				user4);
		Assert.assertTrue(mutualFriends.size() == 2);

		mutualFriends = userService.getMutualFriends(user1, user3);
		Assert.assertTrue(mutualFriends.size() == 1);
	}

}
