package com.pj.database_design.controller;

import com.pj.database_design.controller.request.*;
import com.pj.database_design.domain.*;
import com.pj.database_design.service.JwtUserDetailsService;
import com.pj.database_design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

        Map<String, Object> response = new HashMap<>();
        Long id = userService.login(request.getUsername(), request.getPassword(),request.getAuthority(),request.getTreatmentArea());
        final UserDetails targetUser = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        Long authorityId=userService.findAuthorityId(request.getUsername());
        response.put("Id", id);
        response.put("authorityId",authorityId);
        response.put("userDetails", targetUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getPatientsInThisArea")
    public ResponseEntity<Map<String, Object>> getPatientsInThisArea(@RequestBody GetPatientsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Patient> message = userService.getPatients(request.getTreatmentArea());

        response.put("patients",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getHeadNurseInThisArea")
    public ResponseEntity<Map<String, Object>> getHeadNurseInThisArea(@RequestBody GetHeadNursesRequest request) {

        Map<String, Object> response = new HashMap<>();
        Head_nurse message = userService.getHeadNurses(request.getTreatmentArea());

        response.put("headNurse",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getNursesInThisArea")
    public ResponseEntity<Map<String, Object>> getNursesInThisArea(@RequestBody GetWardNursesRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Ward_nurse> message = userService.getWardNurses(request.getTreatmentArea());

        response.put("wardNurses",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/modifyPatientCondition")
    public ResponseEntity<Map<String, Object>> modifyPatientCondition(@RequestBody ModifyPatientConditionRequest request) {

        Map<String, Object> response = new HashMap<>();
        String message = userService.changePatientsLevel(request.getPatientID(),request.getNewCondition());

        response.put("message",message);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/toBeDischargedPatients")
    public ResponseEntity<Map<String, Object>> toBeDischargedPatients(@RequestBody FindDischargedPatientsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Patient> patients = userService.findDischargedPatients(request.getTreatmentArea());

        response.put("patients",patients);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getSickbedsInThisArea")
    public ResponseEntity<Map<String, Object>> getSickbedsInThisArea(@RequestBody GetSickbedsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Sickbed> beds = userService.getSickbeds(request.getTreatmentArea());

        response.put("sickbeds",beds);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addNurse")
    public ResponseEntity<Map<String, Object>> addNurse(@RequestBody AddNurseRequest request) {

        Map<String, Object> response = new HashMap<>();
        userService.addNurse(request.getNurseID(),request.getTreatmentArea());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/newNucTest")
    public ResponseEntity<Map<String, Object>> newNucTest(@RequestBody AddNucTestRequest request) {

        //DateFormat d4 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        DateFormat d4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Map<String, Object> response = new HashMap<>();
        try {
            userService.addNucTest(request.getPatientId(),request.getDoctorId(),request.getConditionRate(),d4.parse(request.getDate().replace("Z"," UTC")),request.getResult());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deleteNurse")
    public ResponseEntity<Map<String, Object>> deleteNurse(@RequestBody DeleteNurseRequest request) {

        Map<String, Object> response = new HashMap<>();
        userService.deleteNurse(request.getNurseID(),request.getTreatmentArea());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/allowDischarge")
    public ResponseEntity<Map<String, Object>> allowDischarge(@RequestBody AllowDischargeRequest request) {

        Map<String, Object> response = new HashMap<>();
        userService.allowDischarge(request.getPatinetId(),request.getDoctorId());

        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initialRecord")
    public ResponseEntity<Map<String, Object>> initialRecord(@RequestBody InitialRecordRequest request) {

        Map<String, Object> response = new HashMap<>();
        String message=userService.initialRecord(request.getName(),request.getAge(),request.getGender(),request.getConditionRate());
        response.put("message",message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dailyRecord")
    public ResponseEntity<Map<String, Object>> dailyRecord(@RequestBody DailyRecordRequest request) {

        //DateFormat d4 = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        DateFormat d4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");

        Map<String, Object> response = new HashMap<>();
        try {
            userService.dailyRecord(request.getPatientId(),request.getNurseId(),request.getTemperature(),request.getSymptom(),request.getResult(),request.getLiveState(),d4.parse(request.getDate().replace("Z"," UTC")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.put("message","success");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/getResponsibledPatients")
    public ResponseEntity<Map<String, Object>> getResponsibledPatients(@RequestBody GetResponsibledPatientsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Patient> patients=userService.getResponsibledPatients(request.getNureseId());
        response.put("message",patients);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/modifyInfos")
    public ResponseEntity<Map<String, Object>> modifyInfos(@RequestBody ModifyInfosRequest request) {

        Map<String, Object> response = new HashMap<>();
        userService.modifyInfos(request.getUserId(),request.getName(),request.getAge(),request.getGender(),request.getPwd());
        response.put("message","success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/browseNucTests")
    public ResponseEntity<Map<String, Object>> browseNucTests(@RequestBody BrowseNucTestsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Nucleic_acid_test> tests=userService.browseNucTests(request.getPatientId());
        response.put("tests",tests);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/browseTreatRecords")
    public ResponseEntity<Map<String, Object>> browseTreatRecords(@RequestBody BrowseTreatRecordsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Treat_record> tests=userService.browseTreatRecords(request.getPatientId());
        response.put("tests",tests);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getMessages")
    public ResponseEntity<Map<String, Object>> getMessages(@RequestBody GetMessagesRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Message> tests=userService.getMessages(request.getUserId());
        response.put("tests",tests);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getAllPatients")
    public ResponseEntity<Map<String, Object>> getAllPatients(@RequestBody GetAllPatientsRequest request) {

        Map<String, Object> response = new HashMap<>();
        List<Patient> tests=userService.getAllPatients();
        response.put("tests",tests);
        return ResponseEntity.ok(response);
    }

}



