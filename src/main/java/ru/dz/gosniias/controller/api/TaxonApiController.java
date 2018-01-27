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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dz.gosniias.dto.TaxonDto;
import ru.dz.gosniias.dto.TaxonsDto;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.entity.TaxonEntity;
import ru.dz.gosniias.helper.TaxonEntityHelper;
import ru.dz.gosniias.service.IRelationService;
import ru.dz.gosniias.service.ITaxonService;

/**
 *
 * @author vassaeve
 */
@RestController
@RequestMapping("/api/taxon")
@Api(value = "/api/taxon", description = "Управление понятиями/терминами")
public class TaxonApiController {

    @Autowired
    ITaxonService iTaxon;

    @Autowired
    IRelationService relationService;

    @Autowired
    TaxonEntityHelper taxonEntityHelper;

    @ApiOperation(value = "Удалить термин с id")
    @ApiResponses({
        @ApiResponse(code = 200, message = "deleted"),
        @ApiResponse(code = 404, message = "Термина с таким ID не существует")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Object> delete(@ApiParam(required = true, defaultValue = "1") @PathVariable("id") long id) {
        TaxonEntity entity = iTaxon.findByPK(id);
        if (entity != null) {

            List<RelationEntity> relations = relationService.findByTaxon(entity);
            relationService.deleteAll(relations);
            iTaxon.delete(entity);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Список понятий")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = TaxonsDto.class)
    })

    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public ResponseEntity<Object> list(
            @ApiParam(value = "Номер страницы", required = true, defaultValue = "1") @PathVariable int page,
            @ApiParam(value = "Размер страницы") @RequestParam(value = "pageSize") int pageSize,
            @ApiParam(value = "ID проекта") @RequestParam(value = "projectId", required = true) long currentProject) {
        Page<TaxonEntity> list = iTaxon.findAllByPage(currentProject, page, pageSize);
        List<TaxonDto> result = new ArrayList<>(list.getNumberOfElements());
        list.getContent().forEach((taxon) -> {
            result.add(taxonEntityHelper.createDto(taxon));
        });

        int size = (int) list.getTotalElements();
        return new ResponseEntity<>(new TaxonsDto(size, result), HttpStatus.OK);
    }
}
