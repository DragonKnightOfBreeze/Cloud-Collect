package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
