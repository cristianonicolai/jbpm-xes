package org.jbpm.xes.dataset;

import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.filter.ColumnFilter;

public interface DataSetService {

    DataSet findTraces(ColumnFilter... filters);

    DataSet findEvents(ColumnFilter... filters);
}
