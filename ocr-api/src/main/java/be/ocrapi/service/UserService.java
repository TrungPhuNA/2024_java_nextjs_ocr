package be.ocrapi.service;

import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.UserRequest;
import be.ocrapi.response.LoginResponse;
import be.ocrapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;


    private User setData(UserRequest u, User d) {
        if(d == null) {
            d = new User();
            d.setPassword(passwordEncoder.encode(u.getPassword()));
        }
        d.setAvatar(u.getAvatar());
        d.setName(u.getName());
        d.setEmail(u.getEmail());
        d.setPhone(u.getPhone());
        d.setGender(u.getGender());
        return d;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public LoginResponse login(UserRequest data) {
        log.debug("email=====> " + data.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getEmail(),
                        data.getPassword()
                )
        );

        var user = userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setAccess_token(jwtToken);
        user.setRefresh_token(refreshToken);
        userRepository.save(user);
        log.debug("token=========> " + jwtToken);

        return new LoginResponse(jwtToken, refreshToken, user);
    }

    @Override
    public Page<User> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return userRepository.findAll(pageable);
    }

    @Override
    public User save(UserRequest user) {
        User u = setData(user, null);
        return userRepository.save(u);
    }

    @Override
    public User update(int id, UserRequest user) {
        User u = userRepository.getById(id);
        u = setData(user, u);
        return userRepository.save(u);
    }


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
