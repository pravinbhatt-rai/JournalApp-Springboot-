package com.pravinbhattarai.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(JournalApplication.class, args);

		Environment env = context.getEnvironment();


	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory myFactory){
		return  new MongoTransactionManager(myFactory);
	}
}