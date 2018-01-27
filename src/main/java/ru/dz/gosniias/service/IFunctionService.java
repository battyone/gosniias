package ru.dz.gosniias.service;

import java.util.List;
import org.springframework.data.domain.Page;
import ru.dz.gosniias.dto.GroovyScriptResponse;
import ru.dz.gosniias.entity.FunctionEntity;

/**
 *
 * @author vassaeve
 */
public interface IFunctionService {

    public List<FunctionEntity> findAll();

    public FunctionEntity findByPK(long pk);

    public Page<FunctionEntity> findAllByPage(int page, int pageSize);

    public Long countAll();

    public void save(FunctionEntity script);

    public void delete(FunctionEntity entity);

    public boolean validate(String scriptText);

    public FunctionEntity findByName(String functionName);

    public GroovyScriptResponse executeScript(String body, String params);

}
