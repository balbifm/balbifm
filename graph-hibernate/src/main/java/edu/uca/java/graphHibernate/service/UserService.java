package edu.uca.java.graphHibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uca.java.graphHibernate.entity.Friendship;
import edu.uca.java.graphHibernate.entity.UserGraph;
import edu.uca.java.graphHibernate.repository.FriendshipRepository;
import edu.uca.java.graphHibernate.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendshipRepository friendshipRepository;

	public UserGraph save(UserGraph user) {
		return userRepository.save(user);
	}

	public UserGraph findOne(Long id) {
		return userRepository.findOne(id);
	}

	public List<UserGraph> getFriends(UserGraph user) {
		return userRepository.getFriends(user.getId());
	}

	public List<UserGraph> getMutualFriendsFalse(UserGraph user1,
			UserGraph user2) {
		List<UserGraph> friendsUser1 = getFriends(user1);
		List<UserGraph> friendsUser2 = getFriends(user2);
		List<UserGraph> mutualFriends = new ArrayList<UserGraph>();
		for (UserGraph user : friendsUser1) {
			for (UserGraph userTwo : friendsUser2) {
				if (user.getId().equals(userTwo.getId())) {
					mutualFriends.add(user);
				}
			}
		}
		return mutualFriends;
	}

	public List<UserGraph> getMutualFriends(UserGraph userOne, UserGraph userTwo) {
		return userRepository
				.getMutualFriends(userOne.getId(), userTwo.getId());
	}

	public List<UserGraph> getRecommendationFriends(UserGraph user) {
		List<UserGraph> friends = getFriends(user);
		for (UserGraph userGraph : friends) {
			getFriends(userGraph);
		}
		return null;
	}

	public Friendship createFriendshipBetween(UserGraph user1, UserGraph user2) {
		return friendshipRepository.save(new Friendship(user1, user2));
	}
}
