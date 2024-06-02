package be.ocrapi.service.Room;

import be.ocrapi.model.Category;
import be.ocrapi.model.Room;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RoomRequest;
import be.ocrapi.response.Room.ListRoomResponse;
import be.ocrapi.response.Room.RoomResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoomServiceInterface {
    RoomResponse findById(Integer id);
    ListRoomResponse findAll(int page, int page_size);
    RoomRequest save(RoomRequest order);
    RoomRequest update(int id, RoomRequest order);
    void delete(int order);
}
