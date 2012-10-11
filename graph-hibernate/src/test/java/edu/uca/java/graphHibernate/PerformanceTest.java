package edu.uca.java.graphHibernate;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.uca.java.graphHibernate.config.MainConfig;
import edu.uca.java.graphHibernate.entity.UserGraph;
import edu.uca.java.graphHibernate.repository.UserRepository;
import edu.uca.java.graphHibernate.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PerformanceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	public void shallPerformTest() {
		UserGraph userOne, userTwo;

		userOne = userRepository.findOne(5000L);
		userTwo = userRepository.findOne(7000L);

		long startTime = System.currentTimeMillis();

		List<UserGraph> mutualFriends = userService.getMutualFriends(userOne,
				userTwo);

		long finishTime = System.currentTimeMillis();

		System.out.println("Mutual friends: " + (finishTime - startTime)
				+ " ms");

		startTime = System.currentTimeMillis();

		List<UserGraph> recommendedFriends = userService
				.getRecommendationFriends(userOne);

		finishTime = System.currentTimeMillis();

		System.out.println("Recommended friends: " + (finishTime - startTime)
				+ " ms");
	}

}
