package command.out_of_control;

import command.ActionCommand;
import managers.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetLocaleCommand implements ActionCommand {
    private static final String PARAM_LANGUAGE = "language";
    private static final Logger LOG = Logger.getLogger(SetLocaleCommand.class);

    /***
     *
     *
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(PARAM_LANGUAGE);
        String url = ConfigurationManager.getProperty("redirect") + request.getHeader("referer").split("8080/committee")[1];
        LOG.debug("referer url ==>" + url);
        if (language != null) {
            request.getSession().setAttribute("language", language);
        }
//        if(url.equals("/controller?command=setLocale")){
//            LOG.debug("Previous request is also setLocale, so we need to forward user to index.jsp");
//            url = ConfigurationManager.getProperty("common.index");
//        }
        LOG.debug("Go to ==> " + url);
        return url;
    }
}
