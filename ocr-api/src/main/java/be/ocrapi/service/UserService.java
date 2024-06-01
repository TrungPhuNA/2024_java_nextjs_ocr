package be.ocrapi.service;

import be.ocrapi.model.*;
import be.ocrapi.repository.*;
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
    private RankRepository rankRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EmployerTypeRepository employerTypeRepository;
    @Autowired
    private CertificateRepository certificateRepository;


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
        d.setStatus(u.getStatus());
        d.setCode(u.getCode());
        d.setName(u.getName());
        d.setEmail(u.getEmail());
        d.setPhone(u.getPhone());
        d.setUserType(u.getUserType());
        d.setGender(u.getGender());
        d.setAddress(u.getAddress());
        d.setDob(u.getDob());

        d.setCccd(u.getCccd());
        d.setCccdAddress(u.getCccdAddress());
        d.setCccdDate(u.getCccdDate());
        d.setRegion(u.getRegion());

        EmployerType e = d.getEmployerType();
        Rank r = d.getRank();
        Salary s = d.getSalary();
        Certificate c = d.getCertificate();
        Room room = d.getRoom();

        if(u.getEmployerTypeId() != null) {
            e = employerTypeRepository.getById(u.getEmployerTypeId());
        }
        if(u.getUserRankId() != null) {
            r = rankRepository.getById(u.getUserRankId());
        }
        if(u.getSalaryId() != null) {
            s = salaryRepository.getById(u.getSalaryId());
        }
        if(u.getCertificateId() != null) {
            c = certificateRepository.getById(u.getCertificateId());
        }
        if(u.getRoomId() != null) {
            room = roomRepository.getById(u.getRoomId());
        }

        d.setEmployerType(e);
        d.setRank(r);
        d.setSalary(s);
        d.setCertificate(c);
        d.setRoom(room);

        return d;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByAccessToken(String access_token) {
        return userRepository.findUserByAccessToken(access_token);
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
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("USER not active");
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(jwtToken);
        user.setRefreshToken(refreshToken);
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
    public User save(UserRequest dataRequest) {
        User u = setData(dataRequest, null);
        User newData = userRepository.save(u);
        if(dataRequest.getCode() == null) {
            newData.setCode("MEMBER0000" + newData.getId());
        }
        userRepository.save(newData);
        return newData;
    }

    @Override
    public User update(int id, UserRequest dataRequest) {
        User u = userRepository.getById(id);
        u = setData(dataRequest, u);
        return userRepository.save(u);
    }


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
