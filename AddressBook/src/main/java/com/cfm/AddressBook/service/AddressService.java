package com.cfm.AddressBook.service;

import com.cfm.AddressBook.model.Addresses;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {

    public String addAddress(Addresses address);

    public List<Addresses> getAddress();

    public String updateAddress(long add, Addresses address);

    public String deleteAddress(long id);

    public Page<Addresses> findMenuItemPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields);
}
