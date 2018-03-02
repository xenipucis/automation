package es.automation.feeder.repository;

import es.automation.feeder.entity.TestHeaderEntity;
import es.automation.feeder.repository.mapper.CustomBeanPropertyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestHeadersRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHeadersRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TestHeadersRepository(final JdbcTemplate jdbcTemplate, final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void saveRequestTestHeaders(final Long testId, final Map<String, String> testHeaders) {
        final List<Map<String, Object>> batchValues = new ArrayList<>(testHeaders.size());
        if (!testHeaders.isEmpty()) {
            final String insertTestHeadersSql = "INSERT INTO test_headers(test_id, key, value) values(:testId, :key, :value)";
            for (String key : testHeaders.keySet()) {

                batchValues.add(
                        new MapSqlParameterSource("testId", testId)
                                .addValue("key", key)
                                .addValue("value", testHeaders.get(key))
                                .getValues());
            }

            /*int[] updateCounts = */namedParameterJdbcTemplate.batchUpdate(insertTestHeadersSql,
                    batchValues.toArray(new Map[testHeaders.size()]));
        }
    }


    public List<TestHeaderEntity> retrieveRequestTestHeaders(final Long testId) {

        final String selectQuery = "SELECT test_id, key, value FROM test_headers WHERE test_id = ? AND is_request_header = true";

        return jdbcTemplate.query(selectQuery,
                CustomBeanPropertyRowMapper.newInstance(TestHeaderEntity.class), testId);
    }
}
