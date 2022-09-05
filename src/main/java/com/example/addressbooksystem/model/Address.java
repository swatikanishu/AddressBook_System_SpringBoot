package com.example.addressbooksystem.model;

import com.example.addressbooksystem.dto.AddressBookDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
    @Data
    @NoArgsConstructor
    @Table(name = "address_book")
    public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id")
        private Long Id;
        String fullName;
        String phoneNumber;

        String address;
        String city;
        String state;
        String zipcode;
    @ElementCollection
    @CollectionTable(name="email_address",joinColumns=@JoinColumn(name="id"))
    @Column(name="email")
    private List<String> email;

        public Address(AddressBookDto dto) {
            this.fullName = dto.getFullName();
            this.phoneNumber = dto.getPhoneNumber();
            this.email = dto.getEmail();
            this.address = dto.getAddress();
            this.city = dto.getCity();
            this.state = dto.getState();
            this.zipcode = dto.getZipcode();
        }
    }