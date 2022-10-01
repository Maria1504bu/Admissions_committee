package services.implementation;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.*;
import models.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import services.ServiceException;
import services.interfaces.ApplicationService;
import util.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationDao applicationDao;
    private final CandidateDao candidateDao;
    private final FacultyDao facultyDao;
    private final GradeDao gradeDao;
    private final SubjectDao subjectDao;
    private static final Logger LOG = Logger.getLogger(ApplicationServiceImpl.class);

    public ApplicationServiceImpl(ApplicationDao applicationDao, CandidateDao candidateDao, FacultyDao facultyDao, GradeDao gradeDao, SubjectDao subjectDao) {
        this.applicationDao = applicationDao;
        this.candidateDao = candidateDao;
        this.facultyDao = facultyDao;
        this.gradeDao = gradeDao;
        this.subjectDao = subjectDao;
    }

    @Override
    public void saveWithGrades(Application application) throws AlreadyExistException, WrongExecutedQueryException {
        Connection connection = applicationDao.save(application);
        //todo: validate grate less maxGrade
        for (Grade grade : application.getGradesList()) {
            gradeDao.save(connection, grade);
            gradeDao.createApplGradesSet(connection);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Cannot commit changes at db", e);
        }
    }

    @Override
    public List<Application> getCandidatesAppls(String candidateId) {
        LOG.trace("Start get candidates applications by candidateId ==> " + candidateId);
        int validatedId = Validator.validateId(candidateId);
        List<Application> applications = applicationDao.getCandidateAppls(validatedId);
        LOG.trace("Applications from db ==> " + applications);
        // from applicationDao we get only id faculty
        for (Application application : applications) {
            Faculty faculty = facultyDao.getById(application.getFaculty().getId());
            application.setFaculty(faculty);
            LOG.trace("Set faculty " + faculty + " for application" + application);

            List<Grade> grades = gradeDao.getApplGrades(application.getId());
            for (Grade grade : grades) {
                grade.setSubject(subjectDao.getById(grade.getSubject().getId()));
            }
            application.setGradesList(grades);
            LOG.trace("Set grades " + grades + " for application" + application);
        }
        return applications;
    }

    @Override
    public List<Application> getFacultyAppls(String facultyId) {
        LOG.trace("Start get faculty`s applications by candidateId ==> " + facultyId);
        int validateId = Validator.validateId(facultyId);
        List<Application> applications = applicationDao.getFacultyAppls(validateId);
        LOG.trace("Applications from db ==> " + applications);
        // from applicationDao we get only id faculty
        for (Application application : applications) {
            Candidate candidate = candidateDao.getById(application.getCandidate().getId());
            application.setCandidate(candidate);
            LOG.trace("Set candidate " + candidate + " to application" + application);

            List<Grade> grades = gradeDao.getApplGrades(application.getId());
            for (Grade grade : grades) {
                grade.setSubject(subjectDao.getById(grade.getSubject().getId()));
            }
            application.setGradesList(grades);
            LOG.trace("Set grades " + grades + " for application" + application);
        }
        return applications;
    }

    @Override
    public void provideDocuments(Application application) throws AlreadyExistException, WrongExecutedQueryException {
        application.setApplicationStatus(ApplicationStatus.DOCUMENTS_PROVIDED);
        applicationDao.provideDocuments(application);
    }

    @Override
    public void createRegister(Faculty faculty) {
        faculty.setSubjectsWithCoefs(subjectDao.findAllByFacultyId(faculty.getId()));
        LOG.debug("Set subjects for faculty ==> " + faculty);
        String engName = faculty.getNames().get(Language.EN);
        String ukName = faculty.getNames().get(Language.UK);
        String file = "D:/My Final Project/Admissions_committee/src/main/webapp/register/" + engName + "Register.pdf";
        FileOutputStream outputStream = null;
        try {
            outputStream = FileUtils.openOutputStream(new File(file));

        } catch (IOException e) {
            LOG.error(e);
            throw new ServiceException("Cannot create register because file is not found");
        }
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        PdfFont arialFont = null;

        try {
            arialFont = PdfFontFactory.createFont("D:/My Final Project/Admissions_committee/src/main/resources/register/fonts/Arial.ttf", PdfEncodings.IDENTITY_H, true);
            ImageData data = ImageDataFactory.create("D:/My Final Project/Admissions_committee/src/main/resources/register/emblems/emblem Of Ukraine.png");
            Image image = new Image(data).scaleAbsolute(70, 100).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        document.setFont(arialFont);

        Paragraph executive = new Paragraph("Міністерство освіти і науки України").setFontSize(28).setBold().setTextAlignment(TextAlignment.CENTER);
        Paragraph committee = new Paragraph("Приймальна комісія").setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        Paragraph register = new Paragraph("Рейтингова відомість").setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        Paragraph info = new Paragraph("вступників, які подали документи для здобуття \n освітнього ступення бакалавр " +
                "\n за спеціальністю " + ukName + "\n Кількість місць: " + faculty.getTotalPlaces() +
                 "\n з них бюджетних: " + faculty.getBudgetPlaces()).setTextAlignment(TextAlignment.CENTER);

        document.add(executive);
        document.add(committee);
        document.add(register);
        document.add(info);

        float[] columnWidth = {20f, 100f, 50f, 50f, 30f};
        for(int i = 0; i<faculty.getSubjectsWithCoefs().size(); i++){
            columnWidth = Arrays.copyOf(columnWidth, columnWidth.length+1);
            columnWidth[columnWidth.length-1] = 30f;
        }

        Table table = new Table(columnWidth);
        table.addCell(new Cell().add("N").setBackgroundColor(Color.LIGHT_GRAY).setBold());
        table.addCell(new Cell().add("Прізвище").setBackgroundColor(Color.LIGHT_GRAY).setBold());
        table.addCell(new Cell().add("Ім'я").setBackgroundColor(Color.LIGHT_GRAY).setBold());
        table.addCell(new Cell().add("По-батькові").setBackgroundColor(Color.LIGHT_GRAY).setBold());
        table.addCell(new Cell().add("Рейтинговий бал").setBackgroundColor(Color.LIGHT_GRAY).setBold());
        for (Subject subject : faculty.getSubjectsWithCoefs().keySet()) {
            String ukrSubName = subject.getNames().get(Language.UK);
            table.addCell(new Cell().add(ukrSubName).setBackgroundColor(Color.LIGHT_GRAY));
        }
        List<Application> applications = getFacultyAppls(String.valueOf(faculty.getId()));
        Color color = new DeviceRgb(210, 239, 210);
        for (int i = 1; i < applications.size(); i++) {
            table.addCell(new Cell().add(String.valueOf(i)).setBackgroundColor(color));
            Candidate candidate = applications.get(i - 1).getCandidate();
            table.addCell(new Cell().add(candidate.getSecondName()).setBackgroundColor(color));
            table.addCell(new Cell().add(candidate.getFirstName()).setBackgroundColor(color));
            table.addCell(new Cell().add(candidate.getFatherName()).setBackgroundColor(color));
            table.addCell(new Cell().add("Rating").setBackgroundColor(color));
            for (Grade grade : applications.get(i - 1).getGradesList()) {
                String mark = String.valueOf(grade.getGrade());
                table.addCell(new Cell().add(mark).setBackgroundColor(color));
            }
            if (i == faculty.getBudgetPlaces()) color = new DeviceRgb(255, 255, 200);
        }
        document.add(table);
        document.close();
    }
}
