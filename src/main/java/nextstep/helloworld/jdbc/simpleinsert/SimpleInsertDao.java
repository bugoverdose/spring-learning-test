package nextstep.helloworld.jdbc.simpleinsert;

import java.util.HashMap;
import java.util.Map;
import nextstep.helloworld.jdbc.Customer;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SimpleInsertDao {
    private SimpleJdbcInsert insertActor;

    public SimpleInsertDao(DataSource dataSource) {
        System.out.println(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("customers")
                .usingGeneratedKeyColumns("id");
    }

    /**
     * Map + insertActor.executeAndReturnKey id를 포함한 Customer 객체를 반환하세요
     */
    public Customer insertWithMap(Customer customer) {
        Map<String, String> paramMap = new HashMap<>(){{
            put("first_name", customer.getFirstName());
            put("last_name", customer.getLastName());
        }};
        long id = insertActor.executeAndReturnKey(paramMap).longValue();
        return new Customer(id,
                customer.getFirstName(),
                customer.getLastName());
    }

    /**
     * SqlParameterSource + insertActor.executeAndReturnKey id를 포함한 Customer 객체를 반환하세요
     */
    public Customer insertWithBeanPropertySqlParameterSource(Customer customer) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(customer);
        long id = insertActor.executeAndReturnKey(paramSource).longValue();
        return new Customer(id,
                customer.getFirstName(),
                customer.getLastName());
    }
}
