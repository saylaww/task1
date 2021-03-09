package uz.pdp.task1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Hotel;
import uz.pdp.task1.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    final HotelRepository hotelRepository;


    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    //Create
    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        boolean byName = hotelRepository.existsByName(hotel.getName());
        if (byName){
            return "Hotel name have in db";
        }
        hotelRepository.save(hotel);

        return "Hotel saved";
    }

    //Read
    @GetMapping
    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    //Update
    @PutMapping("/{id}")
    public String updateHotel(@PathVariable Integer id, @RequestBody Hotel hotel){

        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()){
            return "Hotel id not found";
        }
        Hotel dbhotel = optionalHotel.get();
        dbhotel.setName(hotel.getName());

        hotelRepository.save(dbhotel);

        return "Hotel updated";
    }

    //Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        boolean existsById = hotelRepository.existsById(id);
        if (!existsById){
            return "Hotel id not found";
        }
        hotelRepository.deleteById(id);

        return "Hotel deleted";
    }

}
