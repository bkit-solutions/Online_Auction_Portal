package Com.Auction.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Com.Auction.DTO.LoginDTO;
import Com.Auction.DTO.LoginRequest;
import Com.Auction.DTO.RegisterDTO;
import Com.Auction.Entity.User;
import Com.Auction.Repository.UserRepository;
import Com.Auction.Service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend JS calls
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
        String result = userService.register(dto);
        if (result.equals("Email already registered")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        return userOptional
            .map(user -> {
                // Optionally check password here if needed
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return ResponseEntity.ok(user);  // Or return specific info like user.getId() if needed
                } else {
                    return ResponseEntity.status(401).body("Invalid email or password");
                }
            })
            .orElseGet(() -> ResponseEntity.status(401).body("Invalid email or password"));
    }

}
