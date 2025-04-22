package com.mndev.spring.database.repository;

import com.mndev.spring.database.entity.QUser;
import com.mndev.spring.database.entity.Role;
import com.mndev.spring.database.entity.User;
import com.mndev.spring.database.querydsl.QPredicates;
import com.mndev.spring.dto.PersonalInfo;
import com.mndev.spring.dto.UserFilter;
import com.querydsl.core.types.Expression;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mndev.spring.database.entity.QUser.user;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

@Repository
@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    StringUtils stringUtils;

    private static final String FIND_BY_COMPANY_AND_ROLE =
        "SELECT firstname, lastname, birth_date " +
                "FROM users " +
                "WHERE company_id = ? " +
                "AND role = ?";

    private static final String UPDATE_COMPANY_AND_ROLE =
        "UPDATE users " +
                "SET company_id = ?, role = ? " +
                "WHERE id = ?";

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED =
        "UPDATE users " +
                "SET company_id = :companyId, role = :role " +
                "WHERE id = :id";




    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {

        //фильтр(ВСЕ ЮЗЕРЫ, которые соответствуют трем фильтрам )
        Predicate predicate = QPredicates.builder()
                //указанное значение входит в состав имени не зависимо от регистра
                .add(filter.getFirstname(), user.firstname::containsIgnoreCase)
                //указанное значение входит в состав фамилии не зависимо от регистра
                .add(filter.getLastname(), user.lastname::containsIgnoreCase)
                //идет до указанной даты
                .add(filter.getBirthDate(), user.birthDate::before)
                .build();
//
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birth_date").toLocalDate()
                ), companyId, role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        MapSqlParameterSource[] args = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}
