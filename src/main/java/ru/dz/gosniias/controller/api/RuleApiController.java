package ru.dz.gosniias.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dz.gosniias.dto.FunctionDto;
import ru.dz.gosniias.dto.RuleDto;
import ru.dz.gosniias.dto.RuleForExecute;
import ru.dz.gosniias.dto.RuleRequestDto;
import ru.dz.gosniias.dto.RuleResponse;
import ru.dz.gosniias.dto.RulesDto;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.RequirementEntity;
import ru.dz.gosniias.entity.RequirementTypeOf;
import ru.dz.gosniias.helper.RequirementEntityHelper;
import ru.dz.gosniias.service.IProjectService;
import ru.dz.gosniias.service.IRequirementService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/rule")
@MultipartConfig(fileSizeThreshold = 20971520)
@Api(value = "/api/rule", description = "Управление правилами")
public class RuleApiController {

    @Autowired
    IRequirementService requirementService;

    @Autowired
    IProjectService projectService;

    @Autowired
    RequirementEntityHelper requirementEntityHelper;

    @ApiOperation(value = "Исполнение правила.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = RuleResponse.class)
    })
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public ResponseEntity<Object> execute(@RequestBody RuleForExecute requirement) throws JsonProcessingException {

        RuleResponse response = requirementService.execute(requirement);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Выбрать тело правила по его id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = RuleForExecute.class),
        @ApiResponse(code = 404, message = "Правила с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/body/{id}")
    public ResponseEntity<Object> getBody(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {

        RequirementEntity entity = requirementService.findByPK(id);
        if (entity != null) {
            return new ResponseEntity<>(new RuleForExecute(id, entity.getBody()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Выбрать правило по его id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = RuleDto.class),
        @ApiResponse(code = 404, message = "Правила с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> get(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {

        RequirementEntity entity = requirementService.findByPK(id);
        if (entity != null) {
            return new ResponseEntity<>(requirementEntityHelper.createDto(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Удалить правило с id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "deleted"),
        @ApiResponse(code = 404, message = "Правила с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {
        RequirementEntity entity = requirementService.findByPK(id);
        if (entity != null) {
            requirementService.delete(entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Список правил")
    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public ResponseEntity<Object> list(
            @ApiParam(required = true, defaultValue = "1") @PathVariable int page,
            @RequestParam(value = "projectId") long projectId,
            @RequestParam(value = "pageSize") int pageSize,
            @RequestParam(value = "typeOf", required = false) String typeOf) {
        try {
            RequirementTypeOf.valueOf(typeOf);
        } catch (Exception ex) {
            typeOf = RequirementTypeOf.CHECK_RULE.name();
        }
        ProjectEntity project = projectService.findByPK(projectId);

        Page<RequirementEntity> list = requirementService.findAllByPage(page, pageSize, typeOf, project);

        List< RuleDto> result = new ArrayList<>(list.getNumberOfElements());
        list.getContent().stream().forEach((script) -> {
            result.add(requirementEntityHelper.createDto(script));
        });

        int size = (int) list.getTotalElements();
        return new ResponseEntity<>(new RulesDto(size, result), HttpStatus.OK);
    }

    @ApiOperation(value = "Добавление или изменение правила (для изменения передать id)")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(
            @ModelAttribute RuleRequestDto request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        RequirementEntity req = null;
        boolean save = false;
        RequirementTypeOf requirementTypeOf = null;
        try {
            requirementTypeOf = RequirementTypeOf.valueOf(request.getTypeOf());
        } catch (Exception ex) {
            requirementTypeOf = RequirementTypeOf.CHECK_RULE;
        }

        ProjectEntity project = projectService.findByPK(request.getProjectId());

        if (project == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (request.getId() != null) {
            req = requirementService.findByPK(request.getId());
        }
        if (req == null) {
            req = new RequirementEntity();
        }

        if (!StringUtils.isEmpty(request.getDescription())) {
            req.setDescription(request.getDescription());
            save = true;
        }
        req.setTypeOf(requirementTypeOf);
        req.setProject(project);

        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                req.setBody(new String(bytes));
                save = true;
            } catch (IOException ex) {
                save = false;
            }
        }

        if (save) {
            requirementService.save(req);
            return new ResponseEntity<>(new FunctionDto(req.getId(), req.getDescription()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
