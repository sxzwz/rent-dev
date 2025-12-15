package com.rent.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 1. 定义 JWT 安全方案名称（自定义，如 "bearerAuth"）
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 2. 基本信息配置（标题、版本、描述）
                .info(new Info()
                        .title("房屋租赁系统API")
                        .version("1.0")
                        .description("房屋租赁系统的后端接口文档（端口9091）"))
                // 3. 添加安全方案（JWT）
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)  // 认证类型：HTTP
                                .scheme("bearer")  // 认证方案：bearer
                                .bearerFormat("JWT")  // Token 格式：JWT
                                .in(SecurityScheme.In.HEADER)  // Token 位置：请求头
                                .name("Authorization")  // 请求头名称：Authorization
                        ))
                // 4. 全局应用安全方案（所有接口默认需要 Token）
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}