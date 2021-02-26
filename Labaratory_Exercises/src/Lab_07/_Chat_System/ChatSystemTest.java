package Lab_07._Chat_System;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

class NoSuchUserException extends Exception {
    public NoSuchUserException(String name) {
        super("NoSuchUserException " + name);
    }
}

class NoSuchRoomException extends Exception {
    public NoSuchRoomException(String name) {
        super("NoSuchRoomExcеption " + name);
    }
}

class ChatRoom {
    private String name;
    private Set<String> users;

    public ChatRoom(String name) {
        this.name = name;
        users = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void addUser(String username) {
        users.add(username);
    }

    public void removeUser(String username) throws NoSuchUserException {
        try {
            if (!users.contains(username))
                throw new NoSuchUserException(username);

            users.remove(username);
        } catch (NoSuchUserException ignored) {

        }
    }

    public boolean hasUser(String username) {
        return users.contains(username);
    }

    public int numUsers() {
        return users.size();
    }

    @Override
    public String toString() {
        String usersString = String.join("\n", this.users);
        if (usersString.isEmpty())
            usersString += "EMPTY";
        return String.format("%s\n%s\n", this.name, usersString);
    }
}

class ChatSystem {

    private TreeMap<String, ChatRoom> chatRooms;
    private List<String> users;

    public ChatSystem() {
        users = new LinkedList<>();
        chatRooms = new TreeMap<>();
    }

    public void addRoom(String roomName) {
        chatRooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName) {
        chatRooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) {
        return chatRooms.get(roomName);
    }

    public void register(String username) {
        this.users.add(username);
        if (!chatRooms.isEmpty())
            this.chatRooms.values().stream().min(Comparator.comparing(ChatRoom::numUsers)
                    .thenComparing(ChatRoom::getName)).get().addUser(username);
    }

    public void registerAndJoin(String username, String roomname) {
        if (!this.users.contains(username))
            this.users.add(username);

        this.chatRooms.get(roomname).addUser(username);
    }

    public void joinRoom(String username, String roomname) {
        try {
            if (!this.chatRooms.containsKey(roomname))
                throw new NoSuchRoomException(roomname);

            this.chatRooms.get(roomname).addUser(username);
        } catch (NoSuchRoomException ex) {

        }
    }

    public void leaveRoom(String username, String roomname) throws NoSuchRoomException, NoSuchUserException {
        try {
            if (!this.chatRooms.containsKey(roomname))
                throw new NoSuchRoomException(roomname);

            this.chatRooms.get(roomname).removeUser(username);
        } catch (NoSuchRoomException | NoSuchUserException ex) {

        }
    }

    public void followFriend(String username, String friend_username) {
        this.chatRooms.entrySet().stream().filter(i -> i.getValue().hasUser(friend_username))
                .forEach(i -> i.getValue().addUser(username));
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException, NoSuchUserException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));

            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());

            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, params);

                    }
                }
            }
        }
    }

}
