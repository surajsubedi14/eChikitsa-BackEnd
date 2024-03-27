package org.example.echiktsabackend.Controller;
import org.example.coreapi.Entities.Doctor;
import org.example.coreapi.Entities.Hospital;
import org.example.coreapi.Entities.User;
import org.example.coreapi.Services.DoctorServices;
import org.example.coreapi.Services.HospitalServices;
import org.example.coreapi.Services.UserServices;
import org.example.echiktsabackend.DTO.AuthResponseDto;
import org.example.echiktsabackend.DTO.AuthStatus;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    public DoctorServices doctorServices;
    @Autowired
    public HospitalServices hospitalServices;
    @Autowired
    public UserServices userServices;

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> GetDoctor(@PathVariable int id ) {
        try {
            Optional<Doctor> result = doctorServices.getDoctorDetails(id);
            return ResponseEntity.of(Optional.of(result));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }


    @PostMapping("/add-doctor")
    public ResponseEntity<AuthResponseDto> addDoctor(@RequestParam String id, @RequestBody Doctor doctor) {
        Hospital hospital = hospitalServices.hospitalRepository.getHospitalById(Long.valueOf(id));
        doctor.setHospital(hospital);
        try {
            User user = userServices.existUser(doctor);
            boolean status=false;
            if(user == null)
            {
                status = true;
                userServices.addDoctor(doctor);
            }
            var authResponseDto = new AuthResponseDto("", AuthStatus.USER_NOT_CREATED);;
            if(status)
            {
                authResponseDto = new AuthResponseDto("Doctor Added Successfully", AuthStatus.USER_CREATED_SUCCESSFULLY);

            }
            else {
                authResponseDto = new AuthResponseDto("Doctor Already Exists", AuthStatus.USER_NOT_CREATED);

            }
            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (Exception e) {
            var authResponseDto = new AuthResponseDto(null, AuthStatus.USER_NOT_CREATED);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponseDto);
        }

    }
}
