package be.ocrapi.repository;

import be.ocrapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE TRUE AND email = :id",nativeQuery = true)
    User findUserByEmail(@Param("id")String id);

    Optional<User> findByEmail(String username);
    User findUserByAccessToken(String accessToken);



    @Query(value = "Select u.* " +
            " from users u " +
            " LEFT JOIN ranks r ON u.rank_id = r.id " +
            " LEFT JOIN certificates ce ON u.certificate_id = ce.id " +
            " LEFT JOIN rooms ro ON u.room_id = ro.id " +
            " LEFT JOIN employer_types et ON u.type_id = et.id " +
            " WHERE TRUE " +
            "AND (:rank_id is null or :rank_id ='' or u.rank_id =:rank_id) " +
            " AND (:room_id is null or :room_id ='' or u.room_id= :room_id) " +
            " AND (:certificate_id is null or :certificate_id = '' or u.certificate_id= :certificate_id) " +
            " AND (:name is null or :name = '' or u.name LIKE %:name%) " +
            " AND (:email is null or :email = '' or u.email LIKE %:email%) " +
            " AND (:status is null or :status = '' or u.status= :status) " +
            " AND (:user_type is null or :user_type = '' or u.user_type= :user_type) " +
            " LIMIT :page_size OFFSET :page",nativeQuery = true)
    List<User> findAndCount(@Param("page") int page,
                           @Param("page_size")int page_size,
                            @Param("status") String status,
                            @Param("name") String name,
                            @Param("email") String email,
                           @Param("rank_id") String rank_id,
                           @Param("room_id") String room_id,
                           @Param("certificate_id") String certificate_id,
                           @Param("user_type") String user_type
    );


    @Query(value = "Select count(*) from users u " +
            " WHERE TRUE AND (:rank_id is null or :rank_id ='' or u.rank_id =:rank_id) " +
            " AND (:room_id is null or :room_id ='' or u.room_id= :room_id) " +
            " AND (:certificate_id is null or :certificate_id = '' or u.certificate_id= :certificate_id) " +
            " AND (:name is null or :name = '' or u.name LIKE %:name%) " +
            " AND (:email is null or :email = '' or u.email LIKE %:email%) " +
            " AND (:status is null or :status = '' or u.status= :status) " +
            " AND (:user_type is null or :user_type = '' or u.user_type= :user_type) ",nativeQuery = true)
    Integer countByConditions(
            @Param("status") String status,
            @Param("name") String name,
            @Param("email") String email,
            @Param("rank_id") String rank_id,
            @Param("room_id") String room_id,
            @Param("certificate_id") String certificate_id,
            @Param("user_type") String user_type
    );

}