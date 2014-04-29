package com.maxxton.jmx.client;

import java.net.MalformedURLException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * A jmx client which can be used by the command line to fetch values from a
 * remote JMX bean.
 * 
 * @author R. Sonke
 * 
 */
@Configuration
@ComponentScan
public class SimpleJMXClient
{
  
  public static void main(String[] args) throws ParseException, MalformedURLException
  {
    Options options = new Options();

    OptionBuilder.withLongOpt("h");
    OptionBuilder.withDescription("Print this help");
    options.addOption(OptionBuilder.create("h"));
    
    OptionBuilder.withLongOpt("t");
    OptionBuilder.withDescription("Type of bean");
    OptionBuilder.isRequired();
    OptionBuilder.hasArg();
    options.addOption(OptionBuilder.create("t"));
    
    OptionBuilder.withLongOpt("a");
    OptionBuilder.withDescription("Attribute name");
    OptionBuilder.isRequired();
    OptionBuilder.hasArg();
    options.addOption(OptionBuilder.create("a"));
    
    OptionBuilder.withLongOpt("c");
    OptionBuilder.withDescription("Optional composite key");
    OptionBuilder.hasArg();
    options.addOption(OptionBuilder.create("c"));
        
    OptionBuilder.withLongOpt("u");
    OptionBuilder.withDescription("Remote URL JMX server");
    OptionBuilder.isRequired();
    OptionBuilder.hasArg();
    options.addOption(OptionBuilder.create("u"));
    
    CommandLineParser parser = new BasicParser();
    CommandLine cmd = parser.parse(options, args);
    
    if(cmd.hasOption("h"))
    {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("SimpleJMXClient", options);
      return;
    }
    
    String type = cmd.getOptionValue("t");
    String attribute = cmd.getOptionValue("a");
    String composite = cmd.getOptionValue("c");
    String url = cmd.getOptionValue("u");
    
    System.setProperty("jmx.url", url);

    SimpleJMXClient client = new SimpleJMXClient();
    
    // Print value to fetch it inside bash script
    System.out.println("JMXValue " + client.getJMXValue(url, type, attribute, composite));
  }

  private String getJMXValue(String url, String type, String attribute, String composite) throws MalformedURLException
  {
    ApplicationContext context = new AnnotationConfigApplicationContext(SimpleJMXClient.class);
    JMXClientProxy bean = context.getBean(JMXClientProxy.class);
    return bean.getValue(type, attribute, composite);
  }
}
