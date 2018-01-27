package ru.dz.gosniias.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.TaxonEntity;
import ru.dz.gosniias.entity.TaxonEntity_;

/**
 *
 * @author vassaeve
 */
public class TaxonSpecs {

    public static Specification<TaxonEntity> isProjectEq(ProjectEntity project) {

        return new Specification<TaxonEntity>() {
            @Override
            public Predicate toPredicate(Root<TaxonEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate relationEq = builder.equal(root.get(TaxonEntity_.project), project);

                return builder.and(relationEq);
            }
        };
    }

}
