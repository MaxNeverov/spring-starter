package com.mndev.spring.database.repository;

import com.mndev.spring.database.entity.Role;
import com.mndev.spring.database.entity.User;
import com.mndev.spring.dto.PersonalInfo;
import com.mndev.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}
