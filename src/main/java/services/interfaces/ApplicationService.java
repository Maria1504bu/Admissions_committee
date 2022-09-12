package services.interfaces;

import models.Application;

import java.util.List;

public interface ApplicationService {
    public List<Application> getCandidatesAppls(int candidateId, String language);

    List<Application> getFacultyAppls(int facultyId);
}
