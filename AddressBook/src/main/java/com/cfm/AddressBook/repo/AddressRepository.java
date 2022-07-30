package com.cfm.AddressBook.repo;

import com.cfm.AddressBook.model.Addresses;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Addresses, String> {

    @Modifying
    @Query(value = "delete from addresses where add_id=:id", nativeQuery = true)
    int deleteAddress(@Param("id") long id);

    @Modifying
    @Query(value = "select * from addresses where add_id=:id", nativeQuery = true)
    List<Addresses> selectAddresses(@Param("id") long id);
}
