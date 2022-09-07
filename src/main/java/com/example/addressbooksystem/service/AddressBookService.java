package com.example.addressbooksystem.service;

import com.example.addressbooksystem.dto.AddressBookDto;
import com.example.addressbooksystem.exception.AddressBookException;
import com.example.addressbooksystem.model.Address;
import com.example.addressbooksystem.repo.Repo;
import com.example.addressbooksystem.util.EmailSenderService;
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
    @Autowired
    EmailSenderService emailSenderService;


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
            String userData = "After Editing the Data: \n"+editdata.getFullName()+"\n"+editdata.getAddress()+"\n"
                    +editdata.getCity()+"\n"+editdata.getState()+"\n"+editdata.getZipcode()+"\n"+
                    editdata.getPhoneNumber()+"\n"+editdata.getEmail();
            emailSenderService.sendEmail(editdata.getEmail(),"Edited Your Details", userData);

            return
                    repository.save(editdata);
        } else
            throw new AddressBookException("Id:" + Id + " is not present ");

    }


    @Override
    public Optional<Address> deleteById(Long Id) {

        Optional<Address> findById = repository.findById(Id);
        if (findById.isPresent()) {
            repository.deleteById(Id);
            emailSenderService.sendEmail(findById.get().getEmail(), "Deleted Data", "data is deleted");

        } else throw new AddressBookException("Id:" + Id + " not present");
        return findById;
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
        String userData = "Your Details: \n"+addressBook.getFullName()+"\n"+addressBook.getAddress()+"\n"
                +addressBook.getCity()+"\n"+addressBook.getState()+"\n"+addressBook.getZipcode()+"\n"+
                addressBook.getPhoneNumber()+"\n"+addressBook.getEmail();
        emailSenderService.sendEmail(addressBook.getEmail(),"Added Your Details", userData);
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
            throw new AddressBookException("exception....");

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
