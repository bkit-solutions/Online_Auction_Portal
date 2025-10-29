package Com.Auction.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.Auction.DTO.LoginDTO;
import Com.Auction.DTO.RegisterDTO;
import Com.Auction.Entity.User;
import Com.Auction.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Email already registered";
        }

        User.Role role = User.Role.valueOf(dto.getRole().toUpperCase());
        User user = new User(dto.getName(), dto.getEmail(), dto.getPassword(), role);
        userRepository.save(user);

        return "User registered successfully";
    }

    public Optional<User> login(LoginDTO dto) {
        return userRepository.findByEmail(dto.getEmail())
                .filter(user -> user.getPassword().equals(dto.getPassword()));
    }
}
