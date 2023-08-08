package rw.chadiss.backend_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.models.Citizen;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.ICitizenService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/citizens")
@CrossOrigin
public class CitizenController {

    private final ICitizenService citizenService;

    public CitizenController(ICitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> all() {
        return ResponseEntity.ok(new ApiResponse(true, citizenService.findAll()));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CitizenSignUpDTO dto) {
        Citizen citizen = citizenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, citizen));
    }
}
