package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); //jpa 변경은 모두 트랜잭션 안에서!
        tx.begin();
        try {
//            Member member1 = new Member(1L, "chan");
//            Member member2 = new Member(2L, "yunha");
//
//            em.persist(member1);
//            em.persist(member2);
            Member member = em.find(Member.class,1L);
            member.setName("zz");
//


            //디비에 저장되는 시점
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}