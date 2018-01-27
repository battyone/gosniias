package ru.dz.gosniias.service;

import java.util.List;
import org.springframework.data.domain.Page;
import ru.dz.gosniias.entity.ProjectEntity;

/**
 *
 * @author vassaeve
 */
public interface IProjectService {

    public List<ProjectEntity> findAll();

    public ProjectEntity findByPK(long pk);

    public Page<ProjectEntity> findAllByPage(int page, int pageSize);

    public Long countAll();

    public void save(ProjectEntity script);

    public void delete(ProjectEntity entity);

}
