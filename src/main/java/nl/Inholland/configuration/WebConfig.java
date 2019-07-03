package nl.Inholland.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://app.swaggerhub.com/apis/Plez/BankingAPI/1.4, https://inhollandbank-divqyq.firebaseapp.com" )
                .allowedMethods("PUT", "DELETE", "POST", "GET", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
}
