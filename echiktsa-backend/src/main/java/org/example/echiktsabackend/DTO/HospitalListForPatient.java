package org.example.echiktsabackend.DTO;

import lombok.Data;

import java.util.List;
@Data
public class HospitalListForPatient {

    private long hospital_id;
    private String hospital_name;
    private String image_path;
    private String location;
    private double rating;
    private List<String> specialisations;
    private int num_senior_doctors;
    private List<String> doctors;





}
