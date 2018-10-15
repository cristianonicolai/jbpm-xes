package org.jbpm.xes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.cli.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.jbpm.xes.dataset.DataSetService;
import org.jbpm.xes.dataset.DataSetServiceImpl;

public class XESExportMain {

    private static final String DS_NAME = "jdbc/xesDs";

    public static void main(String[] args) {
        // create the Options
        Options options = getOptions();

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                           "org.osjava.sj.memory.MemoryContextFactory");
        System.setProperty("org.osjava.sj.jndi.shared",
                           "true");
        InitialContext context = null;
        CommandLineParser parser = new DefaultParser();
        try (BasicDataSource ds = new BasicDataSource()) {
            // parse the command line arguments
            CommandLine line = parser.parse(options,
                                            args);

            ds.setDriverClassName(line.getOptionValue("driver"));
            ds.setUrl(line.getOptionValue("url"));
            ds.setUsername(line.getOptionValue("user"));
            ds.setPassword(line.getOptionValue("password"));
            ds.setDefaultReadOnly(true);
//          Test connection
            ds.getConnection().close();

            context = new InitialContext();
            context.bind(DS_NAME,
                         ds);
            DataSetService dataSetService = new DataSetServiceImpl(dsName -> DS_NAME);
            XESExportServiceImpl service = new XESExportServiceImpl();
            service.setDataSetService(dataSetService);
            final XESProcessFilter.Builder filter = XESProcessFilter.builder();
            filter.withProcessId(line.getOptionValue("process"));
            if (line.hasOption("version")) {
                filter.withProcessVersion(line.getOptionValue("version"));
            }
            if (line.hasOption("status")) {
                List<Integer> status = new ArrayList<>();
                for (String statusLine : line.getOptionValues("status")) {
                    try {
                        final Integer pStatus = Integer.valueOf(statusLine);
                        //only add valid status
                        if (pStatus >= 0 && pStatus <= 4) {
                            status.add(pStatus);
                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid process status number for input: " + statusLine + ", valid status are number between 0 and 4.");
                    }
                }
                if (status.isEmpty() == false) {
                    filter.withStatus(status);
                }
            }
            if (line.hasOption("logtype")) {
                filter.withNodeInstanceLogType(Integer.valueOf(line.getOptionValue("logtype")));
            }
            if (line.hasOption("nodetypes")) {
                filter.withAllNodeTypes();
            }

            final String xml = service.export(filter.build());
            if (line.hasOption("file")) {
                FileUtils.write(new File(line.getOptionValue("file")),
                                xml);
            } else {
                System.out.println(xml);
            }
        } catch (ParseException exp) {
            System.err.println("Parsing options failed. Reason: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("xes",
                                options,
                                true);
            System.exit(-1);
        } catch (Exception ex) {
            System.err.println("Failed to execute export due to: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException ne) {
                }
            }
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(Option.builder("user").argName("user").required().hasArg().desc("Database username").build());
        options.addOption(Option.builder("password").argName("password").hasArg().desc("Database password").build());
        options.addOption(Option.builder("url").argName("url").required().hasArg().desc("Database url").build());
        options.addOption(Option.builder("driver").argName("driver").required().hasArg().desc("Database driver").build());
        options.addOption(Option.builder("file").argName("file").hasArg().desc("File name to save result XES").build());
        options.addOption(Option.builder("process").argName("process").required().hasArg().desc("Process Id to export").build());
        options.addOption(Option.builder("version").argName("version").hasArg().desc("Process version to export").build());
        options.addOption(Option.builder("status").argName("status").numberOfArgs(4).desc("Process status to export").build());
        options.addOption(Option.builder("nodetypes").argName("a").desc("Export all node type. Default will only export relevant activities.").build());
        options.addOption(Option.builder("logtype").argName("logtype").hasArg().desc("Use 0 for node entered events or 1 for exit events. Default will export all types.").build());
        return options;
    }
}
