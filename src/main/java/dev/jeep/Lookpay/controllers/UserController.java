package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntranceDTO userLogin) {
        try {
            UserResponseDTO user = userService.login(userLogin.getEmail(), userLogin.getPassword());
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Invalid credentials.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntranceDTO user) {
        try {
            UserResponseDTO userCreated = userService.register(user);
            if (userCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"User is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserEntranceDTO user) {
        try {
            UserResponseDTO userUpdated = userService.update(id, user);
            if (userUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"The email is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (userService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"User deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"User not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }
}
