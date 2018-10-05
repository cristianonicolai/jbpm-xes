package org.jbpm.xes.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.dashbuilder.dataset.filter.ColumnFilter;
import org.jbpm.xes.XESProcessFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.dashbuilder.dataset.filter.FilterFactory.*;
import static org.jbpm.xes.mapper.TraceTypeMapper.*;

public class ColumnFilterMapper implements Function<XESProcessFilter, List<ColumnFilter>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnFilterMapper.class);

    @Override
    public List<ColumnFilter> apply(XESProcessFilter filter) {
        List<ColumnFilter> filters = new ArrayList<>();
        if (isNullOrEmpty(filter.getProcessId()) == false) {
            filters.add(equalsTo(COLUMN_PROCESS_ID,
                                 filter.getProcessId()));
        }
        if (isNullOrEmpty(filter.getProcessVersion()) == false) {
            filters.add(equalsTo(COLUMN_PROCESS_VERSION,
                                 filter.getProcessVersion()));
        }
        if (filter.getStatus() != null && filter.getStatus().isEmpty() == false) {
            filters.add(in(COLUMN_STATUS,
                           filter.getStatus()));
        }
        if (filter.getSince() != null) {
            filters.add(greaterOrEqualsTo(COLUMN_START_DATE,
                                          filter.getSince()));
        }
        if (filter.getTo() != null) {
            filters.add(lowerOrEqualsTo(COLUMN_END_DATE,
                                        filter.getTo()));
        }
        LOGGER.debug("Traces column filters: {}",
                     filters);
        return filters;
    }
}
