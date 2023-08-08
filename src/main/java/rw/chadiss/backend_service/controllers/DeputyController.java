package rw.chadiss.backend_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.IDeputyService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/deputies")
@CrossOrigin
public class DeputyController {
    private final IDeputyService deputyService;

    public DeputyController(IDeputyService deputyService) {
        this.deputyService = deputyService;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> all() {
        return ResponseEntity.ok(new ApiResponse(true, deputyService.findAll()));
    }

    @PostMapping(path = "/register")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody DeputySignUpDTO dto) {
        Deputy deputy = deputyService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, deputy));
    }
}
