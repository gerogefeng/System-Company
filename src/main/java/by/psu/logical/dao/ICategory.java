package by.psu.logical.dao;

import by.psu.logical.model.employee.Category;

public interface ICategory {
    public Category findByName(String name);
}
