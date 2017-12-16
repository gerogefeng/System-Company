package by.psu.logical.dao;

public interface ICRUD<T> extends IDAO<T>{
    void create(T object);
    T read(int id);
    void update(T object);
    void delete(T object);
}
