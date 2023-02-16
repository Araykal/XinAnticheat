/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.manager;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class UserManager {
    private final List<User> users = new ArrayList<User>();
    private final Main manager;

    public UserManager(Main manager) {
        this.manager = manager;
    }

    public User getUser(UUID uuid) {
        return this.users.parallelStream().filter(user -> user.getUUID().equals(uuid)).findFirst().orElseGet(() -> new User(uuid));
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public int safeGetLevel(UUID uuid) {
        User user = this.getUser(uuid);
        if (user == null) {
            return 0;
        }
        return user.getLevel();
    }
}

