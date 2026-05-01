package com.sad.adapters.store;

import com.sad.domain.UserAccount;
import com.sad.ports.IUserDataStore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataStore implements IUserDataStore {
    private final Map<String, UserAccount> users = new HashMap<>();
    private final Path filePath;

    public UserDataStore() {
        this(Path.of("data", "accounts.json"));
    }

    public UserDataStore(Path filePath) {
        this.filePath = filePath;
        loadOrCreateDefaults();
    }

    @Override
    public UserAccount findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return users.get(username.trim().toLowerCase());
    }

    private void loadOrCreateDefaults() {
        try {
            Files.createDirectories(filePath.getParent());
            if (Files.notExists(filePath)) {
                users.put("alice", new UserAccount("alice", "pass123", "Alice"));
                users.put("bob", new UserAccount("bob", "pass123", "Bob"));
                users.put("charlie", new UserAccount("charlie", "pass123", "Charlie"));
                save();
                return;
            }
            load();
            if (users.isEmpty()) {
                users.put("alice", new UserAccount("alice", "pass123", "Alice"));
                users.put("bob", new UserAccount("bob", "pass123", "Bob"));
                users.put("charlie", new UserAccount("charlie", "pass123", "Charlie"));
                save();
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Could not initialise account data store.", ex);
        }
    }

    private void load() throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            String cleaned = line.trim();
            if (cleaned.endsWith(",")) {
                cleaned = cleaned.substring(0, cleaned.length() - 1);
            }
            if (!cleaned.startsWith("{") || !cleaned.endsWith("}")) {
                continue;
            }
            String username = extract(cleaned, "username");
            String password = extract(cleaned, "password");
            String displayName = extract(cleaned, "displayName");
            if (username != null && password != null && displayName != null) {
                users.put(username.toLowerCase(), new UserAccount(username, password, displayName));
            }
        }
    }

    private void save() throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        int index = 0;
        for (UserAccount user : users.values()) {
            json.append("  {\"username\":\"").append(escape(user.getUsername())).append("\", ")
                    .append("\"password\":\"").append(escape(user.getPassword())).append("\", ")
                    .append("\"displayName\":\"").append(escape(user.getDisplayName())).append("\"}");
            if (++index < users.size()) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]\n");
        Files.writeString(filePath, json.toString(), StandardCharsets.UTF_8);
    }

    private String extract(String object, String key) {
        String search = "\"" + key + "\":\"";
        int start = object.indexOf(search);
        if (start < 0) {
            search = "\"" + key + "\": \"";
            start = object.indexOf(search);
        }
        if (start < 0) {
            return null;
        }
        start += search.length();
        int end = object.indexOf("\"", start);
        if (end < 0) {
            return null;
        }
        return object.substring(start, end).replace("\\\"", "\"");
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
