package ru.dz.gosniias.dto;

import java.io.Serializable;

/**
 *
 * @author vassaeve
 */
public class ResponseDetail implements Serializable {

    private static final long serialVersionUID = 4772155665198170116L;

    protected String expression;

    public ResponseDetail() {
    }

    public ResponseDetail(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
