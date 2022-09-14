package command.common;

import command.ActionCommand;
import models.Faculty;
import models.Language;
import org.apache.log4j.Logger;
import services.interfaces.FacultyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AjaxChooseFacultyCommand implements ActionCommand {
private final FacultyService facultyService;
public AjaxChooseFacultyCommand(FacultyService facultyService){
    this.facultyService = facultyService;
}
private static final Logger LOG = Logger.getLogger(AjaxChooseFacultyCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = null;
        LOG.debug("AjaxChooseFacultyCommand starts");
        String lang = req.getLocale().getLanguage();
        LOG.trace("Language from request ==> " + lang);
        List<Faculty> faculties = facultyService.getSortedList(lang, "name", "ASC");
        req.setAttribute("faculties", faculties);
        resp.setContentType("text/plain");
      resp.setCharacterEncoding("UTF-8");
      for(Faculty faculty : faculties){
          try {
              resp.getWriter().write("<option value=" + faculty.getId() + ">" + faculty.getNames().get(Language.valueOf(lang.toUpperCase())) + "</option>");
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
      LOG.debug("Go to ==> " + page);
    return page;
    }
}
