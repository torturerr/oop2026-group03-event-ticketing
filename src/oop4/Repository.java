package oop4;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    int save(T entity) throws SQLException;
    T findById(int id) throws SQLException;
    List<T> findAll();
}
