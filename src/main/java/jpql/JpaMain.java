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

//            String query = "select m From Member m join fetch m.team"; //->member를 조회하는데 join할때 team을 한번에 다 가져오는 것.(지연 로딩을 해도 패치 조인이 더 우선으로 먼저 세팅됨.)
//
//            List<Member> resultList = em.createQuery(query, Member.class).getResultList();
//            for (Member member : resultList) {
//                System.out.println("member = " + member.getUsername() + ","+ member.getTeam().getName());
//                //이때 member.getTeam()에서 team은 프록시가 아님.( fetch조인을 통해서 이미 member랑 팀을 조인해서 한꺼번에 다 select함 이미 실제 team이 영속성 컨텍스트에 다 올라가있음.)
//            }
            //일대 다 패치 조인과 distinct
            String query = "select distinct t From Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            System.out.println("resultList = " + resultList.size());
            for (Team team : resultList) {
                System.out.println("team = " + team.getName() +"|members: "+ team.getMembers().size());
                for( Member member : team.getMembers()){
                    System.out.println("member = " + member);
                }
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
