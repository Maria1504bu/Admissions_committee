package services.implementation;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import dao.interfaces.CandidateDao;
import models.Candidate;
import models.Role;
import org.apache.log4j.Logger;
import services.EmptyFieldsException;
import services.ServiceException;
import services.interfaces.CandidateService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CandidateServiceImpl implements CandidateService {
    private static final Logger LOG = Logger.getLogger(CandidateServiceImpl.class);
    private CandidateDao candidateDao;

    public CandidateServiceImpl(CandidateDao candidateDao) {
        this.candidateDao = candidateDao;
    }


    private String encodePassword(String password) throws ServiceException {
        LOG.debug("Start encoding password");
        String encodedPass = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            encodedPass = String.format("%02X", bytes);
            LOG.debug("Encoded password ==> " + encodedPass);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Cannot encode password", e);
        }
        return encodedPass;
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

        if (candidate.getPassword() != encodedPass) throw new ServiceException("Password don`t match");
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

    private Candidate authorizeCandidate(int id) {
        LOG.debug("Find all information about candidate");
        Candidate candidate = candidateDao.getById(id);
        LOG.debug("Searched candidate ==> " + candidate);
        return candidate;
    }
}
