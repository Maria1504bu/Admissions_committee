package services.implementation;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import dao.interfaces.CandidateDao;
import models.Candidate;
import models.City;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.ServiceException;
import services.interfaces.CandidateService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

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
    public Candidate authenticate(String email, String password) throws EmptyFieldsException, ServiceException {
        LOG.debug("Start authenticate user with email ==> " + email);
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new EmptyFieldsException("email or password is empty");
        String encodedPass = encodePassword(password);
        Candidate candidate = candidateDao.getByLogin(email);
        LOG.trace("User from db ==> " + candidate);

        if (!candidate.getPassword().equals(encodedPass)){
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
    public Candidate signInit(String email, String password) throws EmptyFieldsException {
        LOG.debug("Start first step of registration user with email ==> " + email);
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new EmptyFieldsException("email or password is empty");
        String encodedPass = encodePassword(password);
        try {
            candidateDao.saveLogin(email, encodedPass);
        } catch (WrongExecutedQueryException | AlreadyExistException e) {
            LOG.debug("Candidate login data with email " + email + " not saved", e);
            throw new ServiceException("candidate with email already exist");
        }
        Candidate candidate = candidateDao.getByLogin(email);
        LOG.trace("User from db ==> " + candidate);
        LOG.debug("First step of registration went well");
        return candidate;
    }

    @Override
    public Candidate signFinal(Candidate candidate, String firstName, String fatherName, String secondName, City city, String schoolName) throws EmptyFieldsException {
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
    public Candidate saveCertificate(Candidate candidate, String certificateName) {
        final String UPLOAD_DIR = "/certificateUploads/";
        try {
            String certificatePath = UPLOAD_DIR + certificateName;
            candidateDao.saveCertificate(candidate.getId(), certificatePath);
        } catch (WrongExecutedQueryException e) {
            throw new ServiceException("Cannot save certificate into db", e);
        }
        Candidate.modify(candidate).certificate(certificateName).build();
        return candidate;
    }

    private Candidate authorizeCandidate(int id) {
        LOG.debug("Find all information about candidate");
        Candidate candidate = candidateDao.getById(id);
        LOG.debug("Searched candidate ==> " + candidate);
        return candidate;
    }
}
