package by.psu.logical;

import by.psu.logical.service.UserService;
import by.psu.logical.unit.SessionHibernate;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        System.out.println(userService.exists("administrator", "12345"));
        SessionHibernate.getInstance().closeFactory();
    }
}
