package com.nathanieldoe.santa.db;

import com.nathanieldoe.santa.model.Exclusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ExclusionRepository extends JpaRepository<Exclusion, Long> {

}
