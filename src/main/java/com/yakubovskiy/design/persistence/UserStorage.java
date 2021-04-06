package com.yakubovskiy.design.persistence;

import com.yakubovskiy.design.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserStorage {
    public Optional<User> findByUsername(String userName) {
        return Optional.empty();
    }

    public User persist(User user) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Optional<User> load(UUID userId) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public List<User> loadAll() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
