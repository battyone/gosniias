package ru.dz.gosniias.entity;

/**
 *
 * @author vassaeve
 */
public enum FunctionTypeOf {

    /**
     *
     */
    FUNCTION,
    /**
     *
     */
    VOID,
    /**
     *
     */
    ACTION;

    public static FunctionTypeOf valueOf1(String name) {
        switch (name) {
            case "FUNCTION":
                return FUNCTION;
            case "VOID":
                return VOID;
            case "ACTION":
                return ACTION;
        }
        return VOID;
    }
}
