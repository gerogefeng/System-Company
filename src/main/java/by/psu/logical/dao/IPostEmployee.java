package by.psu.logical.dao;

import by.psu.logical.model.employee.PostsEmployee;

public interface IPostEmployee extends IDAO<PostsEmployee> {
    void getAllEmployeesInterval();
}
