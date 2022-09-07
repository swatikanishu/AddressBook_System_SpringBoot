package com.example.addressbooksystem.dto;

import com.example.addressbooksystem.model.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ResponseDto {

    private String message;
    private Object object;

    public ResponseDto(String string, Address response) {
        this.message = string;
        this.object = response;
    }

    public ResponseDto(String string, String response) {
        this.message = string;
        this.object = response;
    }

    public ResponseDto(String string, Optional<Address> response) {
        this.message = string;
        this.object = response;
    }

    public ResponseDto(String string, List<Address> response) {
        this.message = string;
        this.object = response;
    }

}