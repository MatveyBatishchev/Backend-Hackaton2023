package application;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.hackaton.backend")
@EnableJpaRepositories(basePackages = "ru.hackaton.backend.repositories", repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@EntityScan("ru.hackaton.backend.models.domain")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
