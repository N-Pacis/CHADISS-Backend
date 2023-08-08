package rw.chadiss.backend_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.Ombudsman;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.IDeputyService;
import rw.chadiss.backend_service.services.IOmbudsmanService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/ombudsman")
@CrossOrigin
public class OmbudsmanController {
    private final IOmbudsmanService ombudsmanService;

    public OmbudsmanController(IOmbudsmanService ombudsmanService) {
        this.ombudsmanService = ombudsmanService;
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> all() {
        return ResponseEntity.ok(new ApiResponse(true, ombudsmanService.getAll()));
    }

    @PostMapping(path = "/register")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody OmbudsmanSignUpDTO dto) {
        Ombudsman ombudsman = ombudsmanService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, ombudsman));
    }
}
