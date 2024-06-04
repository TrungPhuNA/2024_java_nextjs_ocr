package be.ocrapi.service.User;

import be.ocrapi.model.User;
import be.ocrapi.request.UserRequest;
import be.ocrapi.response.LoginResponse;
import be.ocrapi.response.User.UserResponse;

import java.util.List;

public interface UserServiceInterface {
    UserResponse findById(Integer id);

    UserResponse findByAccessToken(String access_token);
    LoginResponse login(UserRequest data);
//    Page<User> findAll(int page, int page_size);

    UserRequest save(UserRequest user);
    UserRequest update(int id, UserRequest user);
    void delete(User user);

    List<UserResponse> findAndCount(
            String page, String page_size,
            String status, String name,
            String email,
            String rank_id,
            String room_id,
            String certificate_id,
            String user_type
    );

    Integer countTotalCondition(String status, String name,
                                String email,
                                String rank_id,
                                String room_id,
                                String certificate_id,
                                String user_type);
}
