package com.codigo.ms_other.controller;

import com.codigo.ms_other.client.SeguridadClient;
import com.codigo.ms_other.response.UsuarioResponseClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/others/v1")
public class OtherController {
    private final SeguridadClient seguridadClient;

    public OtherController(SeguridadClient seguridadClient) {
        this.seguridadClient = seguridadClient;
    }

    @GetMapping("/info")
    public ResponseEntity<List<UsuarioResponseClient>> getInfoAll(
            @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(seguridadClient.getInfoUsers(token));
    }

}
