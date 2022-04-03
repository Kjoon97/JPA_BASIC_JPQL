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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.USER);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            //기본 case식(이런 것들 다 querydsl로 하기 편함)
            String query ="select "+
                    "case when m.age <=10 then '학생요금' "+
                    "     when m.age >=60 then '경로요금' "+
                    "     else '일반요금' "+
                    "end "+
                          "from Member m";
            //COALESCE - 하나씩 조회해서 null 아니면 반환
            String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m";

            //NULLIF : 사용자 이름이 관리자이면 null반환, 나머지는 본인의 이름 반환
            String query3 = "select NULLIF(m.username, '관리자') from Member m";
            List<String> resultList = em.createQuery(query3, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
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
