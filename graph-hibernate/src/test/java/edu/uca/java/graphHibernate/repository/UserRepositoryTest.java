package edu.uca.java.graphHibernate.repository;

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

import edu.uca.java.graphHibernate.config.MainConfig;
import edu.uca.java.graphHibernate.entity.Friendship;
import edu.uca.java.graphHibernate.entity.UserGraph;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendshipRepository friendshipRepository;

	@Before
	public void setUp() {

	}

	@Test
	public void shallCreateUser() {
		UserGraph user = new UserGraph();
		user.setUsername("user 1");
		UserGraph user2 = new UserGraph();
		user2.setUsername("user 2");
		user = userRepository.save(user);
		user2 = userRepository.save(user2);

		Friendship friendship = new Friendship();
		friendship.setUserOne(user);
		friendship.setUserTwo(user2);

		friendshipRepository.save(friendship);
		//
		// user.addFriendship(friendship);
		// user2.addFriendship(friendship);

		UserGraph userDb = userRepository.findOne(user.getId());
		Assert.assertNotNull(userDb);
		Assert.assertTrue(userDb.getUsername().equals(user.getUsername()));
	}

	@Test
	public void shallUpdateUser() {
	}

}
