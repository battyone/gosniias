package ru.dz.gosniias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.repository.ProjectRepository;
import ru.dz.gosniias.service.IProjectService;

/**
 *
 * @author vassaeve
 */
@Service
@Transactional
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    ProjectRepository repository;

    @Override
    public List<ProjectEntity> findAll() {
        return repository.findAll();
        //return functionsDao.findAll();
    }

    @Override
    public ProjectEntity findByPK(long pk) {
        return repository.findOne(pk);
        //return functionsDao.findByPK(pk);
    }

    @Override
    public Page<ProjectEntity> findAllByPage(int page, int pageSize) {
        PageRequest request = new PageRequest(page - 1, pageSize);

        return repository.findAll(request); //findAllByPage(page, pageSize);
    }

    @Override
    public Long countAll() {
        return repository.count();
        //return functionsDao.countAll();
    }

    @Override
    public void save(ProjectEntity entity) {
        repository.save(entity);
        //functionsDao.createOrUpdate(entity);
    }

    @Override
    public void delete(ProjectEntity entity) {
        repository.delete(entity);
    }

}
