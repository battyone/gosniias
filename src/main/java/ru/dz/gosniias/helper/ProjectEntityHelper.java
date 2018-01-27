package ru.dz.gosniias.helper;

import org.springframework.stereotype.Component;
import ru.dz.gosniias.dto.ProjectDto;
import ru.dz.gosniias.entity.ProjectEntity;

/**
 *
 * @author vassaeve
 */
@Component
public class ProjectEntityHelper {

    public ProjectDto createDto(ProjectEntity script) {
        ProjectDto dto = new ProjectDto(script.getId(), script.getDescription());
        return dto;
    }
}
