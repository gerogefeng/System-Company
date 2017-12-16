package by.psu.logical.dao;

import by.psu.logical.model.User;

public interface IUser extends ICRUD<User> {
    boolean exists(String login, String password);
}
