package ru.dz.gosniias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.dz.gosniias.entity.RequirementEntity;

/**
 *
 * @author vassaeve
 */
public interface RequirementRepository extends JpaRepository<RequirementEntity, Long> , JpaSpecificationExecutor<RequirementEntity>{

}
