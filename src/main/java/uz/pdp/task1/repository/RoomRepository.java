package uz.pdp.task1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Hotel;
import uz.pdp.task1.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
//    boolean existsByName(String name);
    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);
//    List<Page<Room>> findAllByHotelId(Integer hotel_id);
}
