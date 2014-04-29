package com.maxxton.jmx.client;

import java.net.MalformedURLException;

import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;
import org.springframework.stereotype.Component;

/**
 * A proxy class connecting to the JMX server
 * 
 * @author R. Sonke
 * 
 */
@Component
public class JMXClientProxy extends MBeanServerConnectionFactoryBean
{
  private Log log = LogFactory.getLog(JMXClientProxy.class);
  
  @Override
  @Value("#{ systemProperties['jmx.url'] }")
  public void setServiceUrl(String url) throws MalformedURLException
  {
    super.setServiceUrl(url);
  }

  public String getValue(String type, String attribute, String composite)
  {
    try
    {
      Object value = getObject().getAttribute(new ObjectName(type), attribute);

      if (value instanceof CompositeData)
        return ((CompositeData) value).get(composite).toString();
      else
        return value.toString();

    } catch (Exception e)
    {
      log.error(e);
      e.printStackTrace();
      return null;
    }
  }
}
