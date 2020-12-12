package com.example.test;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
@EnableBatchProcessing
public class SpringSecurityApplication implements CommandLineRunner{

    @Autowired
    DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	 @Override
    public void run(String... args) throws Exception {
//        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }
	 
//	@Bean
//	public FlywayMigrationStrategy cleanMigrateStrategy() {
//	    return flyway -> {
//	        flyway.repair();
//	        flyway.migrate();
//	    };
//	}
}
