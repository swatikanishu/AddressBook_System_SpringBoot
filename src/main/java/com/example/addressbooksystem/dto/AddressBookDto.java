package com.example.addressbooksystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@NoArgsConstructor
public class AddressBookDto {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Employee name is invalid ")
    String fullName;
    @Pattern(regexp = "^[1-9]{2}[0-9]{10}$", message="Invalid Contact Number(Should have Country Code and must be 10 digit number) example: 919234567890")
    String phoneNumber;

    private List<String>  email;

    String address;

    String city;
    String state;
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}$", message="Invalid Zip Code(First digit is non-zero, Should be 6 digit), example: 234098")
    String zipcode;

}
