package ru.dz.gosniias.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dz.gosniias.dto.FunctionDto;
import ru.dz.gosniias.dto.FunctionsDto;
import ru.dz.gosniias.dto.GroovyScriptForExecute;
import ru.dz.gosniias.entity.FunctionEntity;
import ru.dz.gosniias.entity.FunctionTypeOf;
import ru.dz.gosniias.helper.FunctionEntityHelper;
import ru.dz.gosniias.service.IFunctionService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/function")
@MultipartConfig(fileSizeThreshold = 20971520)
@Api(value = "/api/function", description = "Управление скриптами groovy")
public class FunctionApiController {

    @Autowired
    IFunctionService iFunctions;

    @Autowired
    FunctionEntityHelper functionsHelper;

    @ApiOperation(value = "Выбрать тело функции по его имени")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = GroovyScriptForExecute.class),
        @ApiResponse(code = 404, message = "Функции с таким именем не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/bodyByName/{name}")
    public ResponseEntity<Object> getBodyByName(@ApiParam(required = true) @PathVariable("name") String name) {
        FunctionEntity entity = iFunctions.findByName(name);
        if (entity != null) {
            return bodyById(entity.getId());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Выбрать тело функции по его id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = GroovyScriptForExecute.class),
        @ApiResponse(code = 404, message = "Функции с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/bodyById/{id}")
    public ResponseEntity<Object> bodyById(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {

        FunctionEntity entity = iFunctions.findByPK(id);
        if (entity != null) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = new HashMap<>(0);
            if (entity.getRequestNames() != null && !StringUtils.isEmpty(entity.getRequestNames())) {
                String[] params = entity.getRequestNames().replaceAll("undefined", "").split(",");
                for (String param : params) {
                    if (!StringUtils.isEmpty(param)) {
                        map.put(param, "");
                    }
                }
            }
            String mapka;
            try {
                mapka = mapper.writeValueAsString(map);
            } catch (JsonProcessingException ex) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(new GroovyScriptForExecute(entity.getBody(), mapka), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Выбрать функцию по его id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = FunctionDto.class),
        @ApiResponse(code = 404, message = "Функции с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Object> get(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {

        FunctionEntity entity = iFunctions.findByPK(id);
        if (entity != null) {
            return new ResponseEntity<>(functionsHelper.createDto(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Удалить функцию с id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "deleted"),
        @ApiResponse(code = 404, message = "Функции с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {
        FunctionEntity entity = iFunctions.findByPK(id);
        if (entity != null) {
            iFunctions.delete(entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Список скриптов")
    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public ResponseEntity<Object> list(
            @ApiParam(required = true, defaultValue = "1") @PathVariable int page,
            @RequestParam(value = "pageSize") int pageSize) {
        Page<FunctionEntity> list = iFunctions.findAllByPage(page, pageSize);
        List<FunctionDto> result = new ArrayList<>(list.getNumberOfElements());
        list.getContent().forEach((script) -> {
            result.add(functionsHelper.createDto(script));
        });

        int size = (int) list.getTotalElements();
        return new ResponseEntity<>(new FunctionsDto(size, result), HttpStatus.OK);
    }

    @ApiOperation(value = "Валидация функции")
    @RequestMapping(value = "/validate/{functionId}", method = RequestMethod.GET)
    public ResponseEntity<Object> validate(@ApiParam(required = true, defaultValue = "1") @PathVariable long functionId) {
        try {
            FunctionEntity script = iFunctions.findByPK(functionId);
            if (script != null && !StringUtils.isEmpty(script.getBody())) {
                boolean A = iFunctions.validate(script.getBody());
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Добавление или изменение скрипта (для изменения передать id).")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(
            @ModelAttribute FunctionDto function,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        FunctionEntity script = null;
        boolean save = false;

        if (function.getId() == null) {
            script = new FunctionEntity();
        } else {
            script = iFunctions.findByPK(function.getId());
            if (script == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        if (!StringUtils.isEmpty(function.getDescription())) {
            script.setDescription(function.getDescription());
            save = true;
        }

        script.setRequestNames(function.getRequestNames());

        script.setTypeOf(FunctionTypeOf.valueOf1(function.getTypeOf()));

        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                script.setBody(new String(bytes));
                save = true;
            } catch (IOException ex) {
                save = false;
            }
        }

        if (save) {
            boolean A = iFunctions.validate(script.getBody());
            iFunctions.save(script);
            return new ResponseEntity<>(new FunctionDto(script.getId(), script.getDescription()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
