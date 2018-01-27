package ru.dz.gosniias.service;

import java.util.List;
import org.springframework.data.domain.Page;
import ru.dz.gosniias.entity.TaxonEntity;

/**
 *
 * @author vassaeve
 */
public interface ITaxonService {

    public List<TaxonEntity> findAll();

    public TaxonEntity findByPK(long pk);

    public Page<TaxonEntity> findAllByPage(long currentProject, int page, int pageSize);

    public Long countAll();

    public void save(TaxonEntity taxon);

    public void delete(TaxonEntity entity);

}
