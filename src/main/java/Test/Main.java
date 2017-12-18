package Test;

import by.psu.logical.model.*;
import by.psu.logical.service.*;

public class Main {
    public static void main(String[] args) {/*
        EmployeeService employeeService = new EmployeeService();
        Employee e = employeeService.read(1);
        System.out.println(e.getPassports());*/
        /*UserService userService = new UserService();
        System.out.println(userService.read(1).getLogin());*/

        /*PassportService passportService = new PassportService();
        Passport p = passportService.read(1);
        System.out.println(p.getEmployee().getUser().getRole().getTitle());*/

        /*RoleService roleService = new RoleService();
        System.out.println(roleService.readALL().get(0).getTitle());*/

        /*PrivateCardService pcs = new PrivateCardService();
        System.out.println(pcs.read(1).getEmployee().getUser().getRole().getTitle());*/

        /*CategoryService categoryService = new CategoryService();
        Category category = categoryService.read(1);
        System.out.println(category.getDriverLicences().get(0).getEmployee().getUser().getRole().getTitle());*/

        PostService postService = new PostService();
        //postService.create(new Post("Kingman"));

        //postService.delete(postService.read(1));

        /*DriverLicenceService dls = new DriverLicenceService();
        dls.readALL().get(0).getCategories().forEach(category -> {
            System.out.println(category.getCategory());
        });*/

    }
}
