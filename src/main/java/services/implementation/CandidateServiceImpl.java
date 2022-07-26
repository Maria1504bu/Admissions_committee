package services.implementation;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import dao.interfaces.CandidateDao;
import models.Candidate;
import models.City;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.ServiceException;
import services.interfaces.CandidateService;
import util.Validator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

public class CandidateServiceImpl implements CandidateService {
    private static final Logger LOG = Logger.getLogger(CandidateServiceImpl.class);
    private CandidateDao candidateDao;

    public CandidateServiceImpl(CandidateDao candidateDao) {
        this.candidateDao = candidateDao;
    }


    private String encodePassword(String password) throws ServiceException {
        LOG.debug("Start encoding password");
        StringBuilder encodedPass = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            for (byte b : bytes) {
                encodedPass.append(String.format("%02X", b));
            }
            LOG.debug("Encoded password ==> " + encodedPass);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Cannot encode password", e);
        }
        return encodedPass.toString();
    }

    //TODO: check if throws exception
    @Override
    public Candidate authenticate(String email, String password) throws EmptyFieldsException, ServiceException, DaoException {
        LOG.debug("Start authenticate user with email ==> " + email);
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new EmptyFieldsException("email or password is empty");
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        String encodedPass = encodePassword(password);
        Candidate candidate = candidateDao.getByLogin(email);
        LOG.trace("User from db ==> " + candidate);

        if (!candidate.getPassword().equals(encodedPass)) {
            LOG.trace("Password don't match");
            throw new ServiceException("Password don`t match");
        }
        if (candidate.getRole() == Role.CANDIDATE) {
            LOG.trace("It`s candidate");
            candidate = authorizeCandidate(candidate.getId());
            LOG.trace("All candidate`s fields is initialized");
        }
        LOG.debug("Authentication went well");
        return candidate;
    }

    @Override
    public Candidate signInit(String email, String password) throws EmptyFieldsException, AlreadyExistException, WrongExecutedQueryException {
        LOG.debug("Start first step of registration user with email ==> " + email);
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new EmptyFieldsException("email or password is empty");
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        String encodedPass = encodePassword(password);
            candidateDao.saveLogin(email, encodedPass);
        Candidate candidate = candidateDao.getByLogin(email);
        LOG.trace("User from db ==> " + candidate);
        LOG.debug("First step of registration went well");
        return candidate;
    }

    @Override
    public Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException, ServiceException, DaoException {
        LOG.debug("Start final step of registration user");
        if (firstName == null || fatherName == null || secondName == null || city == null || schoolName == null ||
                firstName.isEmpty() || fatherName.isEmpty() || secondName.isEmpty() || schoolName.isEmpty()) {
            throw new EmptyFieldsException("Some field is empty");
        }
        Candidate.modify(candidate)
                .firstName(firstName)
                .fatherName(fatherName)
                .secondName(secondName)
                .city(city)
                .schoolName(schoolName)
                .applicationDate(LocalDate.now()).build();
        LOG.trace("Add data of candidate ==> " + candidate);
        try {
            candidateDao.save(candidate);
        } catch (AlreadyExistException | WrongExecutedQueryException e) {
            throw new ServiceException("candidate with email already exist", e);
        }
        LOG.debug("Finish final step of registration user");
        return candidate;
    }

    @Override
    public Candidate saveCertificate(Candidate candidate, String certificateName) throws ServiceException {
        final String UPLOAD_DIR = "/certificateUploads/";
        try {
            String certificatePath = UPLOAD_DIR + certificateName;
            candidateDao.saveCertificate(candidate.getId(), certificateName);
        } catch (WrongExecutedQueryException e) {
            throw new ServiceException("Cannot save certificate into db", e);
        }
        Candidate.modify(candidate).certificate(certificateName).build();
        return candidate;
    }

    private Candidate authorizeCandidate(int id) throws DaoException {
        LOG.debug("Find all information about candidate");
        Candidate candidate = candidateDao.getById(id);
        LOG.debug("Searched candidate ==> " + candidate);
        return candidate;
    }

    @Override
    public void blockCandidate(String candidateId) throws ServiceException {
        try {
            int validateId = Validator.validateId(candidateId);
            candidateDao.blockCandidate(validateId);
        } catch (WrongExecutedQueryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int getCandidatesListSize(String facultyId) {
        int id = facultyId == null || facultyId.isEmpty() ? 1 : Integer.parseInt(facultyId);
        int candidatesForFaculty;
        try {
            candidatesForFaculty = candidateDao.getCandidateListSize(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return candidatesForFaculty;

    }

    @Override
    public List<Candidate> getByFaculty(String selectedFacultyId, String limitItems, String offset) {
        int idFaculty = selectedFacultyId == null || selectedFacultyId.isEmpty() ? 1 : Integer.parseInt(selectedFacultyId);
        int limit = limitItems == null || limitItems.isEmpty() ? 2 : Integer.parseInt(limitItems);
        int offSet = offset == null || offset.isEmpty() ? 0 : Integer.parseInt(offset);
        return candidateDao.getCandidatesForFaculty(idFaculty, limit, offSet);
    }

    @Override
    public Candidate getById(String id) throws DaoException {
        int validateId = Validator.validateId(id);
        return candidateDao.getById(validateId);
    }

    @Override
    public List<Candidate> getAll() throws DaoException {
        return candidateDao.findAll();
    }

    @Override
    public void update(Candidate candidate) throws AlreadyExistException, WrongExecutedQueryException, DaoException {
        candidateDao.update(candidate);
    }
}
