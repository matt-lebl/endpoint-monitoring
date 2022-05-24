package ca.lebl.monitoring;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MonitoringApplication implements CommandLineRunner {

	private UserRepository userRepository;

	public MonitoringApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Seed database with some users
		User applifting = new User(
			"Applifting",
			"info@applifting.cz",
			"93f39e2f-80de-4033-99ee-249d92736a25"
		);

		User batman = new User(
			"Batman",
			"batman@example.com",
			"dcb20f8a-5657-4f1b-9f7f-ce65739b359e"
		);

		User matt = new User(
			"Matt",
			"matt.lebl@applifting.cz",
			"hello"
		);

		userRepository.saveAll(List.of(applifting, batman, matt));
	}
}
