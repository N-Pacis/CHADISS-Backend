package rw.chadiss.backend_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rw.chadiss.backend_service.dtos.AdminSignUpDTO;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;
import rw.chadiss.backend_service.exceptions.BadRequestException;
import rw.chadiss.backend_service.models.Citizen;
import rw.chadiss.backend_service.models.User;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.IUserService;
import rw.chadiss.backend_service.utils.Constants;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
@CrossOrigin
public class UserController {

    private final IUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${ADMIN_REG_KEY}")
    private String adminRegKey;


    @Autowired
    public UserController(IUserService userService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status",required = false) EUserStatus status,
            @RequestParam(value = "role", required = false) ERole role

    ){
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success(userService.search(status, name, role, pageable)));
    }

    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponse> currentlyLoggedInUser() {
        return ResponseEntity.ok(new ApiResponse(true, userService.getLoggedInUser()));
    }

    @PostMapping(path = "/register/as-admin")
    public ResponseEntity<ApiResponse> registerAsAdmin(@Valid @RequestBody AdminSignUpDTO dto) {

        if(!dto.getKey().equals(adminRegKey))
            throw new BadRequestException("You are not authorized to register as an admin");

        User user = new User();

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getMobile());
        user.setNationalId(dto.getNationalId());
        user.setPassword(encodedPassword);
        user.setRole(ERole.ADMIN);

        User entity = this.userService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, entity));
    }

}