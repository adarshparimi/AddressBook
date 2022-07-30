package com.cfm.AddressBook.service;

import com.cfm.AddressBook.exceptions.ResourceNotFoundException;
import com.cfm.AddressBook.model.Addresses;
import com.cfm.AddressBook.repo.AddressRepository;
import com.cfm.AddressBook.repo.ElasticSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import java.util.List;

import static co.elastic.clients.elasticsearch.snapshot.SnapshotSort.Repository;

@Service
@Slf4j
public class DefaultAddressService implements AddressService{

    AddressRepository addressRepository;
    @Autowired
    ElasticSearchRepository elasticSearchRepository;

    public DefaultAddressService(AddressRepository addressRepository, ElasticSearchRepository elasticSearchRepository){
        this.addressRepository = addressRepository;
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public String addAddress(Addresses address) {
        if(!address.getEmail().contains("@")){
            return "Email id not in correct format";
        }
        if(address.getName().isEmpty()){
            return "Name is a required can not be left empty";
        }
        if(address.getMobile() == 0){
            return "Mobile number is a required field can not be left empty";
        }
        try {
            addressRepository.save(address);
            elasticSearchRepository.createAddress(address);
            log.info("Saved address: {}",address);
            return "Address details saved successfully";
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Address details provided");
        }
    }

    @Override
    public List<Addresses> getAddress() {
        List<Addresses> result = (List<Addresses>) addressRepository.findAll();

        if(result.isEmpty()){
            throw new ResourceNotFoundException("No Addresses available");
        }else{
            log.info("Address retreived: {}",result);
            return result;
        }
    }

    @Override
    public String updateAddress(long add_id, Addresses address) {
        List<Addresses> addressesList = addressRepository.selectAddresses(add_id);
        if(addressesList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Address details");

        } else{
            long update_add_id = addressesList.get(0).getAddId();
            log.info("updating update_id: {}",update_add_id);
            address.setAddId(update_add_id);
            log.info("updating address: {}",address);
            Addresses updated;
            try{
                updated = addressRepository.save(address);
                log.info("Updated address: {}", address);
                return "Address updated successfully";
            }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Address details are invalid");
            }
        }
    }

    @Override
    @Test
    @Transactional
    public String deleteAddress(long id) {
        int deletedRecords;
        deletedRecords = addressRepository.deleteAddress(id);
        if(deletedRecords == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Address details invalid");
        else {
            log.info("Deleted Records : {}",deletedRecords);
            return "Address deleted successfully";
        }
    }


    @Override
    public Page<Addresses> findMenuItemPaginatedAndSorted(String page, String size, String sortBy, String sortOrder, String fields){
        Page<Addresses> foundAddress;
        foundAddress = elasticSearchRepository.findAddressPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
        if(foundAddress.isEmpty()) {
            throw new ResourceNotFoundException("No Addresses available");
        }
        else {
            log.info("The retrieved Addesses: {}",foundAddress);
            return foundAddress;
        }
    }
}
