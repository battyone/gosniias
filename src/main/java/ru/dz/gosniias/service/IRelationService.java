package ru.dz.gosniias.service;

import java.util.List;
import ru.dz.gosniias.dto.PageWrapper;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.entity.TaxonEntity;

/**
 *
 * @author vassaeve
 */
public interface IRelationService {

    public List<RelationEntity> findAll();

    public RelationEntity findByPK(long pk);

    public Long countAll();

    public void save(RelationEntity script);

    public void delete(RelationEntity entity);

    public void deleteAll(List<RelationEntity> relations);

    public List<RelationEntity> findByTaxon(TaxonEntity entity);

    public PageWrapper<RelationEntity> findAllByPage(long currentProject, int page, int pageSize);

}
