package com.windea.demo.cloudcollect.core.repository;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository extends JpaRepository<Collect, Long> {
}
