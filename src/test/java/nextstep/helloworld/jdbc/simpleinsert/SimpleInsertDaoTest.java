package nextstep.helloworld.jdbc.simpleinsert;

import static org.assertj.core.api.Assertions.assertThat;

import nextstep.helloworld.jdbc.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SimpleInsertDaoTest {
    @Autowired
    private SimpleInsertDao simpleInsertDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    @Test
    void insertWithMap() {
        Customer customer = new Customer("Leonor", "Watling");
        Customer newCustomer = simpleInsertDao.insertWithMap(customer);

        assertThat(newCustomer).isNotNull();
        assertThat(newCustomer.getId()).isNotNull();
    }

    @Test
    void insertWithBeanPropertySqlParameterSource() {
        Customer customer = new Customer("Leonor", "Watling");
        Customer newCustomer = simpleInsertDao.insertWithBeanPropertySqlParameterSource(customer);

        assertThat(newCustomer).isNotNull();
        assertThat(newCustomer.getId()).isNotNull();
    }
}