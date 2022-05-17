package com.fii.serverRest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server extends Thread {

    public static final int PORT = 8765;
    private final Set<User> users = Collections.synchronizedSet(new HashSet<>());
    private boolean open = true;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (open) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(60 * 1000);
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    public boolean userExists(User user) {
        return users.contains(user);
    }

    public void closeServer() {
        open = false;
    }

    public User getUserByName(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }
}
