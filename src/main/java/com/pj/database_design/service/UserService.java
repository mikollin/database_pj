package com.pj.database_design.service;

import com.pj.database_design.domain.*;
import com.pj.database_design.exception.AddNurseException;
import com.pj.database_design.exception.AuthErrorException;
import com.pj.database_design.exception.PasswordErrorException;
import com.pj.database_design.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private AuthenticationManager authenticationManager;
    private JwtUserDetailsService jwtUserDetailsService;
    private DoctorRepository doctorRepository;
    private Emergency_nurseRepository emergency_nurseRepository;
    private Head_nurseRepository head_nurseRepository;
    private Nucleic_acid_testRepository nucleic_acid_testRepository;
    private PatientRepository patientRepository;
    private SickbedRepository sickbedRepository;
    private Ward_nurseRepository ward_nurseRepository;
    private Treat_recordRepository treat_recordRepository;
    private SickroomRepository sickroomRepository;

    private PasswordEncoder passwordEncoder;


    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService,
                        PasswordEncoder passwordEncoder,
                       DoctorRepository doctorRepository,Emergency_nurseRepository emergency_nurseRepository,Head_nurseRepository head_nurseRepository,
                       Nucleic_acid_testRepository nucleic_acid_testRepository, PatientRepository patientRepository,SickbedRepository sickbedRepository,
                       Ward_nurseRepository ward_nurseRepository,Treat_recordRepository treat_recordRepository,SickroomRepository sickroomRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;

        this.doctorRepository=doctorRepository;
        this.emergency_nurseRepository=emergency_nurseRepository;
        this.head_nurseRepository=head_nurseRepository;
        this.nucleic_acid_testRepository=nucleic_acid_testRepository;
        this.patientRepository=patientRepository;
        this.sickroomRepository=sickroomRepository;
        this.sickbedRepository=sickbedRepository;
        this.ward_nurseRepository=ward_nurseRepository;
        this.treat_recordRepository=treat_recordRepository;
    }

    public String login(String username, String password) {
        final UserDetails targetUser = jwtUserDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, targetUser.getPassword())) {
            throw new PasswordErrorException(username);
        }

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, password,
                targetUser.getAuthorities());
        final Authentication authentication = authenticationManager.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        final String token = jwtTokenUtil.generateToken((User) targetUser);
//        return token;
        return "Allowed";
    }

    public List<Patient> getPatients(String treatment){
        int area=0;
        switch (treatment){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        List res=patientRepository.findByTreatmentArea(area);
        return res;

    }

    public Head_nurse getHeadNurses(String treatment){
        int area=0;
        switch (treatment){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        Head_nurse res=head_nurseRepository.findByTreatmentArea(area);
        return res;
    }

    public List<Ward_nurse> getWardNurses(String treatment){
        int area=0;
        switch (treatment){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        List res=ward_nurseRepository.findByTreatmentArea(area);
        return res;

    }

    public String changePatientsLevel(Long patientId,String level){
        //修改病人病情评级、生命状态（带动病人治疗区域变动、病人的主治医生和护士长更改为新的）
        //
        int area=0;
        int max=0;
        switch (level){
            case "mild":area=0;max=3;break;
            case "middle":area=1;max=2;break;
            case "severe":area=2;max=1;break;

        }



        //检查转入的区域是否有空的病床
        if(sickbedRepository.findByPatient(null).size()!=0){
            System.out.println("has empty beds!");
            Patient patient=patientRepository.findByPatientId(patientId);
            patient.setConditionRate(area);
            patient.setTreatmentArea(area);

            //更改病人的床位和护士
            List<Sickbed> sickbeds=sickbedRepository.findByPatient(null);
            System.out.println(sickbeds.get(0).getSickbedId());

            Sickbed oldbed=sickbedRepository.findByPatient(patient).get(0);
            oldbed.setPatient(null);
            sickbedRepository.save(oldbed);

            Ward_nurse oldNurse=oldbed.getWardNurse();
            List<Patient> newpatients=oldNurse.getPatients();
            newpatients.remove(patient);
            oldNurse.setPatients(newpatients);
            int tmp=oldNurse.getPatientCount();
            tmp--;
            oldNurse.setPatientCount(tmp);
            ward_nurseRepository.save(oldNurse);

            //分配床位和护士
            for(int i=0;i<sickbeds.size();i++) {

                Sickbed newbed = sickbeds.get(i);
                Ward_nurse newnurse = newbed.getWardNurse();
                if(newnurse.getPatientCount()<max){
                    newbed.setPatient(patient);
                    sickbedRepository.save(newbed);


                    newpatients = newnurse.getPatients();
                    newpatients.add(patient);
                    newnurse.setPatients(newpatients);
                    newnurse.setPatientCount(newnurse.getPatientCount() + 1);
                    ward_nurseRepository.save(newnurse);

                    return "newCondition";
                }
                else
                   continue;

            }
            return "oldCondition";

        }else{
            Patient patient=patientRepository.findByPatientId(patientId);
            patient.setConditionRate(area);
            return "oldCondition";
        }


    }


    public List<Patient> findDischargedPatients(String treatment_area){
        int area=3;
        switch (treatment_area){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        if(area!=0)
            throw  new AuthErrorException(treatment_area);

        /*
        如何判断status为康复出院
        List<Patient> patients=patientRepository.findByTreatment_area(area);

        List<Patient> results=new ArrayList<>();
        for(Patient p:patients){
            List<Treat_record> records=treat_recordRepository.findByPatient(p);

            Collections.sort(records);
            System.out.println(records.get(0).getDate());
            System.out.println(records.get(1).getDate());
            //每天记录一次 因此按顺序测三次即为连续三天的体温
            int flag=1;
            for(int i=0;i<3;i++){
                Treat_record r=records.get(i);
                if(r.getTemperature()>=37.3) {
                    flag=0;
                    break;
                }
            }
            if(flag==1){
                //说明连续三天体温小于37.3

                //检查是否连续两次核酸结果为阴性
                List<Nucleic_acid_test> tests=nucleic_acid_testRepository.findByPatient(p);

                Collections.sort(records);
                int flag2=1;
                for(int i=0;i<2;i++){
                    Nucleic_acid_test t=tests.get(i);
                    if(t.getResult()==1) {
                        flag2=0;
                        break;
                    }
                }
                if(flag2==1)
                    results.add(p);
            }

        }

         */



        //或者直接找status为康复出院的患者

        return patientRepository.findByLiveState(0); //0康复出院 1在院治疗 2死亡

    }


    public List<Sickbed> getSickbeds(String treatment_area){
        int area=0;
        switch (treatment_area){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        List res=sickbedRepository.findByTreatmentArea(area);
        return res;

    }

    public void addNurse(Long nurseId,String treatment_area){
        int area=0;
        switch (treatment_area){
            case "mild":area=0;break;
            case "middle":area=1;break;
            case "severe":area=2;break;

        }
        Ward_nurse ward_nurse=ward_nurseRepository.findByWardNurseId(nurseId);
        if(ward_nurse.getPatientCount()==0&&ward_nurse.getPatients().size()==0) {

            ward_nurse.setTreatmentArea(area);
            ward_nurseRepository.save(ward_nurse);
        }
        else
            throw new AddNurseException();

    }



}
