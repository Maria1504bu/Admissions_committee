package command;

import command.admin.*;
import command.candidate.AddApplicationCommand;
import command.candidate.CandidateProfileCommand;
import command.common.LogoutCommand;
import command.out_of_control.InitSignupCommand;
import command.out_of_control.LoginCommand;
import command.out_of_control.SignupCommand;
import dao.implementation.*;
import dao.interfaces.*;
import org.apache.log4j.Logger;
import services.implementation.*;
import services.interfaces.*;

import javax.sql.DataSource;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
    static TreeMap<String, ActionCommand> commands;
    public static void init(DataSource dataSource){
        ApplicationDao applicationDao = new ApplicationDaoImpl(dataSource);
        CandidateDao candidateDao = new CandidateDaoImpl(dataSource);
        FacultyDao facultyDao = new FacultyDaoImpl(dataSource);
        GradeDao gradeDao = new GradeDaoImpl(dataSource);
        SubjectDao subjectDao = new SubjectDaoImpl(dataSource);

        ApplicationService applicationService = new ApplicationServiceImpl(applicationDao);
        CandidateService candidateService = new CandidateServiceImpl(candidateDao);
        FacultyService facultyService = new FacultyServiceImpl(facultyDao);
        GradeService gradeService = new GradeServiceImpl(gradeDao);
        SubjectService examService = new SubjectServiceImpl(subjectDao);


        // out_of_control
        commands.put("login", new LoginCommand(candidateService));
        commands.put("signup", new SignupCommand(candidateService));
        commands.put("initSignup", new InitSignupCommand());
        // candidate
        commands.put("candidateProfile", new CandidateProfileCommand(candidateService));
        commands.put("addApplication", new AddApplicationCommand(applicationService));
        // admin
        commands.put("adminProfile", new AdminProfileCommand());
        commands.put("exams", new SubjectCommand(examService));
        commands.put("addExam", new AddSubjectCommand(examService));
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
