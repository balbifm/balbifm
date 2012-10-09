package edu.java.neo4jProject;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class PerformanceTest {

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

		System.out.println("That took: " + (finishTime - startTime) + " ms");
	}

}
