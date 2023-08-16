package messagingApp.inboxApp;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import messagingApp.inboxApp.email.EmailService;
import messagingApp.inboxApp.emailslist.EmailsList;
import messagingApp.inboxApp.emailslist.EmailsListPrimaryKey;
import messagingApp.inboxApp.emailslist.EmailsListRepository;

@SpringBootApplication
@RestController
public class MessagingApp {

	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(MessagingApp.class, args);
	}

	@RequestMapping("/user")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println(principal);
		return principal.getAttribute("name");
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void initializeData() {

		for (int i = 0; i < 10; i++) {

			emailService.sendEmail("akumbhare47", "akumbhare47", "Test " + i, "Body " + i);
		}
		

	}

}
