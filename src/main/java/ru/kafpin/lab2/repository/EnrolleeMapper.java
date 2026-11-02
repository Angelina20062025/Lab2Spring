package ru.kafpin.lab2.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.kafpin.lab2.Address;
import ru.kafpin.lab2.Enrollee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrolleeMapper implements RowMapper<Enrollee> {
    @Override
    public Enrollee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enrollee enrollee = new Enrollee();
        enrollee.setId(rs.getLong("enrollee_id"));
        enrollee.setLastName(rs.getString("last_name"));
        enrollee.setFirstName(rs.getString("first_name"));
        enrollee.setMiddleName(rs.getString("middle_name"));
        enrollee.setGender(rs.getString("gender"));
        enrollee.setNationality(rs.getString("nationality"));
        enrollee.setBirthDate(rs.getObject("birth_date", java.time.LocalDate.class));
        enrollee.setPassingScore(rs.getInt("passing_score"));

        long addressId = rs.getLong("address_id");
        if (!rs.wasNull()) {
            Address address = new Address();
            address.setId(addressId);
            address.setZipCode(rs.getString("zip_code"));
            address.setCountry(rs.getString("country"));
            address.setCity(rs.getString("city"));
            address.setStreet(rs.getString("street"));
            address.setHouse(rs.getString("house"));
            address.setApartment(rs.getString("apartment"));
            enrollee.setAddress(address);
        }
        return enrollee;
    }
}
