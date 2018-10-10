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
        options.addOption(OptionBuilder.withArgName("user").isRequired().hasArg().withDescription("Database username").create("user"));
        options.addOption(OptionBuilder.withArgName("password").isRequired().hasArg().withDescription("Database password").create("password"));
        options.addOption(OptionBuilder.withArgName("url").isRequired().hasArg().withDescription("Database url").create("url"));
        options.addOption(OptionBuilder.withArgName("driver").isRequired().hasArg().withDescription("Database driver").create("driver"));
        options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("File name to save result XES").create("file"));
        options.addOption(OptionBuilder.withArgName("process").isRequired().hasArg().withDescription("Process Id to export").create("process"));
        options.addOption(OptionBuilder.withArgName("version").hasArg().withDescription("Process version to export").create("version"));
        options.addOption(OptionBuilder.withArgName("status").hasArgs(4).withDescription("Process status to export").create("status"));
        return options;
    }
}
