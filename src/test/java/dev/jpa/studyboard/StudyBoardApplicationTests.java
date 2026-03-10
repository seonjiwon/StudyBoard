package dev.jpa.studyboard;

import dev.jpa.studyboard.domain.user.User;
import dev.jpa.studyboard.domain.user.UserRepository;
import dev.jpa.studyboard.domain.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudyBoardApplicationTests {

	@Test
	void createUser() {

		EntityManagerFactory factory
				= Persistence.createEntityManagerFactory("hello-jpa");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();


		UserRepository userRepository = new UserRepository(manager);
		UserService userService = new UserService(userRepository);

		try {

			transaction.begin();

			// given
			String name = "Minjung";

			// when
			int id = userService.createUser(name);
			User user = userService.findUser(id);

			// then
			assertEquals(name, user.getUserName());

			transaction.commit();

		} catch (Exception e) {

			transaction.rollback();
			throw e;

		} finally {

			manager.close();
			factory.close();

		}
	}

}
