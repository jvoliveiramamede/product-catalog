package com.example.productcatalog.dto;

import java.io.Serializable;

import com.example.productcatalog.model.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest implements Serializable {
    private String username;
    private String email;
    private String password;
    private RoleEnum role;
}
