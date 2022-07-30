package com.cfm.AddressBook.repo;

import com.cfm.AddressBook.model.Addresses;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchRepository{

    public Addresses createAddress(Addresses address);
    Page<Addresses> findAddressPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);
}
