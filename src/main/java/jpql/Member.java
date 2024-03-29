package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType Type;

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

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
