package com.cfm.AddressBook.repo;

import com.cfm.AddressBook.model.ExecTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiExecTimeRepository extends JpaRepository<ExecTime, String> {
}
