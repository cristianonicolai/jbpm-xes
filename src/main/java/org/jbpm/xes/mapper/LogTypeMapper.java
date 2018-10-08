package org.jbpm.xes.mapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.jbpm.xes.model.ClassifierType;
import org.jbpm.xes.model.ExtensionType;
import org.jbpm.xes.model.GlobalsType;
import org.jbpm.xes.model.LogType;

public class LogTypeMapper implements BiFunction<String, String, LogType> {

    @Override
    public LogType apply(String processName,
                         String processId) {
        final LogType log = new LogType();
        log.setXesFeatures("");
        log.setXesVersion(BigDecimal.valueOf(2.0));
        log.getExtension().addAll(getExtensions());
        log.getClassifier().add(new ClassifierType("Resource",
                                                   "org:resource"));
        log.getClassifier().add(new ClassifierType("Activity",
                                                   "jbpm:nodeid"));
        log.getGlobal().addAll(getGlobals());
        log.addStringType("source",
                          "jBPM");
        log.addStringType("lifecycle:model",
                          "standard");
        log.addStringType("concept:name",
                          processName);
        log.addStringType("jbpm:processid",
                          processId);
        return log;
    }

    protected List<ExtensionType> getExtensions() {
        return Arrays.asList(
                new ExtensionType("Lifecycle",
                                  "lifecycle",
                                  "http://www.xes-standard.org/lifecycle.xesext"),
                new ExtensionType("Organizational",
                                  "org",
                                  "http://www.xes-standard.org/org.xesext"),
                new ExtensionType("Time",
                                  "time",
                                  "http://www.xes-standard.org/time.xesext"),
                new ExtensionType("Concept",
                                  "concept",
                                  "http://www.xes-standard.org/concept.xesext")
        );
    }

    protected List<GlobalsType> getGlobals() {
//      Attributes that are always present
        final GlobalsType trace = new GlobalsType("trace");
        trace.addStringType("concept:name",
                            "");
        trace.addStringType("org:resource",
                            "");
        trace.addStringType("jbpm:start",
                            "");
        trace.addStringType("jbpm:status",
                            "");
        trace.addStringType("jbpm:version",
                            "");
        trace.addStringType("jbpm:description",
                            "");
        trace.addStringType("jbpm:instanceid",
                            "");
        trace.addStringType("jbpm:correlationkey",
                            "");
        trace.addStringType("jbpm:logid",
                            "");

        final GlobalsType event = new GlobalsType("event");
        event.addStringType("time:timestamp",
                            "");
        event.addStringType("concept:name",
                            "");
        event.addStringType("lifecycle:transition",
                            "");
        event.addStringType("jbpm:nodeinstanceid",
                            "");
        event.addStringType("jbpm:nodeid",
                            "");
        event.addStringType("jbpm:nodetype",
                            "");
        event.addStringType("jbpm:logid",
                            "");
        return Arrays.asList(
                trace,
                event
        );
    }
}
