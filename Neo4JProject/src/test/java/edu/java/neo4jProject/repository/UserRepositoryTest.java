package edu.java.neo4jProject.repository;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.java.neo4jProject.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class UserRepositoryTest {

	private static final String USERNAME = "user 1";

	private static final String USERNAME_2 = "user 2";

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shallSaveUser() {
		User user = new User(USERNAME);
		user = userRepository.save(user);

		User userDb = userRepository.findOne(user.getId());
		Assert.assertNotNull(userDb);
		Assert.assertTrue(user.getUsername().equals(USERNAME));
	}

	@Test(expected = IllegalStateException.class)
	public void shallDeleteUser() {
		User user = new User(USERNAME);
		user = userRepository.save(user);
		userRepository.delete(user);

		userRepository.findOne(user.getId());
	}

	@Test
	public void shallUpdateUser() {
		User user = new User(USERNAME);
		user = userRepository.save(user);

		user.setUsername(USERNAME_2);
		userRepository.save(user);

		User userDb = userRepository.findOne(user.getId());
		Assert.assertTrue(userDb.getUsername().equals(USERNAME_2));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void shallGetAllUsers() {
		User user1 = new User(USERNAME);
		User user2 = new User(USERNAME_2);

		userRepository.save(user1);
		userRepository.save(user2);

		List<User> users = ((List<User>) userRepository.findAll());
		Assert.assertTrue(users.size() == 2);
	}

}
