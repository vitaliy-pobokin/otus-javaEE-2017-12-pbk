package org.examples.pbk.otus.javaee.hw12.cdi;

import java.util.List;

public interface InvocationTimeMarkerDao {
    void create(InvocationTimeMarker marker);
    List<AverageInvocationTimeDTO> getAverageInvocationTime();
}
