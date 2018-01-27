package ru.dz.gosniias.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.entity.RelationEntity_;
import ru.dz.gosniias.entity.TaxonEntity;

/**
 *
 * @author vassaeve
 */
public class RelationSpecs {

//    public static Specification<RelationEntity> isProjectEq(ProjectEntity project) {
//
//        return new Specification<RelationEntity>() {
//            @Override
//            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//
//                Root<ProjectEntity> projectRoot = query.from(ProjectEntity.class);
//                Root<TaxonEntity> taxonRoot = query.from(TaxonEntity.class);
//                taxonRoot.join("project").get("id");
//
//                Predicate relationEq = builder.equal(taxonRoot.join("project").get("id"), project!=null ? project.getId() : null);
//
//                return relationEq;
//            }
//        };
//    }

    public static Specification<RelationEntity> isPairExist(TaxonEntity parent, TaxonEntity child, String relationName) {

        return new Specification<RelationEntity>() {
            @Override
            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate parentEq = builder.equal(root.get(RelationEntity_.taxonParentId), parent);
                Predicate childEq = builder.equal(root.get(RelationEntity_.taxonChildId), child);
                Predicate relationEq = builder.equal(root.get(RelationEntity_.relationName), relationName);

                return builder.and(parentEq, childEq, relationEq);
            }
        };
    }

    public static Specification<RelationEntity> isParentExist(TaxonEntity parent, String relationName) {

        return new Specification<RelationEntity>() {
            @Override
            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate parentEq = builder.equal(root.get(RelationEntity_.taxonParentId), parent);
                Predicate relationEq = builder.equal(root.get(RelationEntity_.relationName), relationName);

                return builder.and(parentEq, relationEq);
            }
        };
    }

    public static Specification<RelationEntity> isParentForChildExist(TaxonEntity child, String relationName) {

        return new Specification<RelationEntity>() {
            @Override
            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate childEq = builder.equal(root.get(RelationEntity_.taxonChildId), child);
                Predicate relationEq = builder.equal(root.get(RelationEntity_.relationName), relationName);

                return builder.and(childEq, relationEq);
            }
        };
    }

    public static Specification<RelationEntity> isTaxonExist(TaxonEntity taxon) {

        return new Specification<RelationEntity>() {
            @Override
            public Predicate toPredicate(Root<RelationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate parentEq = builder.equal(root.get(RelationEntity_.taxonParentId), taxon);
                Predicate childEq = builder.equal(root.get(RelationEntity_.taxonChildId), taxon);

                return builder.or(parentEq, childEq);
            }
        };
    }

}
