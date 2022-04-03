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
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);

            //flush 될 때(DB에 반영될때)는 커밋하거나 직접 쿼리를 호출할 때.
            //벌크 연산을 통해 모든 회원의 나이를 20살로 맞추겠다.
            int resultCount = em.createQuery("update Member m set m.age=20")   //업데이트 된 것들 개수 반환
                    .executeUpdate();

            //벌크 연산을 위해 초기화하고 다시 불러와야
            em.clear();
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getAge());

            //벌크 연산이 디비에는 반영이되어 회원 나이가 20살로 조회 되지만 영속성 컨텍스트에서는 반영 안되므로 다음과같은 print에서는 0살로 출력된다. 그래서 em.clear해줘야함.
            System.out.println("member1.age" + member1.getAge());
            System.out.println("member2.age" + member2.getAge());
            System.out.println("member3.age" + member3.getAge());

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
