package ru.dz.gosniias.helper;

import java.math.BigInteger;
import org.springframework.stereotype.Component;
import ru.dz.gosniias.dto.RelationDto;
import ru.dz.gosniias.entity.RelationEntity;

/**
 *
 * @author vassaeve
 */
@Component
public class RelationHelper {

    public RelationDto createDto(Object entity) {

        RelationDto dto = new RelationDto();

        Object[] obj = (Object[]) entity;
        dto.setId(((BigInteger) obj[0]).longValue());
        dto.setRelationName(obj[1].toString());

        dto.setParentId(((BigInteger) obj[2]).longValue());
        dto.setParentName(obj[3].toString());

        dto.setChildId(((BigInteger) obj[4]).longValue());
        dto.setChildName(obj[5].toString());

        return dto;
    }

    public RelationDto createDto(RelationEntity entity) {
        RelationDto dto = new RelationDto();
        dto.setChildId(entity.getTaxonChildId().getId());
        dto.setChildName(entity.getTaxonChildId().getDescription());
        dto.setId(entity.getId());
        dto.setRelationName(entity.getRelationName());

        dto.setParentId(entity.getTaxonParentId().getId());
        dto.setParentName(entity.getTaxonParentId().getDescription());
        return dto;
    }
}
