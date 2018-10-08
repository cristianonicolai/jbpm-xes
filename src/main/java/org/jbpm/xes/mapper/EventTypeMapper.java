package org.jbpm.xes.mapper;

import java.util.function.BiFunction;

import org.dashbuilder.dataset.DataSet;
import org.jbpm.xes.model.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jbpm.xes.dataset.DataSetUtils.*;

public class EventTypeMapper implements BiFunction<DataSet, Integer, EventType> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeMapper.class);

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOG_DATE = "log_date";
    public static final String COLUMN_NODE_NAME = "nodeName";
    public static final String COLUMN_NODE_TYPE = "nodeType";
    public static final String COLUMN_NODE_INSTANCE_ID = "nodeInstanceId";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NODE_ID = "nodeId";
    public static final String COLUMN_WORK_ITEM_ID = "workItemId";
    public static final String COLUMN_CREATED_BY = "createdBy";
    public static final String COLUMN_ACTUAL_OWNER = "actualOwner";

    @Override
    public EventType apply(DataSet dataSet,
                           Integer row) {
        EventType event = new EventType();
        event.addDateType(
                "time:timestamp",
                getColumnDateValue(dataSet,
                                   COLUMN_LOG_DATE,
                                   row));

        String nodeName = getColumnStringValue(dataSet,
                                               COLUMN_NODE_NAME,
                                               row);

        String nodeType = getColumnStringValue(dataSet,
                                               COLUMN_NODE_TYPE,
                                               row);

        event.addStringType(
                "concept:name",
                nodeName.isEmpty() ? nodeType : nodeName);

        String nodeInstanceId = getColumnStringValue(dataSet,
                                                     COLUMN_NODE_INSTANCE_ID,
                                                     row);

        event.addStringType(
                "concept:instance",
                nodeInstanceId);

        Integer type = getColumnIntValue(dataSet,
                                         COLUMN_TYPE,
                                         row);

        event.addStringType(
                "lifecycle:transition",
                type == 0 ? "start" : "complete");

//        Custom jBPM attributes
        event.addStringType(
                "jbpm:nodeinstanceid",
                nodeInstanceId);
        event.addIntegerType(
                "jbpm:logid",
                getColumnLongValue(dataSet,
                                   COLUMN_ID,
                                   row));
        event.addStringType(
                "jbpm:nodeid",
                getColumnStringValue(dataSet,
                                     COLUMN_NODE_ID,
                                     row));
        event.addStringType(
                "jbpm:nodename",
                nodeName);
        Integer workItemId = getColumnIntValue(dataSet,
                                               COLUMN_WORK_ITEM_ID,
                                               row);
        event.addIntegerType(
                "jbpm:workitemid",
                workItemId);
        event.addStringType(
                "jbpm:nodetype",
                nodeType);

        if ("HumanTaskNode".equals(nodeType) && workItemId != null) {
            event.addStringType("org:resource",
                                type == 0 ? getColumnStringValue(dataSet,
                                                                 COLUMN_CREATED_BY,
                                                                 row) :
                                        getColumnStringValue(dataSet,
                                                             COLUMN_ACTUAL_OWNER,
                                                             row));
        }

        LOGGER.debug("Generated event object: {}",
                     event);
        return event;
    }
}
