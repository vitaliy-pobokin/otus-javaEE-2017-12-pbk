package org.examples.pbk.otus.javaee.hw12.cdi;

import java.math.BigDecimal;

public class AverageInvocationTimeDTO {
    private String className;
    private String methodName;
    private double averageInvocationTime;

    public AverageInvocationTimeDTO(String className, String methodName, double averageInvocationTime) {
        this.className = className;
        this.methodName = methodName;
        this.averageInvocationTime = averageInvocationTime;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public double getAverageInvocationTime() {
        return averageInvocationTime;
    }

    @Override
    public String toString() {
        return "AverageInvocationTimeDTO{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", averageInvocationTime=" + averageInvocationTime +
                '}';
    }
}
