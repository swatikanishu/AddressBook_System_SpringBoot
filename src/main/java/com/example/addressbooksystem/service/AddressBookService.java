package com.example.addressbooksystem.service;

import com.example.addressbooksystem.dto.AddressBookDto;
import com.example.addressbooksystem.exception.AddressBookException;
import com.example.addressbooksystem.model.Address;
import com.example.addressbooksystem.repo.Repo;
import com.example.addressbooksystem.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements IAddressBookService {
    @Autowired
    Repo repository;
    @Autowired
    TokenUtil tokenUtil;


    @Override
    public Address saveData(Address addressbookData) {
        repository.save(addressbookData);
        return addressbookData;
    }

    @Override
    public Address saveDatadto(AddressBookDto addressBookData) {
        Address newdata = new Address(addressBookData);
        repository.save(newdata);
        return newdata;

    }

    @Override
    public Optional<Address> FindById(Long Id) {
        return repository.findById(Id);
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public Address editById(AddressBookDto dtomodel, Long Id) {
        Address editdata = repository.findById(Id).orElse(null);
        if (editdata != null) {
            editdata.setFullName(dtomodel.getFullName());
            editdata.setPhoneNumber(dtomodel.getPhoneNumber());
            editdata.setEmail(dtomodel.getEmail());
            editdata.setAddress(dtomodel.getAddress());
            editdata.setCity(dtomodel.getCity());
            editdata.setState(dtomodel.getState());
            editdata.setZipcode(dtomodel.getZipcode());
            return
                    repository.save(editdata);
        } else
            throw new AddressBookException("Id:" + Id + " is not present ");

    }


    @Override
    public void deleteById(Long Id) {

        Optional<Address> findById = repository.findById(Id);
        if (findById.isPresent()) {
            repository.deleteById(Id);
        } else throw new AddressBookException("Id:" + Id + " not present");
    }

    @Override
    public List<Address> getAddressBookByemail(String email) {
        List<Address> addressList= repository.findAddressBookByEmail(email);
        if(addressList.isEmpty())
        {
           throw new AddressBookException("email address is not valid") ;
        }
        else
            return addressList;
    }

    @Override
    public List<Address> getAddressBookBycity(String city) {
        List<Address>addressList=repository.findAddressBookByCity(city);
        if(addressList.isEmpty())
        {
            throw new AddressBookException("city name is not valid");
        }
        else
                 return addressList;
    }

    @Override
    public String addRecord(AddressBookDto addressDto) throws AddressBookException {
        Address addressBook =new Address(addressDto);
        repository.save(addressBook);
        String token = tokenUtil.createToken(addressBook.getId());
        return token;
    }

    @Override
    public List<Address> getRecordByToken(String token) {
        Long Id = tokenUtil.decodeToken(token);
        Optional<Address> isDataPresent = repository.findById(Id);
        if (isDataPresent.isPresent()) {
            List<Address> listData = repository.findAll();
            return listData;
        } else {
            System.out.println("exception....");
            return null;
        }
    }
        @Override
        public Optional<Address> getUserRecordByToken(String token) {
            Long Userid = tokenUtil.decodeToken(token);
            Optional<Address> existingData = repository.findById(Userid);
            if(existingData.isPresent()){
                return existingData;
            }else
                throw new AddressBookException("Invalid Token");
        }

    }

