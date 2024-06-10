package be.ocrapi.service.User;

import be.ocrapi.model.*;
import be.ocrapi.repository.*;
import be.ocrapi.request.UserRequest;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.response.LoginResponse;
import be.ocrapi.response.User.UserResponse;
import be.ocrapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private MappingResponseDto responseDto;
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

    public static Date parseDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }
    private User setData(UserRequest u, User d) {
        if(d == null) {
            d = new User();
            d.setPassword(passwordEncoder.encode(u.getPassword()));

        }

        if(u.getDob() != null && !u.getDob().isEmpty()) {
            Date fromDate = this.parseDate(u.getDob(), "yyyy-MM-dd");
            d.setDob(fromDate);
        }
        if(u.getCccdDate() != null && !u.getCccdDate() .isEmpty()) {
            Date date = this.parseDate(u.getCccdDate() , "yyyy-MM-dd");
            d.setCccdDate(date);
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

        d.setCccd(u.getCccd());
        d.setCccdAddress(u.getCccdAddress());
        d.setRegion(u.getRegion());

        EmployerType e = d.getEmployerType();
        Rank r = d.getRank();
        Certificate c = d.getCertificate();
        Room room = d.getRoom();

        if(u.getEmployerTypeId() != null) {
            e = employerTypeRepository.getById(u.getEmployerTypeId());
        }
        if(u.getUserRankId() != null) {
            r = rankRepository.getById(u.getUserRankId());
        }

        if(u.getCertificateId() != null) {
            c = certificateRepository.getById(u.getCertificateId());
        }
        if(u.getRoomId() != null) {
            room = roomRepository.getById(u.getRoomId());
        }

        d.setEmployerType(e);
        d.setRank(r);
        d.setCertificate(c);
        d.setRoom(room);

        return d;
    }

    @Override
    public UserResponse findById(Integer id) {
        User u = userRepository.getById(id);
        return responseDto.getUserInfo(u);
    }

    @Override
    public UserResponse findByAccessToken(String access_token) {
        User u = userRepository.findUserByAccessToken(access_token);
        if(u == null) {
            return null;
        }
        return responseDto.getUserInfo(u);
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
        return new LoginResponse(jwtToken, refreshToken, responseDto.getUserInfo(user));
    }

//    @Override
//    public Page<User> findAll(int page, int page_size) {
//        Pageable pageable = PageRequest.of(page, page_size);
//        return userRepository.findAll(pageable);
//    }

    @Override
    public List<UserResponse> findAndCount(String page, String page_size,
                                           String status, String name,
                                           String email,
                                           String rank_id,
                                           String room_id,
                                           String certificate_id,
                                           String user_type) {
        List<User> data = this.userRepository.findAndCount((parseInt(page) - 1) * parseInt(page_size),
                parseInt(page_size), status, name, email, rank_id, room_id, certificate_id, user_type
                );
        List<UserResponse> users = new ArrayList<>();
        if(!data.isEmpty()) {
            for (User item: data) {
                users.add(responseDto.getUserInfo(item));
            }
        }


        return users;
    }
    @Override
    public Integer countTotalCondition( String status, String name,
                                        String email,
                                        String rank_id,
                                        String room_id,
                                        String certificate_id,
                                        String user_type) {
        return this.userRepository.countByConditions(status, name, email,
                rank_id, room_id, certificate_id, user_type);
    }

    @Override
    public UserRequest save(UserRequest dataRequest) {
        User u = setData(dataRequest, null);
        User newData = userRepository.save(u);
        if(dataRequest.getCode() == null ) {
            newData.setCode("MEMBER0000" + newData.getId());
            userRepository.save(newData);
        }
        return dataRequest;
    }

    @Override
    public UserRequest update(int id, UserRequest dataRequest) {
        User u = userRepository.getById(id);
        u = setData(dataRequest, u);
        if(dataRequest.getCode() == null && u.getCode() == null) {
            u.setCode("MEMBER0000" + u.getId());
            userRepository.save(u);
        }
        userRepository.save(u);
        return dataRequest;
    }


    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);;
    }
}
