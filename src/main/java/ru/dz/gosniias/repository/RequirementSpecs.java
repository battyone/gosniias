package ru.dz.gosniias.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.RequirementEntity;
import ru.dz.gosniias.entity.RequirementEntity_;
import ru.dz.gosniias.entity.RequirementTypeOf;

/**
 *
 * @author vassaeve
 */
public class RequirementSpecs {

    public static Specification<RequirementEntity> isTypeOf(RequirementTypeOf typeOf, ProjectEntity projectId) {

        return new Specification<RequirementEntity>() {
            @Override
            public Predicate toPredicate(Root<RequirementEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate typeOfP = builder.equal(root.get(RequirementEntity_.typeOf), typeOf);
                Predicate projectIdP = builder.equal(root.get(RequirementEntity_.project), projectId);
                
                return builder.and(typeOfP, projectIdP);
            }
        };
    }
}
