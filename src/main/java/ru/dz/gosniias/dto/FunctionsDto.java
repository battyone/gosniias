package ru.dz.gosniias.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
public class FunctionsDto implements Serializable {

    private static final long serialVersionUID = 12979237347346891L;

    private int size;
    private List<FunctionDto> functions;

    public FunctionsDto() {
        this.functions = new ArrayList<>(0);
    }

    public FunctionsDto(List<FunctionDto> list) {
        this.functions = new ArrayList<>(list);
    }

    public FunctionsDto(int size, List<FunctionDto> list) {
        this.size = size;
        this.functions = new ArrayList<>(list);
    }

    public List<FunctionDto> getFunctions() {
        return functions;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFunctions(List<FunctionDto> functions) {
        this.functions.clear();
        this.functions.addAll(functions);
    }

}
