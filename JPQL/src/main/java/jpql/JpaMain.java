package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction(); //jpa 변경은 모두 트랜잭션 안에서!
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("chan");
            member.setAge(27);
            em.persist(member);

            em.flush();
            em.clear();

            // 기본
            List<Member> result1 = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            // 값 타입이 다를때
            List<MemberDTO> result2 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();


            // 페이징
            List<Member> result3 = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();


            // 조인
            String query = "select m from Member m join m.team t";
            em.createQuery(query,Member.class)
                    .getResultList();

            




            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}