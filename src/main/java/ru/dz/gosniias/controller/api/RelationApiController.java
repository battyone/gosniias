package ru.dz.gosniias.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dz.gosniias.dto.PageWrapper;
import ru.dz.gosniias.dto.RelationDto;
import ru.dz.gosniias.dto.RelationsDto;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.helper.RelationHelper;
import ru.dz.gosniias.service.IRelationService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/relation")
@Api(value = "/api/relation", description = "Управление фактами")
public class RelationApiController {

    @Autowired
    IRelationService relationService;

    @Autowired
    RelationHelper relationHelper;

    @ApiOperation(value = "Удалить факт с id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "deleted"),
        @ApiResponse(code = 404, message = "Факта с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {
        RelationEntity entity = relationService.findByPK(id);
        if (entity != null) {

            relationService.delete(entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Список понятий")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = RelationsDto.class)
    })

    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public ResponseEntity<Object> list(
            @ApiParam(value = "Номер страницы", required = true, defaultValue = "1") @PathVariable int page,
            @ApiParam(value = "Размер страницы") @RequestParam(value = "pageSize") int pageSize,
            @ApiParam(value = "ID проекта") @RequestParam(value = "projectId", required = true) long currentProject) {

        PageWrapper list = relationService.findAllByPage(currentProject, page, pageSize);
        List<RelationDto> result = new ArrayList<>(list.getTotalCount());
        list.getData().forEach((relation) -> {
            result.add(relationHelper.createDto(relation));
        });

        int size = list.getTotalCount();
        return new ResponseEntity<>(new RelationsDto(size, result), HttpStatus.OK);
    }
}
