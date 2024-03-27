package org.example.echiktsabackend.Controller;

import org.example.coreapi.Entities.EHR;
import org.example.coreapi.Services.EHRServices;
import org.example.echiktsabackend.DTO.EHRModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/ehr")
public class EHRController {

    @Autowired
    public EHRServices ehrServices;

    @GetMapping(value = "/getnopatient/{id}")
    public ResponseEntity<?> GetNoOfPatient(@PathVariable int id ) {
        try {
            List<EHRModel> result = new ArrayList<>();
            Optional<List<EHR>> ehr= ehrServices.ehrRepository.findByDoctorId(id);
            EHRModel ehrModel = new EHRModel();

            if (ehr.isPresent()) {
                List<EHR> ehrList = ehr.get();
                int rowCount = ehrList.size();
                ehrModel.setNo_patient(rowCount);

            }
            result.add(ehrModel);



            return ResponseEntity.of(Optional.of(result));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("Lavda");
    }




}
