package com.example.addressbooksystem.repo;

import com.example.addressbooksystem.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface Repo extends JpaRepository<Address, Long> {
    @Query(value="SELECT * FROM address_book,email_address WHERE user_id = id AND email = :email", nativeQuery=true)

    List<Address> findAddressBookByEmail(String email);
    @Query(value="SELECT * FROM address_book WHERE user_id = user_id AND city = :city", nativeQuery=true)

    List<Address> findAddressBookByCity(String city);
}


