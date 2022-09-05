package com.example.addressbooksystem.service;

import com.example.addressbooksystem.dto.AddressBookDto;
import com.example.addressbooksystem.dto.ResponseDto;
import com.example.addressbooksystem.model.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressBookService {
    Address saveData(Address addressbookData);

    Address saveDatadto(AddressBookDto addressBookData);

    Optional<Address> FindById(Long Id);

    List<Address> findAll();

    Address editById(AddressBookDto addressBookDTO, Long Id);

    void deleteById(Long Id);

    List<Address> getAddressBookByemail(String email);
}