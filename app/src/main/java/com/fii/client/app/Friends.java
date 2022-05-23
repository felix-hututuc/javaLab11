package com.fii.client.app;

public class Friends {

    private Integer id_friends;

    private Integer friend_1;

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

    @Override
    public String toString() {
        return "Friends{" +
                "id_friends=" + id_friends +
                ", friend_1=" + friend_1 +
                ", friend_2=" + friend_2 +
                '}';
    }
}
