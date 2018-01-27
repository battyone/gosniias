package ru.dz.gosniias.service.impl;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dz.gosniias.dto.PageWrapper;
import ru.dz.gosniias.dto.RelationDto;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.entity.TaxonEntity;
import ru.dz.gosniias.repository.ProjectRepository;
import ru.dz.gosniias.repository.RelationRepository;
import ru.dz.gosniias.repository.RelationSpecs;
import ru.dz.gosniias.service.IRelationService;

/**
 *
 * @author vassaeve
 */
@Service
@Transactional
public class RelationServiceImpl implements IRelationService {

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public List<RelationEntity> findAll() {
        return relationRepository.findAll();
    }

    @Override
    public RelationEntity findByPK(long pk) {
        return relationRepository.findOne(pk);
    }

    @Override
    public PageWrapper<RelationEntity> findAllByPage(long currentProject, int page, int pageSize) {

        String sqlCount = "SELECT \n"
                + "    count(*) \n"
                + "FROM relations rel \n"
                + "join taxon t1 on t1.id=rel.taxon_parent_id \n"
                + "join taxon t2 on t2.id = rel.taxon_child_id\n"
                + "where t1.project_id=:currentProject";

        Query qCount = entityManager.createNativeQuery(sqlCount).setParameter("currentProject", currentProject); 
        int count = ((BigInteger)qCount.getSingleResult()).intValue();

        String sql = "SELECT \n"
                + "    rel.id as id,\n"
                + "    rel.relation_name as relationName, \n"
                + "    t1.id as parentId, \n"
                + "    t1.description as parentName, \n"
                + "    t2.id as childId, \n"
                + "    t2.description as childName \n"
                + "FROM relations rel \n"
                + "join taxon t1 on t1.id=rel.taxon_parent_id \n"
                + "join taxon t2 on t2.id = rel.taxon_child_id\n"
                + "where t1.project_id=:currentProject";

        Query q = entityManager.createNativeQuery(sql)
                .setParameter("currentProject", currentProject)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize);

        List list = q.getResultList();
        PageWrapper result = new PageWrapper(count, list);

        return result;

    }

    @Override
    public Long countAll() {
        return relationRepository.count();
    }

    @Override
    public void save(RelationEntity entity) {
        relationRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(RelationEntity entity) {
        relationRepository.delete(entity);
    }

    @Override
    public void deleteAll(List<RelationEntity> relations) {
        relationRepository.deleteInBatch(relations);
    }

    @Override
    public List<RelationEntity> findByTaxon(TaxonEntity entity) {
        return relationRepository.findAll(RelationSpecs.isTaxonExist(entity));
    }

}
