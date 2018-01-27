package ru.dz.gosniias.dto;

/**
 *
 * @author vassaeve
 */
public class GroovyScriptResponseDetail extends ResponseDetail {

    private static final long serialVersionUID = -6551630861582635598L;

//    @JsonIgnore
    private String functionName;
    private GroovyScriptResponse response;

    public GroovyScriptResponseDetail() {
        this.response = new GroovyScriptResponse();
    }

    public GroovyScriptResponseDetail(GroovyScriptResponse grResp, String expression) {
        super(expression);
        this.response = new GroovyScriptResponse();
        this.response.setData(grResp.getData());
        this.response.setErrors(grResp.getErrors());
        this.response.setSuccess(grResp.getSuccess());
    }

    public GroovyScriptResponseDetail(GroovyScriptResponse grResp, String expression, String functionName) {
        super(expression);
        this.response = new GroovyScriptResponse();
        this.response.setData(grResp.getData());
        this.response.setErrors(grResp.getErrors());
        this.response.setSuccess(grResp.getSuccess());
        this.expression = expression;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public GroovyScriptResponse getResponse() {
        return response;
    }

    public void setResponse(GroovyScriptResponse groovyResponse) {
        this.response = groovyResponse;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

}
