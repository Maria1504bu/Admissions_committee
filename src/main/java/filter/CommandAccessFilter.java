package filter;

import managers.ConfigurationManager;
import managers.MessageManager;
import models.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Allow access to resources by roles
 */
//@WebFilter(urlPatterns = "/controller?",
//        initParams = {@WebInitParam(name = "ADMIN", value = "subjects addSubjects faculties" +
//                " addFaculty editFaculty"),
//                @WebInitParam(name = "CANDIDATE", value = "signupFinal candidateProfile createApplication uploadCertificate"),
//                @WebInitParam(name = "COMMON", value = "logout"),
//                @WebInitParam(name = "OUT_OF_CONTROL", value = "login signupStart initSignup nullCommand")})
public class CommandAccessFilter implements Filter {
    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);
    // commands access
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();


    public void init(FilterConfig fConfig) throws ServletException {
        log.debug("CommandAccessFilter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("ADMIN")));
        accessMap.put(Role.CANDIDATE, asList(fConfig.getInitParameter("CANDIDATE")));
        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("COMMON"));
        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("OUT_OF_CONTROL"));
        log.trace("Out of control commands --> " + outOfControl);

        log.debug("CommandAccessFilter initialization finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("CommandAccessFilter starts");

        if (accessAllowed(request)) {
            log.debug("Access is allowed");
            chain.doFilter(request, response);
        } else {
            log.debug("Access don`t allowed");
            String errorMessage = MessageManager.getProperty("message.noPermission");
            request.setAttribute("errorNoPermission", errorMessage);
            log.trace("Set the request attribute: errorMessage ==> " + errorMessage);

            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error"))
                    .forward(request, response);
        }
        log.debug("CommandAccessFilter finished");
    }

    private boolean accessAllowed(ServletRequest request) {
        log.trace("access :" + accessMap + commons + outOfControl);
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        log.debug("Check access for command ==> " + commandName);
        if (commandName == null || commandName.isEmpty()) {
            log.trace("Command is null or empty");
//            17.08.22
//            commandName = "nullCommand";
//            log.trace("Initialize command by value 'nullCommand' to access user do safe action");
        }

        if (outOfControl.contains(commandName)) {
            log.trace("Command is out of control");
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            log.trace("Session was null. User was not login");
            return false;
        }

        Role userRole = (Role)session.getAttribute("role");
        log.trace("User have role ==> " + userRole);
        if (userRole == null){
            log.trace("Don`t sat role at session attributes");
            return false;
        }

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void destroy() {
        log.debug("CommandAccessFilter destruction");
    }

    /**
     * Extracts parameter values from string.
     * @param str parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

}