package es.automation.feeder.repository;

import es.automation.feeder.entity.TestEntity;
import es.automation.feeder.repository.mapper.CustomBeanPropertyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TestRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TestRepository(final JdbcTemplate jdbcTemplate, final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public TestEntity save(final TestEntity testEntity) {
        final String insertTestSql = "INSERT INTO test"
                + " (test_name, base_uri, port, relative_uri, http_method, body, test_validator_for_result, expected_http_status) "
                + "VALUES (:testName, :baseUri, :port, :relativeUri, :httpMethod, :body, :testValidatorForResult, :expectedHttpStatus)";

        final SqlParameterSource beanParamSource = new BeanPropertySqlParameterSource(testEntity);
        LOGGER.debug("saving entity: sql={}, TestDetails={}", insertTestSql, testEntity);
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final int affectedRowNumber = namedParameterJdbcTemplate.update(insertTestSql, beanParamSource, keyHolder,
                new String[] { "id" });


        testEntity.setId(keyHolder.getKey().longValue());

        return testEntity;
    }

    public void updateProcessedFlag(final TestEntity testEntity) {

    }

    public TestEntity retrieveTestById(final Long testId) {
        final String selectQuery = "SELECT id, test_name, base_uri, port, relative_uri, http_method, body, test_validator_for_result, expected_http_status, processed FROM test WHERE id = ?";

        List<TestEntity> testEntities = jdbcTemplate.query(selectQuery,
                CustomBeanPropertyRowMapper.newInstance(TestEntity.class), testId);

        if (!testEntities.isEmpty()) {
            return testEntities.get(0);
        }
        return null;
    }


}
