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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m From Member m";

            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ","+ member.getTeam().getName());
                //1번째 루프 - 회원1, 팀A( 프록시로 있다가 호출되어 sql문 전송됨.)
                //2번째 루프 - 회원2, 팀A(영속성 컨텍스트(1차캐시))
                //3번쩨 루프 - 회원3, 팀B( 프록시로 있다가 호출되어 sql문 전송됨.)
                //소속이 각자 다 다르면 N+1 문제 발생함. ==> 패치 조인으로 해결하자.

            }

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
