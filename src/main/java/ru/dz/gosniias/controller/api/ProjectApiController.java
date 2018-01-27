package ru.dz.gosniias.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dz.gosniias.dto.ProjectDto;
import ru.dz.gosniias.dto.ProjectsDto;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.helper.ProjectEntityHelper;
import ru.dz.gosniias.service.IProjectService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/project")
@Api(value = "/api/project", description = "Управление проектами")
public class ProjectApiController {

    @Autowired
    IProjectService projectService;

    @Autowired
    ProjectEntityHelper projectEntityHelper;

    @ApiOperation(value = "Выбрать проект по его id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = ProjectDto.class),
        @ApiResponse(code = 404, message = "Проекта с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> get(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {

        ProjectEntity entity = projectService.findByPK(id);
        if (entity != null) {
            return new ResponseEntity<>(projectEntityHelper.createDto(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Удалить проект по id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "deleted"),
        @ApiResponse(code = 404, message = "Проекта с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {
        ProjectEntity entity = projectService.findByPK(id);
        if (entity != null) {
            projectService.delete(entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Список проектов")
    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public ResponseEntity<Object> list(
            @ApiParam(required = true, defaultValue = "1") @PathVariable int page,
            @RequestParam(value = "pageSize") int pageSize) {
        Page<ProjectEntity> list = projectService.findAllByPage(page, pageSize);
        List<ProjectDto> result = new ArrayList<>(list.getNumberOfElements());
        list.getContent().forEach((script) -> {
            result.add(projectEntityHelper.createDto(script));
        });

        int size = (int) list.getTotalElements();
        return new ResponseEntity<>(new ProjectsDto(size, result), HttpStatus.OK);
    }

    @ApiOperation(value = "Добавление или изменение проекта (для изменения передать id проекта)")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody ProjectDto project) {

        ProjectEntity entity = null;
        boolean save = false;
        if (project.getId() != null) {
            //Обновляем существующий скрипт
            try {
                entity = projectService.findByPK(project.getId());
            } catch (NumberFormatException ex) {
            }
        } else {
            entity = new ProjectEntity();
        }
        if (entity != null) {
            if (!StringUtils.isEmpty(project.getDescription())) {
                entity.setDescription(project.getDescription());
                save = true;
            }

            if (save) {
                projectService.save(entity);
                return new ResponseEntity<>(projectEntityHelper.createDto(entity), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
