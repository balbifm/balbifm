package edu.java.neo4jProject.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class User {

	@GraphId
	private Long id;
	@Indexed(indexName = "username")
	private String username;

	@RelatedToVia
	private Set<Friendship> friendships = new HashSet<Friendship>();

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addFriendship(Friendship friendship) {
		friendships.add(friendship);
	}

	public Set<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(Set<Friendship> friendships) {
		this.friendships = friendships;
	}

	public String toString() {
		return username;
	}
}
