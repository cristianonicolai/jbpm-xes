package org.jbpm.xes.mapper;

import java.util.function.Function;

import static org.kie.api.runtime.process.ProcessInstance.*;

public class ProcessInstaceStatusMapper implements Function<Integer, String> {

    @Override
    public String apply(Integer status) {
        if (status == null) {
            return "Unknown";
        }
        switch (status) {
            case STATE_PENDING:
                return "pending";
            case STATE_ACTIVE:
                return "active";
            case STATE_COMPLETED:
                return "completed";
            case STATE_ABORTED:
                return "aborted";
            case STATE_SUSPENDED:
                return "suspended";
            default:
                return "Unknown";
        }
    }
}
