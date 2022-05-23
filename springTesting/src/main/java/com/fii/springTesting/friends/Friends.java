package com.fii.springTesting.friends;

import javax.persistence.*;

@NamedQuery(name = "getMostPopularIds",
            query = "select friend_1, count(friend_1) as c from Friends group by friend_1 order by c desc")
@Entity
@Table(name = "friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_friends", nullable = false)
    private Integer id_friends;

    @Column(name = "friend_1")
    private Integer friend_1;

    @Column(name = "friend_2")
    private Integer friend_2;

    public Friends() {}

    public Friends(Integer friend_1, Integer friend_2)  {
        this.friend_1 = friend_1;
        this.friend_2 = friend_2;
    }

    public Integer getId_friends() {
        return id_friends;
    }

    public void setId_friends(Integer id_friends) {
        this.id_friends = id_friends;
    }

    public Integer getFriend_1() {
        return friend_1;
    }

    public void setFriend_1(Integer friend_1) {
        this.friend_1 = friend_1;
    }

    public Integer getFriend_2() {
        return friend_2;
    }

    public void setFriend_2(Integer friend_2) {
        this.friend_2 = friend_2;
    }
}
