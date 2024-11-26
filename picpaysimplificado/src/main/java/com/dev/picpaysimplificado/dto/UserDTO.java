package com.dev.picpaysimplificado.dto;

import com.dev.picpaysimplificado.entities.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
