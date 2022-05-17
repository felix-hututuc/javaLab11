package com.fii.serverRest;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {
    private final String username;
    private final Set<User> friendList = new HashSet<>();
    private final File messageFile;

    public User(String username) {
        this.username = username;
         String messagePath = "E:\\Facultate\\Informatica_2020\\Semestrul_4\\Java\\javaLab10\\Server\\messageFiles\\msg" + username + ".txt";
         messageFile = new File(messagePath);
    }

    public void addFriend(User friend) {
        friendList.add(friend);
    }

    public File getMessageFile() {
        return messageFile;
    }

    public Set<User> getFriendList() {
        return friendList;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
