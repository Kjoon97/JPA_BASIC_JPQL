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

            //프로젝션 여러 값 조회하는 법 1.Query 타입으로 조회
            List resultList = em.createQuery("select m.username,m.age from Member m").getResultList();
            Object o = resultList.get(0);
            Object[] QueryResult = (Object[]) o;
            System.out.println("QueryResult = " + QueryResult[0]);
            System.out.println("QueryResult = " + QueryResult[1]);

            //프로젝션 여러 값 조회하는 법 2. Object[] 타입으로 조회
            List<Object[]> resultList1 = em.createQuery("select m.username,m.age from Member m").getResultList();
            Object[] typeresult = resultList1.get(0);
            System.out.println("typeresult = " + typeresult[0]);
            System.out.println("typeresult = " + typeresult[1]);


            //프로젝션 여러 값 조회하는 법 3. (new 명령어로 조회하기)  ->이 방법이 젤 깔끔함.
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
