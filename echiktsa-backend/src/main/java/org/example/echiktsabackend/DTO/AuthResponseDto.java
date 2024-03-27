package org.example.echiktsabackend.DTO;


import org.example.echiktsabackend.DTO.AuthStatus;
public record AuthResponseDto (String token, AuthStatus authStatus){

}
