package by.psu.logical.service.employee_services;

import by.psu.logical.dao.ICategory;
import by.psu.logical.model.employee.Category;
import by.psu.logical.service.AbstractService;
import by.psu.logical.unit.SessionHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryService extends AbstractService<Category> implements ICategory{

    public CategoryService() {
        super(Category.class);
    }

    @Override
    public Category findByName(String name) {
        Session session = SessionHibernate.getInstance().retriver();
        Query query = session.createQuery("from Category where title = :name");

        List list = query.setParameter("name", name).list();
        System.out.println(list.size());
        SessionHibernate.getInstance().putback(session);
        return (Category) list.get(0);
    }
}
