package ru.dz.gosniias.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.TaxonEntity;
import ru.dz.gosniias.repository.ProjectRepository;
import ru.dz.gosniias.repository.TaxonRepository;
import ru.dz.gosniias.repository.TaxonSpecs;
import ru.dz.gosniias.service.ITaxonService;

/**
 *
 * @author vassaeve
 */
@Service
@Transactional
public class TaxonServiceImpl implements ITaxonService {

    @Autowired
    TaxonRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<TaxonEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public TaxonEntity findByPK(long pk) {
        return repository.findOne(pk);
    }

    @Override
    public Page<TaxonEntity> findAllByPage(long currentProject, int page, int pageSize) {
        PageRequest request = new PageRequest(page - 1, pageSize);
        ProjectEntity project = projectRepository.findOne(currentProject);
        Page<TaxonEntity> result = repository.findAll(Specifications.where(TaxonSpecs.isProjectEq(project)), request);

        return result;
    }

    @Override
    public Long countAll() {
        return repository.count();
    }

    @Override
    public void save(TaxonEntity entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(TaxonEntity entity) {
        repository.delete(entity);
    }

}
