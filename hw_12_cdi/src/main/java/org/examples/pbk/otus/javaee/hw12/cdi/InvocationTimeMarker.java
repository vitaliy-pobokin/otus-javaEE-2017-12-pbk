package org.examples.pbk.otus.javaee.hw12.cdi;

import javax.persistence.*;

@Entity(name = "STAT_INVOCATION_TIME_MARKER")
public class InvocationTimeMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;

    @Column(name = "CLASS_NAME")
    private String className;

    @Column(name = "METHOD_NAME")
    private String methodName;

    @Column(name = "INVOCATION_TIME")
    private long invocationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getInvocationTime() {
        return invocationTime;
    }

    public void setInvocationTime(long invocationTime) {
        this.invocationTime = invocationTime;
    }

    @Override
    public String toString() {
        return "InvocationTimeMarker{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", invocationTime=" + invocationTime +
                '}';
    }
}
