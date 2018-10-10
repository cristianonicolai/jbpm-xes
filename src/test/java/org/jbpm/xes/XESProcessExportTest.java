package org.jbpm.xes;

import java.util.stream.IntStream;
import javax.naming.InitialContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.jbpm.xes.dataset.DataSetService;
import org.jbpm.xes.dataset.DataSetServiceImpl;
import org.jbpm.xes.model.LogType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;

import static org.junit.Assert.*;

public class XESProcessExportTest extends JbpmJUnitBaseTestCase {

    private static final String DS_NAME = "jdbc/xesDs";
    private BasicDataSource xesDataSource;

    public XESProcessExportTest() {
        super(true,
              true);
    }

    public static BasicDataSource setupDataSource(String connectURI) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl(connectURI);
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Before
    public void setup() throws Exception {
        InitialContext context = new InitialContext();
        xesDataSource = setupDataSource("jdbc:h2:mem:jbpm-db;MVCC=true");
        context.bind(DS_NAME,
                     xesDataSource);
    }

    @After
    public void cleanup() throws Exception {
        if (xesDataSource != null) {
            xesDataSource.close();
        }
    }

    @Test
    public void testHelloProcess() throws Exception {
        // create runtime manager with single process - hello.bpmn
        createRuntimeManager("hello.bpmn");

        // take RuntimeManager to work with process engine
        RuntimeEngine runtimeEngine = getRuntimeEngine();

        // get access to KieSession instance
        KieSession ksession = runtimeEngine.getKieSession();

        int instances = 5;
        IntStream.range(0,
                        instances).forEach(i -> {
            // start process
            ProcessInstance processInstance = ksession.startProcess("hello");

            // check whether the process instance has completed successfully
            assertProcessInstanceCompleted(processInstance.getId(),
                                           ksession);

            // check what nodes have been triggered
            assertNodeTriggered(processInstance.getId(),
                                "Start",
                                "Hello",
                                "End");
        });

        DataSetService dataSetService = new DataSetServiceImpl(dsName -> DS_NAME);
        XESExportServiceImpl service = new XESExportServiceImpl();
        service.setDataSetService(dataSetService);
        final String xml = service.export(XESProcessFilter.builder().withProcessId("hello").build());
        final XESLogMarshaller marshaller = new XESLogMarshaller();
        marshaller.validate(xml);

        LogType log = marshaller.unmarshall(xml);
        assertNotNull(log);
        assertEquals(4,
                     log.getExtension().size());
        assertEquals(3,
                     log.getStringOrDateOrInt().size());
        assertEquals(2,
                     log.getGlobal().size());
        assertEquals(2,
                     log.getClassifier().size());
        assertEquals(instances,
                     log.getTrace().size());
        IntStream.range(0,
                        instances).forEach(i -> assertEquals(6,
                                                             log.getTrace().get(i).getEvent().size()));
    }
}
