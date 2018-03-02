package es.automation.batch.repository;

import es.automation.feeder.entity.TestEntity;
import es.automation.feeder.repository.mapper.CustomBeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutomationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AutomationRepository(final JdbcTemplate jdbcTemplate
                            , final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TestEntity> findAllTestsToBeProcessed() {
        final String selectQuery = "SELECT id, test_name, base_uri, port, relative_uri, http_method, body, test_validator_for_result, expected_http_status, processed FROM test WHERE processed = false";

        List<TestEntity> testEntities = jdbcTemplate.query(selectQuery,
                CustomBeanPropertyRowMapper.newInstance(TestEntity.class));

        return testEntities;
    }
}
