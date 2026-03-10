package dev.jpa.studyboard.domain.comment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionTemplate {
    private final EntityManagerFactory emf;

    public <T> T execute(Function<EntityManager, T> action) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            try {
                T result = action.apply(em);
                tx.commit();
                return result;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
