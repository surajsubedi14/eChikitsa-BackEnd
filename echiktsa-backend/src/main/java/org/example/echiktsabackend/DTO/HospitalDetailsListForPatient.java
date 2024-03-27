package org.example.echiktsabackend.DTO;

import lombok.Data;
import org.hibernate.sql.results.graph.collection.internal.BagInitializer;

import java.math.BigInteger;
@Data
public class HospitalDetailsListForPatient {
    private String email;
    private BigInteger phoneNumber;
    private String address;
    private  String website;
    private int noOfDoctors;
    private String hospital_name;

    private int noOFSeniorDoctors;
    private String Specialization;

    private float Rating;

}
