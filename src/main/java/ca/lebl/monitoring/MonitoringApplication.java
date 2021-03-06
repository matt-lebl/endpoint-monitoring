package ca.lebl.monitoring;

import ca.lebl.monitoring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class MonitoringApplication implements CommandLineRunner {

	private UserService userService;

	public MonitoringApplication(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Seed database with some users
		userService.createUserIfNotExists(
			"Applifting",
			"info@applifting.cz",
			"93f39e2f-80de-4033-99ee-249d92736a25"
		);

		userService.createUserIfNotExists(
			"Batman",
			"batman@example.com",
			"dcb20f8a-5657-4f1b-9f7f-ce65739b359e"
		);

		userService.createUserIfNotExists(
			"Matt",
			"matt.lebl@applifting.cz",
			"hello"
		);
	}
}
