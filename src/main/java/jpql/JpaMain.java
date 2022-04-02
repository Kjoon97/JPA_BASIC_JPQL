package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("철수");
            member.setAge(10);
            em.persist(member);
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            Query query2 = em.createQuery("select m.username, m.age from Member m");

            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username","철수")
                    .getSingleResult();
            System.out.println("singleResult = " + result.getUsername());

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
