package com.example.addressbooksystem.controller;

import com.example.addressbooksystem.dto.AddressBookDto;
import com.example.addressbooksystem.dto.ResponseDto;
import com.example.addressbooksystem.model.Address;
import com.example.addressbooksystem.service.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressBookController {
    @Autowired
    IAddressBookService service;

    @GetMapping(value = {"", "/", "/home"})
    public String Welcomeaddressbook(@RequestParam(value = "name", defaultValue = "Swatika") String name) {
        return "welcome to address book app";
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> addUserData(@Valid @RequestBody AddressBookDto addressBookData) {
        Address response = service.saveDatadto(addressBookData);
        ResponseDto responseDTO = new ResponseDto("Data Added Successfully", (Optional.ofNullable(response)));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);


    }

    @GetMapping("/get/{Id}")
    public ResponseEntity<ResponseDto> FindById(@PathVariable Long Id) {
        Optional<Address> response = service.FindById(Id);
        ResponseDto responseDto = new ResponseDto("***All Details of adrreessbook using Id***", response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<ResponseDto> findAllDetail() {
        List<Address> allEmp = service.findAll();
        ResponseDto responseDTO = new ResponseDto("** All Employee List ** ", allEmp);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{Id}")
    public ResponseEntity<ResponseDto> updateById(@PathVariable Long Id, @Valid @RequestBody AddressBookDto addressBookDTO) {
        Optional<Address> Details = Optional.ofNullable(service.editById(addressBookDTO, Id));
        ResponseDto respDTO = new ResponseDto("Data Update info", Details);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long Id) {
        service.deleteById(Id);
        ResponseDto reponseDTO = new ResponseDto("** Employee Data deleted successfully ** ", "Id:" + Id + " is deleted");
        return new ResponseEntity(reponseDTO, HttpStatus.ACCEPTED);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity <ResponseDto> getUserByemail(@PathVariable String email) {
        List<Address> addresslist = service.getAddressBookByemail(email);
        ResponseDto respDTO = new ResponseDto("*** Data by using email ***", addresslist);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
}