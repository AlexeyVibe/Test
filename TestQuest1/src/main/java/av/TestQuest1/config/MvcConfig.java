package av.TestQuest1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file///E:/ideaprojects/TestQuest1/src/main/resources/static/";
        registry.addResourceHandler("/static/**")
                .addResourceLocations(path);
    }

}
