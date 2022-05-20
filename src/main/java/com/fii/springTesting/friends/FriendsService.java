package com.fii.springTesting.friends;

import com.fii.springTesting.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class FriendsService {

    private final FriendsRepository friendsRepository;
    private final EntityManager entityManager;

    @Autowired
    public FriendsService(FriendsRepository friendsRepository, EntityManager entityManager) {
        this.friendsRepository = friendsRepository;
        this.entityManager = entityManager;
    }

    public List<Friends> findAllFriends() {
        return friendsRepository.findAll();
    }

    public void addFriends(Friends friends) {
        friendsRepository.save(friends);
    }

    public List<User> findMostPopular(Integer k) {

        return entityManager.createNativeQuery("select s.id, s.name from users s join (select friend_1, count(friend_1) as c from friends group by friend_1 order by c desc LIMIT :k) d on s.id = d.friend_1")
                .setParameter("k", k)
                .getResultList();
    }
}
