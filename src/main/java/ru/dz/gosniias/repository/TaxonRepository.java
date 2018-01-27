package ru.dz.gosniias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.TaxonEntity;

/**
 *
 * @author vassaeve
 */
public interface TaxonRepository extends JpaRepository<TaxonEntity, Long>, JpaSpecificationExecutor<TaxonEntity> {

    @Query("select b from TaxonEntity b where b.description = :description and b.project = :project" )
    TaxonEntity findByName(@Param("description") String description, @Param("project") ProjectEntity project);

//    @Query("select b from TaxonEntity b where b.project = :project")
//    TaxonEntity findByProject(@Param("project") ProjectEntity project);

}
