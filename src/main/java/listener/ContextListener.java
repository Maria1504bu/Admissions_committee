package listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	private DataSource dataSource;
	// bootstrap of the application
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.debug("Start context initialization");
		ServletContext context = sce.getServletContext();
//		initDatasource(context);
//		LOG.debug("DataSource initialized");
		initLog4J(context);
		LOG.debug("Log4j initialized");
		initCommandContainer();
		LOG.debug("CommandContainer initialized");
	}

//	public DataSource getDataSource() {
//		return dataSource;
//	}
//
//	private void initDatasource(ServletContext context) throws IllegalStateException {
//		String dataSourceName = context.getInitParameter("dataSource");
//		Context envContext = null;
//		try {
//			envContext = (Context) new InitialContext().lookup("java:comp/env");
//			dataSource = (DataSource) envContext.lookup("jdbc/admissions_committee");
//		} catch (NamingException e) {
//			throw new IllegalStateException("Cannot initialize dataSource", e);
//		}
//	}


	private void initLog4J(ServletContext servletContext) {
		LOG.debug("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("log4j.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		LOG.debug("Log4J initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 */
	private void initCommandContainer() {
		LOG.debug("Command container initialization started");

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		LOG.debug("Command container initialization finished");
	}
}
