package ru.dz.gosniias.dto;

/**
 *
 * @author vassaeve
 */
public class CheckRequirementResponseDetail extends ResponseDetail {

    private static final long serialVersionUID = -62202517773610910L;

    private boolean fact;

    public CheckRequirementResponseDetail() {
    }

    public CheckRequirementResponseDetail(String expression) {
        super(expression);
    }

    public CheckRequirementResponseDetail(boolean fact, String expression) {
        super(expression);
        this.fact = fact;
    }

    public boolean isFact() {
        return fact;
    }

    public void setFact(boolean fact) {
        this.fact = fact;
    }

}
