package servlet;

import command.ActionCommand;
import command.factory.ActionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import managers.ConfigurationManager;
import managers.MessageManager;

import java.io.IOException;

@WebServlet("/controller")
public class Controller  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(req);
        page = command.execute(req);
        if(page != null) {
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        } else{
            page = ConfigurationManager.getProperty("path.page.index");
            req.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullPage"));
            resp.sendRedirect(req.getContextPath() + page);
        }
    }


}
