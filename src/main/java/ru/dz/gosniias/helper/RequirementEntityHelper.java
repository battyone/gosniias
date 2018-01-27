package ru.dz.gosniias.helper;

import org.springframework.stereotype.Component;
import ru.dz.gosniias.dto.RuleDto;
import ru.dz.gosniias.entity.RequirementEntity;

/**
 *
 * @author vassaeve
 */
@Component
public class RequirementEntityHelper {

    public RuleDto createDto(RequirementEntity req) {
        RuleDto dto = new RuleDto(req.getId(), req.getDescription());
        dto.setTypeOf(req.getTypeOf().name());
        return dto;
    }
}
