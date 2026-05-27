package com.example.aiserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final String uploadDir;
    private final String publicPrefix;

    public WebMvcConfig(
            @Value("${app.file.upload-dir}") String uploadDir,
            @Value("${app.file.public-prefix}") String publicPrefix
    ) {
        this.uploadDir = uploadDir;
        this.publicPrefix = publicPrefix;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Path.of(uploadDir).toAbsolutePath().toUri().toString();
        registry.addResourceHandler(publicPrefix + "/**")
                .addResourceLocations(location);
    }
}
