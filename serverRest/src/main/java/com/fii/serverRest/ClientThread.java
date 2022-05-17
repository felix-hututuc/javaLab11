package com.fii.serverRest;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ClientThread extends Thread{
    private final Server server;
    private final Socket socket;
    private User client = null;
    private final ThreadLocal<Boolean> running = new ThreadLocal<>();
    private final ThreadLocal<Boolean> loggedIn = new ThreadLocal<>();

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

             PrintWriter output = new PrintWriter(socket.getOutputStream())) {
            running.set(true);
            loggedIn.set(false);
            while (running.get()) {


                String request = input.readLine();
                String answer = null;
                if (request != null) {
                    answer = handleRequest(request);
                }

                if (answer != null) {
                    System.out.println(answer);
                    output.println(answer);
                    output.flush();
                    if (answer.equals("Server stopped")) {
                        System.out.println("Exiting server...");
                        //System.exit(0);
                        break;
                    } else if (answer.equals("Bye bye...")) {
                        System.out.println("Client left...");
                        break;
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Connection timed out ... ");
        } catch (IOException e) {
            System.err.println("Communication error...\n" + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleRequest (String request) {
        if (request.startsWith("register ")) {              //REGISTER
            if (loggedIn.get()) {
                return "Already logged in ...";
            }

            String username = request.substring(9);
            if (username.isEmpty() || StringUtils.containsAny(username, " ,/")) {
                return "Invalid username!";
            }

            //loggedIn.set(true);
            if (server.userExists(new User(username))) {
                return "Username already exists!";
            }

            client = new User(username);
            server.addUser(client);

            return "Created account...";
        } else if (request.startsWith("login ")) {          //LOGIN
            if (loggedIn.get()) {
                return "Already logged in ...";
            }

            String username = request.substring(6);
            if (username.isEmpty() || StringUtils.containsAny(username, " ,/")) {
                return "Invalid username!";
            }
            if (!server.userExists(new User(username))) {
                return "User not registered!";
            }

            loggedIn.set(true);
            client = new User(username);
            return "Logged in ...";
        } else if (request.startsWith("friend ")) {         //FRIEND
            if (!loggedIn.get()) {
                return "Not logged in ...";
            }
            String newFriends = request.substring(7);

            StringTokenizer st = new StringTokenizer(newFriends, " ");
            while (st.hasMoreTokens()) {
                String friendUsername = st.nextToken();
                User newFriend = server.getUserByName(friendUsername);
                if (newFriend == null) {
                    return "User " + friendUsername + " does not exist.";
                }
                client.addFriend(newFriend);
//                newFriend.addFriend(client);
            }
            return "Added friends ...";
        } else if (request.startsWith("send ")) {           //SEND
            if (!loggedIn.get()) {
                return "Not logged in ...";
            }
            String message = request.substring(5);
            if (message.isEmpty()) {
                return "Empty message ...";
            }
            Set<User> friendList = client.getFriendList();
            for (User friend : friendList) {
                sendMessage(client.getUsername(), friend.getMessageFile(), message);
            }
            return "Message sent ...";
        } else if (request.equals("read")) {                //READ
            if (!loggedIn.get()) {
                return "Not logged in ...";
            }
            String messages = readMessages();
            if (messages != null) {
                messages = messages.replace("\n", "--newline--");
            }
            return messages;
        } else if (request.equals("stop")) {                //STOP
            running.set(false);
            return "Server stopped";
        } else if (request.equals("exit")) {                //EXIT
            running.set(false);
            server.closeServer();
            return "Bye bye...";
        } else {
            return "Unknown command";
        }
    }

    private synchronized void sendMessage (String senderName, File filePath, String message) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath, true))) {
            writer.write(senderName + ": " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String readMessages () {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(client.getMessageFile()))) {
            StringBuilder result = new StringBuilder();
            String message;
            
            while ((message = reader.readLine()) != null) {
                result.append(message).append("\n");
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
