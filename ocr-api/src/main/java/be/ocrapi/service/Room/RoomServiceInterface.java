package be.ocrapi.service.Room;

import be.ocrapi.model.Category;
import be.ocrapi.model.Room;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RoomRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoomServiceInterface {
    Optional<Room> findById(Integer id);
    Page<Room> findAll(int page, int page_size);
    RoomRequest save(RoomRequest order);
    RoomRequest update(int id, RoomRequest order);
    void delete(int order);
}
