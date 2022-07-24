package dao;

import models.User;

import java.util.List;

public interface UserDao extends CrudDao<User>{
    public User getByLogin(String login);
}
