package com.yakubovskiy.design.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class User implements Cloneable {
    private final UUID id;
    private final String username;
    private String firstName;
    private String lastName;
    private int age;
    private UserStatus status;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public User(UUID id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public static User of(@NonNull String username, @NonNull String firstName, @NonNull String lastName) {
        UUID id = UUID.randomUUID();
        return new User(id, username, firstName, lastName);
    }
}
