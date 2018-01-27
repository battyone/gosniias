package ru.dz.gosniias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dz.gosniias.entity.RelationEntity;

/**
 *
 * @author vassaeve
 */
public interface RelationRepository extends JpaRepository<RelationEntity, Long>, JpaSpecificationExecutor<RelationEntity> {

//        @Query("select b from RelationEntity b where b.a = :a")
//    RelationEntity findByName(@Param("a") String a);
}
