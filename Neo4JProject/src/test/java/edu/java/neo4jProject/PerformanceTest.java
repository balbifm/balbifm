package edu.java.neo4jProject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PerformanceTest {

	private static final int N_NODES = 100000;

	private int size;
	private Long random, random2;
	private Long startTime, finishTime, queryTime;
	private User userOne;
	private User userTwo;

	@Autowired
	private UserService userService;

	@Test
	public void shallTestFriendsCypherPerformance() {
		System.out.println("===== FRIENDS CYPHER =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);

			startTime = System.currentTimeMillis();

			Iterable<User> friendsCypher = userService
					.getFriendsCypher(userOne);

			queryTime = System.currentTimeMillis();

			for (User user : friendsCypher) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " friends of " + random
					+ " by cypher");
			System.out.print("Query time: " + (queryTime - startTime)
					+ " ms || ");
			System.out.print("Iterate time: " + (finishTime - queryTime)
					+ " ms || ");
			System.out.println("Total time: " + (finishTime - startTime)
					+ " ms");
		}
	}

	@Test
	public void shallTestFriendsTraversalPerformance() {
		System.out.println("===== FRIENDS TRAVERSAL =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);

			startTime = System.currentTimeMillis();

			Iterable<User> friends = userService.getFriendsTraversal(userOne);

			queryTime = System.currentTimeMillis();

			for (User user : friends) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " friends of " + random
					+ " by traversal");
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
		System.out.println("===== MUTUAL FRIENDS CYPHER =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);
			random2 = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);
			userTwo = userService.findOne(random2);

			startTime = System.currentTimeMillis();

			Iterable<User> mutualFriends = userService.getMutualFriendsCypher(
					userOne, userTwo);

			queryTime = System.currentTimeMillis();

			for (User user : mutualFriends) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " mutual friends of "
					+ random + " and " + random2 + " by cypher");
			System.out.print("Query time: " + (queryTime - startTime)
					+ " ms || ");
			System.out.print("Iterate time: " + (finishTime - queryTime)
					+ " ms || ");
			System.out.println("Total time: " + (finishTime - startTime)
					+ " ms");
		}
	}

	@Ignore
	@Test
	public void shallTestRecFriendsCypher() {
		System.out.println("===== REC FRIENDS CYPHER =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);

			startTime = System.currentTimeMillis();

			Iterable<User> recFriends = userService
					.getFriendsRecomendations(userOne);

			queryTime = System.currentTimeMillis();

			for (User user : recFriends) {
				size++;
			}

			finishTime = System.currentTimeMillis();

			System.out.println("Retrieved " + size + " rec friends of "
					+ random + " by cypher");
			System.out.print("Query time: " + (queryTime - startTime)
					+ " ms || ");
			System.out.print("Iterate time: " + (finishTime - queryTime)
					+ " ms || ");
			System.out.println("Total time: " + (finishTime - startTime)
					+ " ms");
		}
	}

	@Test
	public void shallTestCountFriendsCypher() {
		System.out.println("===== COUNT FRIENDS CYPHER =====");
		for (int i = 0; i < 10; i++) {
			size = 0;
			random = (long) ((Math.random() * ((N_NODES) + 1)) + 1);

			userOne = userService.findOne(random);

			startTime = System.currentTimeMillis();

			int numFriends = userService.countFriends(userOne);

			finishTime = System.currentTimeMillis();

			System.out.println("Counted " + numFriends + " friends of "
					+ random + " by cypher");
			System.out.println("Query time: " + (finishTime - startTime)
					+ " ms || ");
		}
	}

}
