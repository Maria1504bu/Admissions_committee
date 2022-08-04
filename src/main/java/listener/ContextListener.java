package listener;

import command.CommandContainer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	// bootstrap of the application
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.debug("Start context initialization");
		ServletContext context = sce.getServletContext();
		initDatasource(context);
		LOG.debug("DataSource initialized");
		initCommandContainer(context);
		LOG.debug("CommandContainer initialized");
		initLog4J(context);
		LOG.debug("Log4j initialized");
	}

	private void initDatasource(ServletContext context) throws IllegalStateException {
		Context envContext = null;
		try {
			envContext = (Context) new InitialContext().lookup("java:comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/admissions_committee");
			context.setAttribute("dataSource", dataSource);
			LOG.trace("context.setAttribute 'dataSource' ==> " + dataSource.getClass().getName());
		} catch (NamingException e) {
			throw new IllegalStateException("Cannot initialize dataSource", e);
		}
	}


	/**
	 * Initializes CommandContainer.
	 */
	private void initCommandContainer(ServletContext context) {
		 DataSource  dataSource = (DataSource) context.getAttribute("dataSource");
		LOG.debug("Command container initialization started");
 		CommandContainer.init(dataSource);
		try {
			Class.forName("command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		LOG.debug("Command container initialization finished");
	}

	private void initLog4J(ServletContext servletContext) {
		LOG.debug("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("log4j.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		LOG.debug("Log4J initialization finished");
	}
}
