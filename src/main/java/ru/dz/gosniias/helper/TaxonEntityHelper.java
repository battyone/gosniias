package ru.dz.gosniias.helper;

import org.springframework.stereotype.Component;
import ru.dz.gosniias.dto.TaxonDto;
import ru.dz.gosniias.entity.TaxonEntity;

/**
 *
 * @author vassaeve
 */
@Component
public class TaxonEntityHelper {

    public TaxonDto createDto(TaxonEntity script) {
        TaxonDto dto = new TaxonDto(script.getId(), script.getDescription());
        return dto;
    }
}
