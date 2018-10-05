package org.jbpm.xes;

public interface XESExportService {

    String export(XESProcessFilter filter) throws Exception;
}
