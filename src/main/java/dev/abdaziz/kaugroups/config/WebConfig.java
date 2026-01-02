package dev.abdaziz.kaugroups.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final GenderCheckInterceptor genderCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(genderCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/auth/**",
                    "/oauth2/**",
                    "/login/**",
                    "/users/me/gender"
                );
    }
}

