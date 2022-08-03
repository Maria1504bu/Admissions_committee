package command;

import command.admin.*;
import command.candidate.AddCandidateExamCommand;
import command.candidate.CandidateProfileCommand;
import command.common.LogoutCommand;
import command.out_of_control.InitSignupCommand;
import command.out_of_control.LoginCommand;
import command.out_of_control.SignupCommand;
import dao.*;
import org.apache.log4j.Logger;
import services.*;

import java.util.TreeMap;

public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
    static TreeMap<String, ActionCommand> commands;

    static {
        UserDao userDao = new UserDaoImpl();
        FacultyDao facultyDao = new FacultyDaoImpl();
        ExamDao examDao = new ExamDaoImpl();

        UserService userService = new UserServiceImpl();
        FacultyService facultyService = new FacultyServiceImpl(facultyDao);
        ExamService examService = new ExamServiceImpl();


// out_of_control
        commands.put("login", new LoginCommand(userService));
        commands.put("signup", new SignupCommand(userService));
        commands.put("initSignup", new InitSignupCommand());
        // candidate
        commands.put("candidateProfile", new CandidateProfileCommand());
        commands.put("addCandidateExam", new AddCandidateExamCommand());
        // admin
        commands.put("adminProfile", new AdminProfileCommand());
        commands.put("exams", new ExamsCommand(examService));
        commands.put("addExam", new AddExamCommand(examService));
        commands.put("editExam", new EditExamCommand(examService));
        commands.put("faculties", new FacultiesCommand(facultyService));
        commands.put("addFaculty", new AddFacultyCommand(facultyService));
        commands.put("editFaculties", new EditFacultyCommand(facultyService));
        // common
        commands.put("logout", new LogoutCommand());
    }

    /**
     * Returns command object with the given name.
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static ActionCommand get(String commandName) {
        if(commandName.contains("?")){
            String commandToProceed = commandName.substring(0, commandName.indexOf("?"));
            LOG.debug("Command updated name --> " + commandToProceed);
            return commands.get(commandToProceed);
        }
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
