package ru.dz.gosniias.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dz.gosniias.dto.GroovyScriptForExecute;
import ru.dz.gosniias.dto.GroovyScriptResponse;
import ru.dz.gosniias.service.IFunctionService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/groovy")
@Api(value = "/api/groovy", description = "Выполнение groovy скриптов")
public class GroovyScriptApiController {

    @Autowired
    IFunctionService functionService;

    @ApiOperation(value = "Выполнение скрипта")
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public ResponseEntity<Object> execute(@RequestBody GroovyScriptForExecute groovy) {
        GroovyScriptResponse response = functionService.executeScript(groovy.getBody(), groovy.getParams());//new GroovyScriptResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
