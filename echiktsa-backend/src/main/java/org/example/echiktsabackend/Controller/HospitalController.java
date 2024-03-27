package org.example.echiktsabackend.Controller;
import org.example.coreapi.Entities.Department;
import org.example.coreapi.Entities.Doctor;
import org.example.coreapi.Entities.Hospital;
import org.example.coreapi.Entities.User;
import org.example.coreapi.Services.HospitalServices;
import org.example.echiktsabackend.DTO.HospitalDetailsListForPatient;
import org.example.echiktsabackend.DTO.HospitalListForPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    @Autowired
    public HospitalServices hospitalServices;

    @GetMapping(value = "/get-hospitals")
    public ResponseEntity<?> GetAllHospitalDetails() {
        try {
            List<Hospital> result= hospitalServices.getAllHospitalDetails();
            return ResponseEntity.of(Optional.of(result));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping(value = "/get-hospitals-landing")

    public ResponseEntity<?> GetAllHospitalDetailsLanding() {
        try {
            List<HospitalListForPatient> result = new ArrayList<>();
            List<Hospital> hospitals = hospitalServices.getAllHospitalDetails();

            for(Hospital h : hospitals)
            {
                List<Doctor> doctors = h.getDoctors().stream().toList();
                List<String> nameOfDoctors = new ArrayList<>();
                List<String> nameofSpecialization = new ArrayList<>();

                int cnt = 0;
                for(Doctor d: doctors)
                {
                    if(d.getSeniorityLevel().equals("Senior")) {
                        cnt++;
                    }
//                    nameOfDoctors.add(d.getUser().getFirstName() + " " + d.getUser().getLastName() );

                }
                for(Department f: h.getDepartments())
                {
                    nameofSpecialization.add(f.getDepartment_name());
                }
                HospitalListForPatient hospitalListForPatient = new HospitalListForPatient();
                hospitalListForPatient.setHospital_name(h.getName());
                hospitalListForPatient.setImage_path(h.getImgUrl());
                hospitalListForPatient.setLocation(h.getAddress());
                hospitalListForPatient.setNum_senior_doctors(cnt);
                hospitalListForPatient.setDoctors(nameOfDoctors);
                hospitalListForPatient.setRating(h.getRating());
                hospitalListForPatient.setSpecialisations(nameofSpecialization);
                hospitalListForPatient.setHospital_id(h.getHospital_id());

                result.add(hospitalListForPatient);

            }

            return ResponseEntity.of(Optional.of(result));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }


    @GetMapping(value = "/get-specific-hospitals/{id}")
    public ResponseEntity<?> GetSpecificHospitalsDetails(@PathVariable int id ) {
        try {
            Optional<Hospital> hospitals = hospitalServices.getSpecificHospitalDetails(id);
            Hospital hospital = hospitals.orElse(null);
            HospitalDetailsListForPatient hospitalDetailsListForPatient = new HospitalDetailsListForPatient();
            hospitalDetailsListForPatient.setAddress(hospital.getAddress());
            hospitalDetailsListForPatient.setHospital_name(hospital.getName());
            hospitalDetailsListForPatient.setRating(hospital.getRating());
            hospitalDetailsListForPatient.setEmail(hospital.getEmail());
            hospitalDetailsListForPatient.setWebsite(hospital.getWebsite());
            hospitalDetailsListForPatient.setPhoneNumber(hospital.getPhoneNumber());
            List<String> specialisations = new ArrayList<>();
            for (Department department : hospital.getDepartments()) {
                specialisations.add(department.getDepartment_name());
            }
            hospitalDetailsListForPatient.setSpecialization(String.valueOf(specialisations));
            List<String> doctors = new ArrayList<>();
            int cnt = 0;
            int cnt2 = 0;
            for (Doctor doctor : hospital.getDoctors()) {
//                doctors.add(doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName());
                cnt++;
                if(Objects.equals(doctor.getSeniorityLevel(), "Senior"))
                {
                    cnt2++;
                }
            }
            hospitalDetailsListForPatient.setNoOfDoctors(cnt);
            hospitalDetailsListForPatient.setNoOFSeniorDoctors(cnt2);
            return ResponseEntity.of(Optional.of(hospitalDetailsListForPatient));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @PostMapping(value = "/add-hospital")
    public ResponseEntity<?> RegisterHospital(@RequestBody Hospital hospital) {
        try {
            if (hospitalServices.existHospital(hospital.getEmail()) != null){
                return ResponseEntity.ok("Hospital already exists");
            }
            // Save the new user
            hospitalServices.addHospital(hospital);
            return ResponseEntity.ok("Hospital registered successfully");
        }
        catch (Exception ex) {
            return ResponseEntity.internalServerError().body("There is a server error");
        }
    }

}
