package ru.kafpin.lab2.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.kafpin.lab2.Enrollee;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrolleeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnrolleeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String JOIN_QUERY = """
        SELECT e.id AS enrollee_id, e.last_name, e.first_name, e.middle_name, e.gender, 
               e.nationality, e.birth_date, e.passing_score, e.address_id,
               a.zip_code, a.country, a.city, a.street, a.house, a.apartment
        FROM enrollees e
        LEFT JOIN addresses a ON e.address_id = a.id
        """;

    public List<Enrollee> findAll() {
        return jdbcTemplate.query(JOIN_QUERY, new EnrolleeMapper());
    }

    public Optional<Enrollee> findById(Long id) {
        String sql = JOIN_QUERY + " WHERE e.id = ?";
        List<Enrollee> list = jdbcTemplate.query(sql, new EnrolleeMapper(), id);
        return list.stream().findFirst();
    }

    public List<Enrollee> findByLastName(String lastName) {
        String sql = JOIN_QUERY + " WHERE LOWER(e.last_name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new EnrolleeMapper(), "%" + lastName + "%");
    }

    public List<Enrollee> findByAddressCity(String city) {
        String sql = JOIN_QUERY + " WHERE LOWER(a.city) = LOWER(?)";
        return jdbcTemplate.query(sql, new EnrolleeMapper(), city);
    }

    public List<Enrollee> findByPassingScoreGreaterThanEqual(int score) {
        String sql = JOIN_QUERY + " WHERE e.passing_score >= ?";
        return jdbcTemplate.query(sql, new EnrolleeMapper(), score);
    }

    public Enrollee save(Enrollee enrollee) {
        Long addressId = null;

        if (enrollee.getAddress() != null) {
            String addressSql = "INSERT INTO addresses(zip_code, country, city, street, house, apartment) VALUES (?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(addressSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, enrollee.getAddress().getZipCode());
                ps.setString(2, enrollee.getAddress().getCountry());
                ps.setString(3, enrollee.getAddress().getCity());
                ps.setString(4, enrollee.getAddress().getStreet());
                ps.setString(5, enrollee.getAddress().getHouse());
                ps.setString(6, enrollee.getAddress().getApartment());
                return ps;
            }, keyHolder);

            addressId = keyHolder.getKey().longValue();
            enrollee.getAddress().setId(addressId);
        }

        String enrolleeSql = "INSERT INTO enrollees(last_name, first_name, middle_name, gender, nationality, birth_date, passing_score, address_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder enrolleeKeyHolder = new GeneratedKeyHolder();
        Long finalAddressId = addressId;

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(enrolleeSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, enrollee.getLastName());
            ps.setString(2, enrollee.getFirstName());
            ps.setString(3, enrollee.getMiddleName());
            ps.setString(4, enrollee.getGender());
            ps.setString(5, enrollee.getNationality());
            ps.setObject(6, enrollee.getBirthDate());
            ps.setInt(7, enrollee.getPassingScore());
            if (finalAddressId != null) ps.setLong(8, finalAddressId);
            else ps.setNull(8, java.sql.Types.BIGINT);
            return ps;
        }, enrolleeKeyHolder);

        enrollee.setId(enrolleeKeyHolder.getKey().longValue());
        return enrollee;
    }

    public void update(Enrollee enrollee) {
        if (enrollee.getAddress() != null && enrollee.getAddress().getId() != null) {
            String addressSql = "UPDATE addresses SET zip_code=?, country=?, city=?, street=?, house=?, apartment=? WHERE id=?";
            jdbcTemplate.update(addressSql,
                    enrollee.getAddress().getZipCode(), enrollee.getAddress().getCountry(),
                    enrollee.getAddress().getCity(), enrollee.getAddress().getStreet(),
                    enrollee.getAddress().getHouse(), enrollee.getAddress().getApartment(),
                    enrollee.getAddress().getId());
        }

        String enrolleeSql = "UPDATE enrollees SET last_name=?, first_name=?, middle_name=?, gender=?, nationality=?, birth_date=?, passing_score=? WHERE id=?";
        jdbcTemplate.update(enrolleeSql,
                enrollee.getLastName(), enrollee.getFirstName(), enrollee.getMiddleName(),
                enrollee.getGender(), enrollee.getNationality(), enrollee.getBirthDate(),
                enrollee.getPassingScore(), enrollee.getId());
    }

    public void deleteById(Long id) {
        Optional<Enrollee> enrollee = findById(id);
        if (enrollee.isPresent() && enrollee.get().getAddress() != null) {
            jdbcTemplate.update("DELETE FROM addresses WHERE id = ?", enrollee.get().getAddress().getId());
        }
        jdbcTemplate.update("DELETE FROM enrollees WHERE id = ?", id);
    }
}
