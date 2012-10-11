package edu.java.neo4jProject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.java.neo4jProject.entity.Friendship;
import edu.java.neo4jProject.entity.RelationshipsTypes;
import edu.java.neo4jProject.entity.User;
import edu.java.neo4jProject.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext-test.xml" })
@Transactional
public class UserRelationsTest {

	private static final String USERNAME = "user 1";
	private static final String USERNAME_2 = "user 2";

	@Autowired
	private Neo4jOperations template;

	@Autowired
	private UserService userService;

	private User user1;
	private User user2;
	private Friendship friendship;

	@Test
	public void shallCreateRelationshipBetweenUsers() {
		user1 = userService.save(new User(USERNAME));
		user2 = userService.save(new User(USERNAME_2));

		// user1 -- friend -- user2
		friendship = new Friendship(user1, user2);
		user1.addFriendship(friendship);

		Relationship relation = template.getRelationshipBetween(user1, user2,
				RelationshipsTypes.FRIENDS);

		Assert.assertNotNull(relation);
	}

	@Test
	public void shallDuplicateRelationship() {
		user1 = userService.save(new User(USERNAME));
		user2 = userService.save(new User(USERNAME_2));
		// NOTHING HAPPENS
		friendship = new Friendship(user1, user2);
		user1.addFriendship(friendship);
	}

}
