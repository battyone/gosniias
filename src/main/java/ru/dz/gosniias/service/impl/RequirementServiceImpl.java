package ru.dz.gosniias.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.dz.gosniias.dto.CheckRequirementResponseDetail;
import ru.dz.gosniias.dto.GroovyScriptResponse;
import ru.dz.gosniias.dto.GroovyScriptResponseDetail;
import ru.dz.gosniias.dto.ResponseDetail;
import ru.dz.gosniias.dto.RuleForExecute;
import ru.dz.gosniias.dto.RuleResponse;
import ru.dz.gosniias.entity.FunctionEntity;
import ru.dz.gosniias.entity.ProjectEntity;
import ru.dz.gosniias.entity.RelationEntity;
import ru.dz.gosniias.entity.RequirementEntity;
import ru.dz.gosniias.entity.RequirementTypeOf;
import ru.dz.gosniias.entity.TaxonEntity;
import ru.dz.gosniias.repository.RelationRepository;
import ru.dz.gosniias.repository.RelationSpecs;
import ru.dz.gosniias.repository.RequirementRepository;
import ru.dz.gosniias.repository.RequirementSpecs;
import ru.dz.gosniias.repository.TaxonRepository;
import ru.dz.gosniias.service.IFunctionService;
import ru.dz.gosniias.service.IRequirementService;

/**
 *
 * @author vassaeve
 */
@Service
@Transactional
public class RequirementServiceImpl implements IRequirementService {

    public final String UTF8_BOM = "\uFEFF";

    @Autowired
    RequirementRepository repository;

    @Autowired
    IFunctionService functionService;

    @Autowired
    TaxonRepository taxonRepository;

    @Autowired
    RelationRepository relationRepository;

    @Override
    public List<RequirementEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public RequirementEntity findByPK(long pk) {
        return repository.findOne(pk);
    }

    @Override
    public Page<RequirementEntity> findAllByPage(int page, int pageSize, String typeOf, ProjectEntity projectId) {
        PageRequest request = new PageRequest(page - 1, pageSize);
        Page<RequirementEntity> result = repository.findAll(Specifications.where(RequirementSpecs.isTypeOf(RequirementTypeOf.valueOf(typeOf), projectId)), request);
        return result;
        //return repository.findAll(request);// findAllByPage(page, pageSize);
    }

    @Override
    public Long countAll() {
        return repository.count();
    }

    @Override
    public void save(RequirementEntity entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(RequirementEntity entity) {
        repository.delete(entity);
    }

    @Override
    public RuleResponse execute(RuleForExecute requirement) {

        RuleResponse response = new RuleResponse();
        RequirementEntity entity = findByPK(requirement.getId());
        RequirementTypeOf typeOf; //дефолтный тип правила
        if (entity != null) {
            typeOf = entity.getTypeOf();
        } else {            
            return response;
        }
        ProjectEntity project = entity.getProject();
        String body = requirement.getBody();
        if (StringUtils.isEmpty(body)) {
            body = entity.getBody();
        }
        if (StringUtils.isEmpty(body)) {
            return response;
        }
        boolean success = true;
        boolean total = true;

        try (BufferedReader reader = new BufferedReader(new StringReader(body))) {
            //Возможно, не самый оптимальный способ - задействование stream.
            List<String> lines = reader.lines().collect(Collectors.toList());

            for (String line : lines) {
                if (line.startsWith(UTF8_BOM)) {
                    line = line.substring(1);
                }
                if (StringUtils.isEmpty(line.replaceAll("\\s+", ""))) {
                    continue;
                }

                //Получили данные в строке. Тело скрипта нужно ТОЛЬКО если тип требования - скрипт
                AtomRequirement atom = parse(line, typeOf);

                ResponseDetail responseDetail = new ResponseDetail();
                GroovyScriptResponse grResp;
                if (!atom.isCanExecute()) {
                    grResp = new GroovyScriptResponse();
                    grResp.setData("");
                    grResp.setSuccess(false);
                    grResp.setError("not enough parameters");
                    success = false;
                    total = false;
                } else {
                    switch (typeOf) {
                        case SCRIPT:
                            grResp = functionService.executeScript(atom.getBody(), atom.getParams());
                            responseDetail = new GroovyScriptResponseDetail(grResp, line, atom.getFunctionName());
                            total = total & grResp.getSuccess();
                            break;
                        case SET_RULE:
                            executeSetRequirement(project, atom.getFunctionName(), atom.getParamsArray());
                            responseDetail = new ResponseDetail(line);
                            total = true;
                            break;
                        default:
                            boolean fact = checkRequirement(project, atom.getFunctionName(), atom.getParamsArray());
                            responseDetail = new CheckRequirementResponseDetail(fact, line);
                            total = total & fact;
                            break;
                    }
                } 

                response.getData().add(responseDetail);

            }
        } catch (IOException exc) {
            List<String> errors = new ArrayList<>(1);
            errors.add(exc.getMessage());
            response.setErrors(errors);
            success = false;

        }
        response.setSuccess(success);
        response.setTotal(total);
        return response;
    }

    private Map<String, TaxonEntity> fillTaxon(String[] paramsArray, ProjectEntity project) {
        Map<String, TaxonEntity> mapOfEntity = new HashMap<>(paramsArray.length);
        String trimParam;
        for (String param : paramsArray) {
            trimParam = param.trim();
            TaxonEntity taxon = taxonRepository.findByName(trimParam, project);
            if (taxon == null) {
                taxon = new TaxonEntity();
                taxon.setDescription(trimParam);
                taxon.setProject(project);
                taxon = taxonRepository.save(taxon);
            }
            mapOfEntity.put(trimParam, taxon);
        }
        return mapOfEntity;
    }

    @Override
    public void executeSetRequirement(ProjectEntity project, String functionName, String[] paramsArray) {

        if (paramsArray.length != 0) {

            Map<String, TaxonEntity> mapOfEntity = fillTaxon(paramsArray, project);

            RelationEntity entity = null;
            TaxonEntity parent = null;
            TaxonEntity child = null;
            if (paramsArray.length > 0) {
                parent = mapOfEntity.get(paramsArray[0]);
                if (paramsArray.length > 1) {
                    child = mapOfEntity.get(paramsArray[1]);
                }
            }

            //считаем, что хотя бы один параметр всегда есть.
            if (parent != null) {
                if (child == null) {
                    //факт в отношении одного объекта
                    entity = relationRepository.findOne(RelationSpecs.isParentExist(parent, functionName));
                } else {
                    entity = relationRepository.findOne(RelationSpecs.isPairExist(parent, child, functionName));
                }
                if (entity == null) {
                    entity = new RelationEntity();
                    entity.setRelationName(functionName);
                    entity.setTaxonParentId(parent);
                    if (child != null) {
                        entity.setTaxonChildId(child);
                    }
                    relationRepository.save(entity);
                }
            }
        }
    }

    @Override
    public boolean checkRequirement(ProjectEntity project, String functionName, String[] paramsArray) {

        if (paramsArray.length != 0) {
            Map<String, TaxonEntity> mapOfEntity = fillTaxon(paramsArray, project);

            RelationEntity entity = null;
            TaxonEntity parent = null;
            TaxonEntity child = null;
            if (paramsArray.length > 0) {
                parent = mapOfEntity.get(paramsArray[0]);
                if (paramsArray.length > 1) {
                    child = mapOfEntity.get(paramsArray[1]);
                }
            }
            if (parent != null) {
                if (child == null) {
                    entity = relationRepository.findOne(RelationSpecs.isParentExist(parent, functionName));
                } else {
                    entity = relationRepository.findOne(RelationSpecs.isPairExist(parent, child, functionName));
                }
                if (entity != null) {
                    return true;
                }
                //связи такой нет! ищем дальше
                if (child == null) {
                    //факты типа fact(Object) либо сразу найдены, либо их в системе нет
                    return false;
                }
                //Ищем ближайшего родителя у child. речь о двух параметрах однозначно!
//                boolean found = false;
                TaxonEntity parent1 = child;
                do {
                    parent1 = getFirstParent(parent1, functionName);
                    if (parent1 != null && Objects.equals(parent1.getId(), parent.getId())) {
                        return true;
                    }
                    if (parent1 == null) {
                        return false;
                    }
                } while (true);
            }
        }
        return false;
    }

    private AtomRequirement parse(String line, RequirementTypeOf typeOf) throws JsonProcessingException {
        AtomRequirement atom = new AtomRequirement();
        String functionName = "";
        String params = "";
        int pos = line.indexOf('(');
        if (pos != -1) {
            functionName = line.substring(0, pos).trim();
            int pos1 = line.indexOf(')');
            if (pos1 != -1) {
                params = line.substring(pos + 1, pos1);
            } else {
                params = line.substring(pos + 1);
            }
        } else {
            functionName = line.trim();
        }
        atom.setFunctionName(functionName);
        //поиск функции по имени 
        String[] paramsArray = params.replaceAll("(\\s*),(\\s*)", ",").split(",");
        atom.setParamsArray(paramsArray);

        if (typeOf == RequirementTypeOf.SCRIPT) {
            FunctionEntity function = functionService.findByName(functionName);
            if (function != null) {

                atom.setBody(function.getBody());
                String reqParams = function.getRequestNames();
                if (!reqParams.isEmpty()) {

                    Map<String, String> map = new HashMap<>(0);
                    String[] reqParamsArray = reqParams.split(",");
                    if (reqParamsArray.length <= paramsArray.length) {
                        for (int i = 0; i < reqParamsArray.length; i++) {
                            if (!StringUtils.isEmpty(reqParamsArray[i])) {
                                map.put(reqParamsArray[i].trim(), paramsArray[i].trim());
                            }
                        }
                        ObjectMapper mapper = new ObjectMapper();
                        String mapka = mapper.writeValueAsString(map);
                        atom.setParams(mapka);
                    } else {
                        atom.setCanExecute(false);
                    }
                }

            } else {
                atom.setCanExecute(false);
            }
        }
        //

        return atom;
    }

    private TaxonEntity getFirstParent(TaxonEntity child, String functionName) {
        RelationEntity entity = relationRepository.findOne(RelationSpecs.isParentForChildExist(child, functionName));
        if (entity != null) {
            return entity.getTaxonParentId();
        } else {
            return null;
        }
    }

    private class AtomRequirement {

        private boolean canExecute;
        private String functionName;
        private String body;
        private String params;
        private String[] paramsArray;

        public AtomRequirement() {
            this.body = "";
            this.params = "{}";
            this.canExecute = true;
            this.paramsArray = new String[]{};
        }

        public String[] getParamsArray() {
            return paramsArray;
        }

        public void setParamsArray(String[] paramsArray) {
            this.paramsArray = Arrays.copyOf(paramsArray, paramsArray.length);
        }

        public boolean isCanExecute() {
            return canExecute;
        }

        public void setCanExecute(boolean canExecute) {
            this.canExecute = canExecute;
        }

        public String getFunctionName() {
            return functionName;
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

    }
}
