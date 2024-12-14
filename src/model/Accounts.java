package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Accounts {
    private static Vector<User> users;
    private static Vector<Save> saves;
    private static User currentUser;
    private static String accountsFilename = "accounts.obj";

    public static void save2file(Save save) {
        try {
            FileOutputStream fos = new FileOutputStream("sav/" + save.getUsername() + ".sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Saved file for " + save.getUsername());
    }

    public static void save2file() {
        new File("sav").mkdir();
        try {
            FileOutputStream fos = new FileOutputStream("sav/" + accountsFilename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Saved accounts");
    }

    public static void load4file() {
        users = new Vector<User>();
        saves = new Vector<Save>();
        currentUser = null;
        try {
            FileInputStream fin = new FileInputStream("sav/" + accountsFilename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Vector<?> temp = (Vector<?>) ois.readObject();
            for (Object obj : temp) {
                users.add((User) obj);
            }
            ois.close();
            fin.close();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Creating new accounts list");
            users.add(new User("admin", "admin"));
            save2file();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        System.out.println("Loaded accounts list");
        for (User user : users) {
            try {
                FileInputStream fin = new FileInputStream("sav/" + user.getUsername() + ".sav");
                ObjectInputStream ois = new ObjectInputStream(fin);
                Save save = (Save) ois.readObject();
                saves.add(save);
                ois.close();
                fin.close();
            } catch (IOException e) {
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
        }
        System.out.println("Loaded save files");
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
        if (username.isEmpty() || !javax.lang.model.SourceVersion.isIdentifier(username)) {
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
        Accounts.save2file();
        System.out.println("Registered new user");
        return 1;
    }

    public static User findUserByName(String username) {
        for (User user : Accounts.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Save findSaveByName(String username) {
        for (Save s : Accounts.saves) {
            if (s.getUsername().equals(username)) {
                return s;
            }
        }
        return null;
    }

    public static void replaceSaveByName(String username, MapMatrix savedMap, int savedLevelId, int savedSteps) {
        for (Save s : Accounts.saves) {
            if (s.getUsername().equals(username)) {
                s.setSavedMap(savedMap);
                s.setSavedLevelId(savedLevelId);
                s.setSavedSteps(savedSteps);
                save2file(s);
                return;
            }
        }
        Save s = new Save(username, savedMap, savedLevelId, savedSteps);
        Accounts.saves.add(s);
        save2file(s);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
