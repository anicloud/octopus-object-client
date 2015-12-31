package com.ani.octopus.service.agent.core.http;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.RestTemplate;

/**
 * This class provides the singleton http RestTemplate instance based on <strong>Spring RestTemplate</strong>.<br><br>
 * Created by zhaoyu on 15-11-11.
 */
public class RestTemplateFactory {
    public final static String ACCESS_TOKEN = "access_token";
    public final static String CODE = "code";
    public final static String REFRESH_TOKEN = "refresh_token";

    private RestTemplate restTemplate;

    public RestTemplateFactory() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get the singleton instance of Spring RestTemplate.
     * @param classes for the XStream annotation
     * @return Spring RestTemplate instance
     */
    public RestTemplate getRestTemplate(Class[] classes) {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setStreamDriver(new StaxDriver());
        xStreamMarshaller.setAnnotatedClasses(classes);

        MarshallingHttpMessageConverter marshallingHttpMessageConverter =
                new MarshallingHttpMessageConverter();
        marshallingHttpMessageConverter.setMarshaller(xStreamMarshaller);
        marshallingHttpMessageConverter.setUnmarshaller(xStreamMarshaller);

        restTemplate.getMessageConverters().add(marshallingHttpMessageConverter);
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        // set the data style
        // jackson2HttpMessageConverter.setObjectMapper(new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        restTemplate.getMessageConverters().add(jackson2HttpMessageConverter);

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        restTemplate.getMessageConverters().add(stringHttpMessageConverter);

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        restTemplate.getMessageConverters().add(formHttpMessageConverter);

        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        restTemplate.getMessageConverters().add(byteArrayHttpMessageConverter);

        SourceHttpMessageConverter sourceHttpMessageConverter = new SourceHttpMessageConverter();
        restTemplate.getMessageConverters().add(sourceHttpMessageConverter);
        return restTemplate;
    }
}
