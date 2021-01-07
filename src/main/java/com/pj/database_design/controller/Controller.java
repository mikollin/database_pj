package com.pj.database_design.controller;

import com.pj.database_design.controller.request.*;
import com.pj.database_design.domain.Head_nurse;
import com.pj.database_design.domain.Patient;
import com.pj.database_design.domain.Sickbed;
import com.pj.database_design.domain.Ward_nurse;
import com.pj.database_design.service.JwtUserDetailsService;
import com.pj.database_design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyl
 */
@RestController
public class Controller {
    private UserService userService;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public Controller(UserService userService, JwtUserDetailsService jwtUserDetailsService) {
        this.userService = userService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        String message = userService.login(request.getUsername(), request.getPassword());
        final UserDetails targetUser = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        response.put("isAllowed:", message);
        response.put("userDetails", targetUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPatientsInThisArea")
    public ResponseEntity<Map<String, Object>> getPatientsInThisArea(@RequestBody GetPatientsRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        List<Patient> message = userService.getPatients(request.getTreatmentArea());

        response.put("patients",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getHeadNurseInThisArea")
    public ResponseEntity<Map<String, Object>> getHeadNurseInThisArea(@RequestBody GetHeadNursesRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        Head_nurse message = userService.getHeadNurses(request.getTreatmentArea());

        response.put("headNurse",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getNursesInThisArea")
    public ResponseEntity<Map<String, Object>> getNursesInThisArea(@RequestBody GetWardNursesRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        List<Ward_nurse> message = userService.getWardNurses(request.getTreatmentArea());

        response.put("wardNurses",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/modifyPatientCondition")
    public ResponseEntity<Map<String, Object>> modifyPatientCondition(@RequestBody ModifyPatientConditionRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        String message = userService.changePatientsLevel(request.getPatientID(),request.getNewCondition());

        response.put("message",message);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/toBeDischargedPatients")
    public ResponseEntity<Map<String, Object>> toBeDischargedPatients(@RequestBody FindDischargedPatientsRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        List<Patient> patients = userService.findDischargedPatients(request.getTreatmentArea());

        response.put("patients",patients);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getSickbedsInThisArea")
    public ResponseEntity<Map<String, Object>> getSickbedsInThisArea(@RequestBody GetSickbedsRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        List<Sickbed> beds = userService.getSickbeds(request.getTreatmentArea());

        response.put("sickbeds",beds);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addNurse")
    public ResponseEntity<Map<String, Object>> addNurse(@RequestBody AddNurseRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        userService.addNurse(request.getNurseID(),request.getTreatmentArea());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/newNucTest")
    public ResponseEntity<Map<String, Object>> newNucTest(@RequestBody AddNucTestRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        userService.addNucTest(request.getPatientId(),request.getDoctorId(),request.getConditionRate(),request.getDate(),request.getResult());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deleteNurse")
    public ResponseEntity<Map<String, Object>> deleteNurse(@RequestBody DeleteNurseRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        userService.deleteNurse(request.getNurseID(),request.getTreatmentArea());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/allowDischarge")
    public ResponseEntity<Map<String, Object>> allowDischarge(@RequestBody AllowDischargeRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        userService.allowDischarge(request.getPatinetId(),request.getDoctorId());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initialRecord")
    public ResponseEntity<Map<String, Object>> initialRecord(@RequestBody InitialRecordRequest request) {
        //login
        Map<String, Object> response = new HashMap<>();
        userService.initialRecord(request.getName(),request.getAge(),request.getGender(),request.getConditionRate());
        response.put("message","success");
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/dailyRecord")
//    public ResponseEntity<Map<String, Object>> dailyRecord(@RequestBody DailyRecordRequest request) {
//        //login
//        Map<String, Object> response = new HashMap<>();
//        userService.initialRecord(request.getName(),request.getAge(),request.getGender(),request.getConditionRate());
//        response.put("message","success");
//        return ResponseEntity.ok(response);
//    }


}



