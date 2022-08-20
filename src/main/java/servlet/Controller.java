package servlet;

import command.ActionCommand;
import command.CommandContainer;
import managers.ConfigurationManager;
import managers.MessageManager;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        ActionCommand command;

        //
        if (ServletFileUpload.isMultipartContent(req)) {
            command = CommandContainer.get("uploadCertificate");
        } else {
            command = CommandContainer.get(req.getParameter("command"));
        }
        page = command.execute(req);
        if (page != null) {
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        } else {
            page = ConfigurationManager.getProperty("path.common.index");
            req.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullPage"));
            resp.sendRedirect(req.getContextPath() + page);
        }
    }


}
