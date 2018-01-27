package ru.dz.gosniias.service;

import java.util.List;
import org.springframework.data.domain.Page;
import ru.dz.gosniias.dto.RuleForExecute;
import ru.dz.gosniias.dto.RuleResponse;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.RequirementEntity;

/**
 *
 * @author vassaeve
 */
public interface IRequirementService {

    public List<RequirementEntity> findAll();

    public RequirementEntity findByPK(long pk);

    public Page<RequirementEntity> findAllByPage(int page, int pageSize, String typeOf, ProjectEntity projectId);

    public Long countAll();

    public void save(RequirementEntity script);

    public void delete(RequirementEntity entity);

    public RuleResponse execute(RuleForExecute requirement);

    public void executeSetRequirement(ProjectEntity project, String functionName, String[] paramsArray);

    public boolean checkRequirement(ProjectEntity project, String functionName, String[] paramsArray);

}
