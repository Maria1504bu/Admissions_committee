package dao;

import models.Faculty;
import models.Subject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FacultyDaoImpl  implements FacultyDao{
    private final DataSource ds;

    public FacultyDaoImpl(DataSource dataSource) {
        ds = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    @Override
    public Faculty getById(int id) {
        return null;
    }

    @Override
    public void save(Faculty model) {

    }

    @Override
    public void update(Faculty faculty) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Faculty> findAll() {
        return null;
    }

    @Override
    public List<Subject> findSubjects(int id) {
        return null;
    }
}
