package edu.java.neo4jProject.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = RelationshipsTypes.FRIENDS)
public class Friendship {

	@GraphId
	private Long id;

	@StartNode
	private User userOne;
	@EndNode
	private User userTwo;

	public Friendship() {

	}

	public Friendship(User userOne, User userTwo) {
		this.userOne = userOne;
		this.userTwo = userTwo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserOne(User userOne) {
		this.userOne = userOne;
	}

	public User getUserOne() {
		return userOne;
	}

	public void setUserTwo(User userTwo) {
		this.userTwo = userTwo;
	}

	public User getUserTwo() {
		return userTwo;
	}
}
