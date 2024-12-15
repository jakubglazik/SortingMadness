package pl.put.poznan.sortingMadness.rest;

public class FunctionRequest {
    private String methodName;
    private String className;
    private boolean IsStatic;
    private Class<?>[] paramTypes;
    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isStaticMethod() {
        return IsStatic;
    }

    public void setStaticMethod(boolean staticMethod) {
        this.IsStatic = staticMethod;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
