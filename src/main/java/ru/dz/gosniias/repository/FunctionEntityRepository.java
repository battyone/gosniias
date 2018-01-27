package ru.dz.gosniias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dz.gosniias.entity.FunctionEntity;

/**
 *
 * @author vassaeve
 */
public interface FunctionEntityRepository extends JpaRepository<FunctionEntity, Long> {

    @Query("select b from FunctionEntity b where b.description = :description")
    FunctionEntity findByName(@Param("description") String description);

}
