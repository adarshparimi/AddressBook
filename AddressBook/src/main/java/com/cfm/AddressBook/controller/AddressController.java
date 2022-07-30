package com.cfm.AddressBook.controller;

import com.cfm.AddressBook.model.AddressUpdate;
import com.cfm.AddressBook.model.Addresses;
import com.cfm.AddressBook.service.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AddressController {

    private final AddressService addressService;
    private String message;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/addAddress")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public String addAddress(@RequestBody Addresses address){
        return addressService.addAddress(address);
    }

    @PostMapping("/getAddress")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public List<Addresses> getAddress(){
        return addressService.getAddress();
    }

    @PostMapping("/updateAddress")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public String updateAddress(@RequestBody AddressUpdate addressUpdate){
        long add_id = addressUpdate.getAdd_id();
        Addresses addresses = addressUpdate.getAddresses();

        return addressService.updateAddress(add_id, addresses);
    }

    @PostMapping("/deleteAddress")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public String deleteAddress(@RequestBody long id){
        return addressService.deleteAddress(id);
    }


    @PostMapping("/findAddress")
    @ApiOperation(value = "This operation updates a reservation")
    @ApiResponses(value ={
            @ApiResponse(code = 200,message = "Ok"),
            @ApiResponse(code = 201, message = "Added"),
            @ApiResponse(code = 500,message = "Internal Server Error")
    })
    public Page<Addresses> findMenuItemPaginatedAndSorted(@RequestParam("page") final String page,
                                                          @RequestParam("size") final String size,
                                                          @RequestParam(name = "sortBy",required = false) final String sortBy,
                                                          @RequestParam(name = "sortOrder",required = false, defaultValue = "ASC") final String sortOrder,
                                                          @RequestParam(name = "fields", required = false) final String fields){
        return addressService.findMenuItemPaginatedAndSorted(page, size, sortBy, sortOrder, fields);
    }
}
