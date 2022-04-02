package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
            //    ", team=" + team + -> 양방향 연관관계는 toString에 쓰면 큰일남 -> 무한으로 서로가 서로를 참조하기때문.
                '}';
    }
}
