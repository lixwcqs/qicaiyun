package com.cqs.qicaiyun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}


	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("Api Documentation").description("Api Documentation")
				.version("1.0").build();
		return apiInfo;
	}

//	@Bean
//	public Docket createRestApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.cqs"))
//				.paths(PathSelectors.any())
//				.build();
//	}
//	private ApiInfo apiInfo() {
//		//Contact contact = new Contact("lixwcqs","","lixwcqs@163.com");
//		return new ApiInfoBuilder()
//				.title("Spring Boot中使用Swagger2构建RESTful APIs")
//				.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
//				.termsOfServiceUrl("http://blog.didispace.com/")
//				.contact("程序猿DD")
//				.version("1.0")
//				.build();
//	}
}