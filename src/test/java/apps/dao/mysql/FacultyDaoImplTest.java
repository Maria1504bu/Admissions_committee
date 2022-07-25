package apps.dao.mysql;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import dao.FacultyDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import models.Role;
import models.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FacultyDaoImplTest {

	static FacultyDaoImpl impl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

//		System.out.println(dao);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetConnection() throws Exception{
//		assertNotNull(impl);
//		try (Connection connection = impl.getConnection()) {
//		} catch (SQLException e) {
//			fail("Cannot gat connection");
//		}
	}

	@Test
	public void testUserDaoGetByLogin(){
//		UserDao userDao = new UserDaoImpl();
//		User user = userDao.getByLogin("qqq");
//		assertSame(new User(1, "qqq", "qqq", Role.CANDIDATE), user);
	}

}
