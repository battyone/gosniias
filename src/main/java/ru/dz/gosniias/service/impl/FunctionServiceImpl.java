package ru.dz.gosniias.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dz.gosniias.dto.GroovyScriptResponse;
import ru.dz.gosniias.entity.FunctionEntity;
import ru.dz.gosniias.repository.FunctionEntityRepository;
import ru.dz.gosniias.service.IFunctionService;
import ru.dz.gosniias.service.ITaxonService;

/**
 *
 * @author vassaeve
 */
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

    @Autowired
    FunctionEntityRepository repository;

    @Autowired
    ITaxonService taxonService;

    @Override
    public List<FunctionEntity> findAll() {
        return repository.findAll();
        //return functionsDao.findAll();
    }

    @Override
    public FunctionEntity findByPK(long pk) {
        return repository.findOne(pk);
        //return functionsDao.findByPK(pk);
    }

    @Override
    public Page<FunctionEntity> findAllByPage(int page, int pageSize) {
        PageRequest request = new PageRequest(page - 1, pageSize);

        return repository.findAll(request); //findAllByPage(page, pageSize);
    }

    @Override
    public Long countAll() {
        return repository.count();
        //return functionsDao.countAll();
    }

    @Override
    public void save(FunctionEntity entity) {
        repository.save(entity);
        //functionsDao.createOrUpdate(entity);
    }

    @Override
    public void delete(FunctionEntity entity) {
        repository.delete(entity);
    }

    @Override
    public boolean validate(String scriptText) {
        try {
            GroovyClassLoader gcl = new GroovyClassLoader();
            Class cls = gcl.parseClass(scriptText);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String preparse(String body) {
        String A = body.replaceAll("File", "_").replaceAll("Reader", "_");
        A = A.replaceAll("Writer", "_").replaceAll("Path", "_");
        A = A.replaceAll("InputStream", "_").replaceAll("OutputStream", "_");
        return A;
    }

    @Override
    public GroovyScriptResponse executeScript(String body, String params) {
        GroovyScriptResponse response = new GroovyScriptResponse();
        ObjectMapper mapper = new ObjectMapper();

        boolean success = false;
        body = preparse(body);
        if (!body.isEmpty()) {
            try {
                Binding binding = new Binding();
                Map<String, Object> mapka = mapper.readValue(params, Map.class);
                mapka.keySet().stream().forEach((key) -> {
                    binding.setVariable(key, mapka.get(key));
                });

                GroovyShell shell = new GroovyShell(binding);
                Object result = shell.evaluate(body);
                if (result != null) {
                    try {
                        String resultStr = mapper.writeValueAsString(result);
                        response.setData(resultStr);
                    } catch (Exception ex) {
                    }
                }
                success = true;
            } catch (Exception ex) {
                List<String> errors = new ArrayList<>(1);
                errors.add(ex.getMessage());
                response.setErrors(errors);
            }
        }
        response.setSuccess(success);

        return response;
    }

    @Override
    public FunctionEntity findByName(String functionName) {
        return repository.findByName(functionName);
    }

}
