package edu.uca.java.graphHibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Friendship {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private UserGraph userOne;

	@ManyToOne
	private UserGraph userTwo;

	public Friendship() {

	}

	public Friendship(UserGraph user1, UserGraph user2) {
		userOne = user1;
		userTwo = user2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserGraph getUserOne() {
		return userOne;
	}

	public void setUserOne(UserGraph userOne) {
		this.userOne = userOne;
	}

	public UserGraph getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(UserGraph userTwo) {
		this.userTwo = userTwo;
	}

}
