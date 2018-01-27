package ru.dz.gosniias.helper;

import org.springframework.stereotype.Component;
import ru.dz.gosniias.dto.FunctionDto;
import ru.dz.gosniias.entity.FunctionEntity;

/**
 *
 * @author vassaeve
 */
@Component
public class FunctionEntityHelper {

    public FunctionDto createDto(FunctionEntity script) {
        FunctionDto dto = new FunctionDto(script.getId(), script.getDescription(), script.getTypeOf().name());
        dto.setRequestNames(script.getRequestNames());
        return dto;
    }
}
