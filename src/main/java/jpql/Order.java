package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="ORDERS")  //Order라는 데이터베이스 질의문이 있기 때문에 오류 뜬다 그래서 테이블 명을 ORDERS라고 해줌.
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID")
    private Product product;
}
