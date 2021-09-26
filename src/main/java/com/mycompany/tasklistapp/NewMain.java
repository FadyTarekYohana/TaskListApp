package com.mycompany.tasklistapp;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class NewMain {

    public static void main(String[] args) throws IOException {
        String username, password;
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        File f = new File("users.json");
        if (!f.exists()) {
            try {
                File file = new File("users.json");
                file.createNewFile();
            } catch (Exception e) {
            }
        }

        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("users.json"));
        Map<String, String> usersJson = gson.fromJson(reader, Map.class);
        reader.close();
        Map<String, String> loginInfo = new HashMap<>();
        try {
            loginInfo.putAll((Map<String, String>) usersJson);
        } catch (Exception e) {
        }

        OUTER:
        while (true) {
            while (true) {
                System.out.println("Welcome to the task list app!\n1- Sign in\n2- Sign up\n3- Exit");
                try {
                    x = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number from one of the options (1-3)!");
                    scanner.nextLine();
                    System.out.println();
                }
            }
            switch (x) {
                case 1:
                    System.out.println("Please enter your username or -1 to go back:");
                    username = scanner.nextLine();
                    if (username.equals("-1")) {
                        System.out.println();
                        continue;
                    }
                    System.out.println("Please enter your password:");
                    password = scanner.nextLine();
                    if (!loginInfo.containsKey(username) || !loginInfo.containsValue(password)) {
                        System.out.println("Username or password is incorrect!");
                        continue;
                    }
                    break;
                case 2:
                    System.out.println("Please enter your username or -1 to go back:");
                    username = scanner.nextLine();
                    if (username.equals("-1")) {
                        System.out.println();
                        continue;
                    } else if (loginInfo.containsKey(username)) {
                        System.out.println("Username is not available.");
                        continue;
                    }
                    System.out.println("Please enter your password:");
                    password = scanner.nextLine();
                    loginInfo.put(username, password);
                    break;
                case 3:
                    break OUTER;
                default:
                    System.out.println("Please enter a number from one of the options (1-3)!");
                    System.out.println();
                    continue;
            }

            if (!loginInfo.isEmpty()) {
                try {
                    Writer writer = Files.newBufferedWriter(Paths.get("users.json"));
                    gson.toJson(loginInfo, writer);
                    writer.close();
                } catch (Exception ex) {
                }
            } else {
                try {
                    Writer writer = Files.newBufferedWriter(Paths.get("users.json"));
                    writer.write("");
                    writer.close();
                } catch (Exception ex) {
                }
            }

            File f2 = new File("tasks/" + username + "tasks.json");
            if (!f2.exists()) {
                try {
                    File file2 = new File("tasks/" + username + "tasks.json");
                    file2.createNewFile();
                } catch (Exception e) {
                }
            }
            gson = new Gson();
            reader = Files.newBufferedReader(Paths.get("tasks/" + username + "tasks.json"));
            Map<String, Object> tasksJson = gson.fromJson(reader, Map.class);
            reader.close();
            Map<String, Object> items = new HashMap<>();
            try {
                items.putAll((Map<String, Object>) tasksJson);
            } catch (Exception e) {
            }

            ArrayList<String> uniqueID = new ArrayList<>();
            int i = 0, choice = 0;
            String id, title, desc;
            boolean done;

            INNER:
            while (true) {
                System.out.print("Please choose one of the following options:\n1- Add a new task\n2- Delete a task\n3- Edit a task\n4- List all tasks\n5- Sign out\n");
                while (true) {
                    try {
                        choice = scanner.nextInt();
                        System.out.println();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a number from one of the options (1-5)!");
                        System.out.println();
                        System.out.print("Please choose one of the following options:\n1- Add a new task\n2- Delete a task\n3- Edit a task\n4- List all tasks\n5- Sign out\n");
                        scanner.nextLine();
                    }
                }
                switch (choice) {
                    case 1:
                        System.out.println("Please enter the title of the new task or -1 to go back:");
                        scanner.nextLine();
                        title = scanner.nextLine();
                        if (title.equals("-1")) {
                            System.out.println();
                            continue;
                        }
                        System.out.println("Please enter the description of the new task:");
                        desc = scanner.nextLine();
                        while (true) {
                            try {
                                System.out.println("Is the task done? Please enter true or false:");
                                done = scanner.nextBoolean();
                                break;

                            } catch (InputMismatchException e) {
                                System.out.println("Please enter true or false!");
                                scanner.nextLine();
                            }
                        }
                        uniqueID.add(UUID.randomUUID().toString());
                        items.put(uniqueID.get(i), new Item(title, desc, done));
                        i++;
                        System.out.println();
                        break;
                    case 2:
                        System.out.println("Please enter the ID of the task you wish to delete or -1 to go back:");
                        scanner.nextLine();
                        id = scanner.nextLine();
                        if (id.equals("-1")) {
                            continue;
                        }
                        if (items.containsKey(id)) {
                            items.remove(id);
                            System.out.println("Task deleted.");
                            System.out.println();
                        } else {
                            System.out.println("There is no task with that ID.");
                            System.out.println();
                        }

                        break;
                    case 3:
                        System.out.println("Please enter the ID of the task you wish to edit or -1 to go back:");
                        scanner.nextLine();
                        id = scanner.nextLine();
                        if (id.equals("-1")) {
                            continue;
                        }
                        if (items.containsKey(id)) {
                            System.out.println("Please enter the title of the task:");
                            title = scanner.nextLine();
                            System.out.println("Please enter the description of the task:");
                            desc = scanner.nextLine();
                            System.out.println("Is the task done? Please enter true or false:");
                            done = scanner.nextBoolean();
                            items.replace(id, new Item(title, desc, done));
                            System.out.println();
                        } else {
                            System.out.println("There is no task with that ID.");
                            System.out.println();
                        }
                        break;
                    case 4:
                        if (items.isEmpty()) {
                            System.out.println("There are no tasks.");
                            System.out.println();
                        } else {
                            for (Object task : items.keySet()) {
                                String key = (String) task;
                                String value = items.get(task).toString();
                                System.out.println("ID: " + key + "\n" + value);
                                System.out.println();
                            }
                        }
                        break;
                    case 5:
                        break INNER;
                    default:
                        System.out.println("Please enter a number from one of the options (1-5)!");
                        System.out.println();
                        break;
                }
            }
            if (!items.isEmpty()) {
                try {
                    Writer writer = Files.newBufferedWriter(Paths.get("tasks/" + username + "tasks.json"));
                    gson.toJson(items, writer);
                    writer.close();
                } catch (Exception ex) {
                }
            } else {
                try {
                    Writer writer = Files.newBufferedWriter(Paths.get("tasks/" + username + "tasks.json"));
                    writer.write("");
                    writer.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
