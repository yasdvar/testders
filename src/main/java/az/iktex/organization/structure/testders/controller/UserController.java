package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.enums.Gender;
import az.iktex.organization.structure.testders.request.PassportRequest;
import az.iktex.organization.structure.testders.request.UserRequest;
import az.iktex.organization.structure.testders.response.PassportResponse;
import az.iktex.organization.structure.testders.response.UserResponse;
import az.iktex.organization.structure.testders.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequest));

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));

    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getByEmail(@RequestParam String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll (@RequestParam(required = false) Gender gender){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(gender));
    }

    @GetMapping("/without-passport")
    public ResponseEntity<List<UserResponse>> getWithoutPassport(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getWithoutPassport());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/passport")
    public ResponseEntity<PassportResponse>  addPassport(@PathVariable Long id, @RequestBody PassportRequest passportRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addPassport(id,passportRequest));
    }


    @GetMapping("/{id}/passport")
    public ResponseEntity<PassportResponse> getPassport(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPassport(id));
    }

    @DeleteMapping("/{id}/passport")
    public ResponseEntity<Void>deletePassport(@PathVariable Long id){
        userService.deletePassport(id);
        return ResponseEntity.noContent().build();
    }
}