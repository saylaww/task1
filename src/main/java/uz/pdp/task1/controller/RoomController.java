package uz.pdp.task1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Hotel;
import uz.pdp.task1.entity.Room;
import uz.pdp.task1.payload.RoomDto;
import uz.pdp.task1.repository.HotelRepository;
import uz.pdp.task1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    final RoomRepository roomRepository;
    final HotelRepository hotelRepository;

    public RoomController(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    //Create
    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto){
        Room room = new Room();
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()){
            return "Hotel id not found";
        }
        Hotel hotel = optionalHotel.get();

        room.setHotel(hotel);

        roomRepository.save(room);

        return "Room saved";
    }

    //Read
    @GetMapping
    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

    //Update
    @PutMapping("/{id}")
    public String updateRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()){
            return "Room id not found";
        }
        Room room = optionalRoom.get();
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setNumber(roomDto.getNumber());

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()){
            return "Hotel id not found";
        }
        Hotel hotel = optionalHotel.get();
        room.setHotel(hotel);

        roomRepository.save(room);

        return "Room updated";
    }

    //Delete
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        boolean existsById = roomRepository.existsById(id);
        if (!existsById){
            return "Room id not found";
        }
        roomRepository.deleteById(id);
        return "Room deleted" +
                "";
    }

    //byHotelId
    @GetMapping("/{id}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
//            Hotel hotel = optionalHotel.get();

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Room> page = roomRepository.findAllByHotelId(id, pageable);

            return page;
    }

}
