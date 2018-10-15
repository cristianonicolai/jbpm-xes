package org.jbpm.xes.dataset;

import java.util.List;
import java.util.function.Function;

import org.dashbuilder.DataSetCore;
import org.dashbuilder.dataprovider.DataSetProviderRegistry;
import org.dashbuilder.dataprovider.sql.SQLDataSetProvider;
import org.dashbuilder.dataset.DataSet;
import org.dashbuilder.dataset.DataSetLookupBuilder;
import org.dashbuilder.dataset.DataSetLookupFactory;
import org.dashbuilder.dataset.DataSetManager;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.DataSetDefFactory;
import org.dashbuilder.dataset.def.DataSetDefRegistry;
import org.dashbuilder.dataset.def.SQLDataSetDefBuilder;
import org.dashbuilder.dataset.filter.ColumnFilter;
import org.dashbuilder.dataset.sort.SortOrder;
import org.kie.server.api.model.definition.QueryDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSetServiceImpl implements DataSetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSetServiceImpl.class);

    private DataSetDefRegistry dataSetDefRegistry = DataSetCore.get().getDataSetDefRegistry();
    private DataSetManager dataSetManager = DataSetCore.get().getDataSetManager();
    private DataSetProviderRegistry providerRegistry = DataSetCore.get().getDataSetProviderRegistry();
    private Function<String, String> dataSourceResolver;

    public DataSetServiceImpl() {
        this(ds -> ds);
    }

    public DataSetServiceImpl(Function<String, String> dataSourceResolver) {
        this.dataSourceResolver = dataSourceResolver;
        this.providerRegistry.registerDataProvider(SQLDataSetProvider.get());
        final List<QueryDefinition> queryDefinitions = QueryDefinitionLoader.get().loadQueryDefinitions();
        queryDefinitions.forEach(q -> registerDataSetDefinition(q));
    }

    protected void registerDataSetDefinition(final QueryDefinition queryDefinition) {
        LOGGER.info("Loaded query definition: {}",
                    queryDefinition);
        SQLDataSetDefBuilder<?> builder = DataSetDefFactory.newSQLDataSetDef()
                .uuid(queryDefinition.getName())
                .name(queryDefinition.getTarget() + "-" + queryDefinition.getName())
                .dataSource(dataSourceResolver.apply(queryDefinition.getSource()))
                .dbSQL(queryDefinition.getExpression(),
                       true);

        DataSetDef dataSetDef = builder.buildDef();

        dataSetDef.setPublic(false);

        dataSetDefRegistry.registerDataSetDef(dataSetDef);
        LOGGER.info("Data Set registered {}",
                    dataSetDef);
    }

    @Override
    public DataSet findTraces(ColumnFilter... filters){
        DataSetLookupBuilder<?> builder = DataSetLookupFactory.newDataSetLookupBuilder().dataset("jbpmXESTraces");
        builder.filter(filters);
        builder.sort("id", SortOrder.ASCENDING);
        DataSet result = dataSetManager.lookupDataSet(builder.buildLookup());
        LOGGER.debug("Data set query result: {}", result);
        return result;
    }

    @Override
    public DataSet findEvents(ColumnFilter... filters){
        DataSetLookupBuilder<?> builder = DataSetLookupFactory.newDataSetLookupBuilder().dataset("jbpmXESEvents");
        builder.filter(filters);
        builder.sort("processInstanceId", SortOrder.ASCENDING);
        builder.sort("log_date", SortOrder.ASCENDING);
        DataSet result = dataSetManager.lookupDataSet(builder.buildLookup());
        LOGGER.debug("Data set query result: {}", result);
        return result;
    }
}
