package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Accounts {
    public static Vector<User> users;
    public static String currentUsername;
    private static String saveName = "accounts.sav";

    public static void save() {
        try {
            FileOutputStream fos = new FileOutputStream(saveName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Saved account modifications");
    }

    public static void load() {
        users = new Vector<User>();
        currentUsername = "";
        try {
            FileInputStream fin = new FileInputStream(saveName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Vector<?> temp = (Vector<?>) ois.readObject();
            for (Object obj : temp) {
                users.add((User) obj);
            }
            ois.close();
            fin.close();
        } catch (IOException e) {
            System.err.println(e);
            System.out.println("Creating new accounts list");
            users.add(new User("admin", "admin"));
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        System.out.println("Loaded accounts list:");
        for (User user : users) {
            System.out.println("[usr] " + user.getUsername() + " [pwd] " + user.getPassword());
        }
        save();
    }

    public static int login(String username, String password) {
        if (username.isEmpty()) {
            System.out.println("Invalid username");
            return -2;
        }
        if (password.isEmpty()) {
            System.out.println("Invalid password");
            return -3;
        }
        for (User user : Accounts.users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    System.out.println("Validated as: " + username);
                    return 1;
                } else {
                    System.out.println("Incorrect password");
                    return 0;
                }
            }
        }
        System.out.println("No such user");
        return -1;
    }

    public static int register(String username, String password) {
        if (username.isEmpty()) {
            System.out.println("Invalid username");
            return -2;
        }
        for (User user : Accounts.users) {
            if (user.getUsername().equals(username)) {
                System.out.println("User already exists");
                return 0;
            }
        }
        if (password.isEmpty()) {
            System.out.println("Invalid password");
            return -3;
        }
        Accounts.users.add(new User(username, password));
        Accounts.save();
        System.out.println("Registered new user");
        return 1;
    }
}
