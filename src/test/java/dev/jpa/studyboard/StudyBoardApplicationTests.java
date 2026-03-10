package dev.jpa.studyboard;

import dev.jpa.studyboard.domain.user.User;
import dev.jpa.studyboard.domain.user.UserRepository;
import dev.jpa.studyboard.domain.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
class StudyBoardApplicationTests {

	@Autowired
	private EntityManagerFactory factory;

	@Test
	@DisplayName("사용자 생성")
	void createUser() {

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

	@Test
	@DisplayName("사용자 조회 테스트")
	void findUser() {

		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {

			transaction.begin();

			User user = new User("Minjung");
			manager.persist(user);

			User findUser = manager.find(User.class, user.getId());

			assertNotNull(findUser);
			assertEquals("Minjung", findUser.getUserName());

			transaction.commit();

		} catch (Exception e) {

			transaction.rollback();

		} finally {

			manager.close();
			factory.close();
		}
	}

}
