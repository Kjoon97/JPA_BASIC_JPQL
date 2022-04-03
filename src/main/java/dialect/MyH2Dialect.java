package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {
    //생성자에서 함수 등록하면됨.
    public MyH2Dialect() {  //예시 group_concat이라는 내가 만든 함수 등록. -> H2Dialect 클래스 까봐서 거기에서 어떤식으로 함수 등록하는지 참고하기.
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
