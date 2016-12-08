package pl.aptewicz.ftthchecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import pl.aptewicz.ftthchecker.contorller.ControllerConfigurationPoint;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = {ControllerConfigurationPoint.class})
public class WebConfig extends WebMvcConfigurerAdapter {

	private static final String TEMPLATE_RESOLVER_PREFIX = "/WEB-INF/views/";

	private static final String TEMPLATE_RESOLVER_SUFFIX = ".html";

	private static final String TEMPLATE_RESOLVER_TEMPLATE_MODE = "HTML5";

	private static final String VIEW_RESOLVER_CHARACTER_ENCODING = "UTF-8";

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix(TEMPLATE_RESOLVER_PREFIX);
		templateResolver.setSuffix(TEMPLATE_RESOLVER_SUFFIX);
		templateResolver.setTemplateMode(TEMPLATE_RESOLVER_TEMPLATE_MODE);
		templateResolver.setCharacterEncoding(VIEW_RESOLVER_CHARACTER_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setCharacterEncoding(VIEW_RESOLVER_CHARACTER_ENCODING);
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasename("messages");
		return messageSource;
	}
}
