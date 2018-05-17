package org.examples.pbk.otus.javaee.hw12.cdi;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class InvocationTimeMeasurementInterceptor {

    @Inject
    private InvocationTimeMarkerDao dao;

    @AroundInvoke
    public Object measureInvocationTime(InvocationContext ctx) throws Exception {
        long startTime = System.currentTimeMillis();
        Object result = ctx.proceed();
        long endTime = System.currentTimeMillis();
        InvocationTimeMarker marker = new InvocationTimeMarker();
        marker.setClassName(ctx.getMethod().getDeclaringClass().getName());
        marker.setMethodName(ctx.getMethod().getName());
        marker.setInvocationTime(endTime - startTime);
        dao.create(marker);
        return result;
    }
}
