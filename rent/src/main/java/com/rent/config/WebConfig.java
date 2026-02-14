package com.rent.config;

import com.rent.Interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    /**
     * 跨域配置
     *
     * @param registry 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(5000);
    }

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    /**
     * 拦截器注册
     *
     * @param registry 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/file/**", // 文件上传/访问（不鉴权）
                        "/service-center/file/**", // 兼容带context-path的文件接口（不鉴权）
                        "/swagger-ui/**", // Swagger UI静态资源
                        "/v3/api-docs/**", // API文档JSON数据
                        "/swagger-resources/**", // Swagger资源列表
                        "/swagger-ui/index.html", // Swagger UI首页
                        "/webjars/**");
    }
}
