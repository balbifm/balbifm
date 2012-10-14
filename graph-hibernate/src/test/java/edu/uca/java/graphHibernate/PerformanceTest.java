package edu.uca.java.graphHibernate;

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
import edu.uca.java.graphHibernate.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PerformanceTest {

	private static final int N_NODES = 10000;
	private static final int N_RELATIONS = 1000000;

	private int size;
	private Long random, random2;
	private Long startTime, finishTime, queryTime;
	private UserGraph userOne;
	private UserGraph userTwo;

	@Autowired
	private UserService userService;

	@Test
	public void shallTestFriendsPerformance() {
		System.out.println("===== FRIENDS =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);

			startTime = System.currentTimeMillis();

			Iterable<UserGraph> friends = userService.getFriends(userOne);

			queryTime = System.currentTimeMillis();

			for (UserGraph user : friends) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " friends of " + random);
			System.out.print("Query time: " + (queryTime - startTime)
					+ " ms || ");
			System.out.print("Iterate time: " + (finishTime - queryTime)
					+ " ms || ");
			System.out.println("Total time: " + (finishTime - startTime)
					+ " ms");
		}
	}

	@Test
	public void shallTestMutualFriendsCypher() {
		System.out.println("===== MUTUAL FRIENDS =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);
			random2 = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);
			userTwo = userService.findOne(random2);

			startTime = System.currentTimeMillis();

			Iterable<UserGraph> mutualFriends = userService.getMutualFriends(
					userOne, userTwo);

			queryTime = System.currentTimeMillis();

			for (UserGraph user : mutualFriends) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " mutual friends of "
					+ random + " and " + random2);
			System.out.print("Query time: " + (queryTime - startTime)
					+ " ms || ");
			System.out.print("Iterate time: " + (finishTime - queryTime)
					+ " ms || ");
			System.out.println("Total time: " + (finishTime - startTime)
					+ " ms");
		}
	}

	// @Test
	// public void shallTestRecFriendsCypher() {
	// System.out.println("===== REC FRIENDS CYPHER =====");
	// for (int i = 0; i < 10; i++) {
	// size = 0;
	// random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);
	//
	// userOne = userService.findOne(random);
	//
	// startTime = System.currentTimeMillis();
	//
	// Iterable<UserGraph> recFriends = userService
	// .getRecommendationFriends(userOne);
	//
	// queryTime = System.currentTimeMillis();
	//
	// for (UserGraph user : recFriends) {
	// size++;
	// }
	//
	// finishTime = System.currentTimeMillis();
	//
	// System.out.println("Retrieved " + size + " rec friends of "
	// + random + " by cypher");
	// System.out.print("Query time: " + (queryTime - startTime)
	// + " ms || ");
	// System.out.print("Iterate time: " + (finishTime - queryTime)
	// + " ms || ");
	// System.out.println("Total time: " + (finishTime - startTime)
	// + " ms");
	// }
	// }

}
