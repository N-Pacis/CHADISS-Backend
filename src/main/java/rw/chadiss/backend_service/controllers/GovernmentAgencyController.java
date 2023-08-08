package rw.chadiss.backend_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.GovernmentAgency;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.IDeputyService;
import rw.chadiss.backend_service.services.IGovernmentAgencyService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/government-agencies")
@CrossOrigin
public class GovernmentAgencyController {
    private final IGovernmentAgencyService service;

    public GovernmentAgencyController(IGovernmentAgencyService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public ResponseEntity<ApiResponse> all() {
        return ResponseEntity.ok(new ApiResponse(true, service.findAll()));
    }

    @PostMapping(path = "/register")
    @PreAuthorize("hasAnyAuthority('DEPUTY')")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody GovernmentAgencySignUpDTO dto) {
        GovernmentAgency governmentAgency = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, governmentAgency));
    }
}
