package servlet;

import command.ActionCommand;
import command.CommandContainer;
import managers.ConfigurationManager;
import managers.MessageManager;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger  LOG = Logger.getLogger(Controller.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Get request ==> " + req.toString());
//        LOG.debug("Request url ==> " + req.getRequestURL());
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("Post request ==> " + req.toString());
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        ActionCommand command;
        if (ServletFileUpload.isMultipartContent(req)) {
            command = CommandContainer.get("uploadCertificate");
        } else {
            command = CommandContainer.get(req.getParameter("command"));
        }
        LOG.trace("Command ==> " + command);
        page = command.execute(req);
        LOG.trace("Next page ==> " + page);
        if (page != null) {
            renderPage(req, resp, page);
        } else {
            LOG.debug("Page is null. Forward to index page");
            page = ConfigurationManager.getProperty("common.index");
            req.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullPage"));
        }
    }

    private void renderPage(HttpServletRequest req, HttpServletResponse resp, String page) throws IOException, ServletException {
        LOG.debug("Start rendering page");
        String redirect = ConfigurationManager.getProperty("redirect");
        if(page.startsWith(redirect)){
            String domain = ConfigurationManager.getProperty("domain");
            String redirectPage = page.replace(redirect, domain);
            LOG.debug("Page to redirect ==> " + redirectPage);
            resp.sendRedirect(redirectPage);
        } else {
            getServletContext().getRequestDispatcher(page).forward(req, resp);
            LOG.debug("Forward to page ==> " + page);
        }
    }


}
