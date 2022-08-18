package dao.interfaces;

import models.Application;

import java.util.List;

public interface ApplicationDao extends CrudDao<Application> {
    public List<Application> getCandidateAppls(int candidateId) ;
}
