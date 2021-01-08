package com.pj.database_design.service;

import com.pj.database_design.domain.*;
import com.pj.database_design.exception.*;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private MessageRepository messageRepository;

    private PasswordEncoder passwordEncoder;


    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService,
                       PasswordEncoder passwordEncoder,
                       DoctorRepository doctorRepository, Emergency_nurseRepository emergency_nurseRepository, Head_nurseRepository head_nurseRepository,
                       Nucleic_acid_testRepository nucleic_acid_testRepository, PatientRepository patientRepository, SickbedRepository sickbedRepository,
                       Ward_nurseRepository ward_nurseRepository, Treat_recordRepository treat_recordRepository, SickroomRepository sickroomRepository,MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;

        this.doctorRepository = doctorRepository;
        this.emergency_nurseRepository = emergency_nurseRepository;
        this.head_nurseRepository = head_nurseRepository;
        this.nucleic_acid_testRepository = nucleic_acid_testRepository;
        this.patientRepository = patientRepository;
        this.sickroomRepository = sickroomRepository;
        this.sickbedRepository = sickbedRepository;
        this.ward_nurseRepository = ward_nurseRepository;
        this.treat_recordRepository = treat_recordRepository;
        this.messageRepository=messageRepository;
    }

    public Long login(String username, String password,String authority,String treatmentArea) {
        Integer area ;
        switch (treatmentArea) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;
            default: area=null;

        }
        User user=userRepository.findByName(username);
        String auth=((Authority) user.getAuthorities().iterator().next()).getAuthority();
        if(!auth.equals(authority))
            throw new AuthErrorException(authority);

        switch (auth){
            case "Doctor":
                if(!doctorRepository.findByUser(user).getTreatmentArea().equals(area)) {
                    throw new TreatmentAreaException();
                }
                break;
            case "Head_nurse":
                if(!head_nurseRepository.findByUser(user).getTreatmentArea().equals(area)) {
                    throw new TreatmentAreaException();
                }
                break;
            case "Ward_nurse":
                if(!ward_nurseRepository.findByUser(user).getTreatmentArea().equals(area)) {
                    throw new TreatmentAreaException();
                }
                break;
        }


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


        return user.getId();
    }

    public Long findAuthorityId(String name){
        User user=userRepository.findByName(name);
        Authority authority= (Authority) user.getAuthorities().iterator().next();
        System.out.println(authority.getAuthority());
        String auth=authority.getAuthority();
        switch (auth){
            case "Doctor":return doctorRepository.findByUser(user).getDoctorId();
            case "Emergency_nurse":return emergency_nurseRepository.findByUser(user).getEmergencyNurseId();
            case "Head_nurse":return head_nurseRepository.findByUser(user).getHeadNurseId();
            case "Ward_nurse":return ward_nurseRepository.findByUser(user).getWardNurseId();
        }
        return null;
    }

    public List<Patient> getPatients(String treatment) {
        int area = 0;
        switch (treatment) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        List res = patientRepository.findByTreatmentArea(area);
        return res;

    }

    public Head_nurse getHeadNurses(String treatment) {
        int area = 0;
        switch (treatment) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        Head_nurse res = head_nurseRepository.findByTreatmentArea(area);
        return res;
    }

    public List<Ward_nurse> getWardNurses(String treatment) {
        int area = 0;
        switch (treatment) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        List res = ward_nurseRepository.findByTreatmentArea(area);
        return res;

    }

    public String changePatientsLevel(Long patientId, String level) {
        //修改病人病情评级、生命状态（带动病人治疗区域变动、病人的主治医生和护士长更改为新的）
        //
        int area = 0;
        int max = 0;
        switch (level) {
            case "mild":
                area = 0;
                max = 3;
                break;
            case "middle":
                area = 1;
                max = 2;
                break;
            case "severe":
                area = 2;
                max = 1;
                break;

        }

        Patient patient = patientRepository.findByPatientId(patientId);



        //检查转入的区域是否有空的病床
        if (sickbedRepository.findByPatientAndTreatmentArea(null, area).size() != 0) {
            System.out.println("has empty beds!");


            List<Ward_nurse> nurses = ward_nurseRepository.findByTreatmentArea(area);
            for (Ward_nurse newnurse : nurses) {

                //检查护士是否有空余
                if (newnurse.getPatientCount() < max) {
                    Integer oldArea=patient.getTreatmentArea();

                    patient.setConditionRate(area);
                    patient.setTreatmentArea(area);

                    //更改病人的床位和护士

                    Sickbed oldbed = sickbedRepository.findByPatient(patient).get(0);
                    oldbed.setPatient(null);
                    Ward_nurse oldNurse = oldbed.getWardNurse();
                    oldbed.setWardNurse(null);
                    sickbedRepository.save(oldbed);

                    List<Patient> newpatients = oldNurse.getPatients();
                    newpatients.remove(patient);
                    oldNurse.setPatients(newpatients);
                    int tmp = oldNurse.getPatientCount();
                    tmp--;
                    oldNurse.setPatientCount(tmp);
                    ward_nurseRepository.save(oldNurse);

                    //分配床位和护士
                    List<Sickbed> sickbeds = sickbedRepository.findByPatient(null);
                    //System.out.println(sickbeds.get(0).getSickbedId());
                    Sickbed newbed = sickbeds.get(0);
                    newbed.setPatient(patient);
                    newbed.setWardNurse(newnurse);
                    sickbedRepository.save(newbed);


                    newpatients = newnurse.getPatients();
                    newpatients.add(patient);
                    newnurse.setPatients(newpatients);
                    newnurse.setPatientCount(newnurse.getPatientCount() + 1);
                    ward_nurseRepository.save(newnurse);

                    patientRepository.save(patient);



                    //由于之前的旧床空出来了 看有无 隔离区中患者/病情等级与治疗区域不匹配的患者
                    //把他们挪入。
                    translateToRightArea(oldArea);



                    return "newCondition";
                } else
                    continue;


            }
            //都没有空余 改病情评级 不改治疗区域
            patient.setConditionRate(area);
            patientRepository.save(patient);
            return "oldCondition";
        } else {

            patient.setConditionRate(area);
            patientRepository.save(patient);
            return "oldCondition";
        }


    }

    public void translateToRightArea(Integer area){
        int max=-1;
        switch (area) {
            case 0:
                max = 3;
                break;
            case 1:
                max = 2;
                break;
            case 2:
                max = 1;
                break;

        }
        List<Sickbed> emptyBeds= sickbedRepository.findByPatientAndTreatmentArea(null, area);
        if(emptyBeds.size()==0){
            return;
        }
        List<Ward_nurse> nurses = ward_nurseRepository.findByTreatmentArea(area);
        List<Ward_nurse> canTakeNurses=new ArrayList<>();
        for(Ward_nurse w:nurses){
            if(w.getPatientCount()<max){
                canTakeNurses.add(w);
            }
        }
        if(canTakeNurses.size()==0)
            return;

        int i=0;
        List<Patient> quarantineAreaPatients=patientRepository.findByTreatmentAreaAndConditionRate(null,area);
        if(quarantineAreaPatients.size()!=0) {
            int min=Math.min(Math.min(emptyBeds.size(), canTakeNurses.size()),quarantineAreaPatients.size());
            for (; i < min ; i++) {
                //转移隔离区中的病人
                Patient patient=quarantineAreaPatients.get(i);
                Sickbed bed=emptyBeds.get(i);
                Ward_nurse nurse=canTakeNurses.get(i);
                patient.setTreatmentArea(area);
                patientRepository.save(patient);
                bed.setPatient(patient);
                bed.setWardNurse(nurse);
                sickbedRepository.save(bed);
                nurse.setPatientCount(nurse.getPatientCount()+1);
                List<Patient> tmp=nurse.getPatients();
                tmp.add(patient);
                nurse.setPatients(tmp);
                ward_nurseRepository.save(nurse);

                //向转入区域的护士长提示
                Message m1=new Message(head_nurseRepository.findByTreatmentArea(area).getUser(),area);
                DateFormat d4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                m1.setDate(new Date());
                m1.setPatient(patient);
                m1.setMessage("the patient whose id is "+patient.getPatientId()+" is now transferred from quarantine area to this treatment area. ");
                messageRepository.save(m1);
            }
        }

        if(emptyBeds.size()-i==0||canTakeNurses.size()-i==0)
            return;
        else{
            //说明三个中最小的是隔离区中患者数 即可以进一步选择 治疗区域与病情评级不匹配的患者
            for (int k=0;k<i;k++){
                emptyBeds.remove(k);
                canTakeNurses.remove(k);
            }

            List<Patient> inconsistentPatients = new ArrayList<>();
            List<Patient> tmpPatients=  patientRepository.findByConditionRate(area);
            for(Patient t:tmpPatients){
                if(!t.getConditionRate().equals(t.getTreatmentArea())){
                    inconsistentPatients.add(t);
                }
            }
            if(inconsistentPatients.size()==0)
                return;

            int min=Math.min(Math.min(emptyBeds.size(), canTakeNurses.size()),inconsistentPatients.size());
            i=0;
            for (; i < min ; i++) {
                Patient patient=inconsistentPatients.get(i);

                Integer oldarea=patient.getTreatmentArea();

                Sickbed bed=emptyBeds.get(i);
                Ward_nurse nurse=canTakeNurses.get(i);
                patient.setTreatmentArea(area);
                patientRepository.save(patient);
                bed.setPatient(patient);
                bed.setWardNurse(nurse);
                sickbedRepository.save(bed);
                nurse.setPatientCount(nurse.getPatientCount()+1);
                List<Patient> tmp=nurse.getPatients();
                tmp.add(patient);
                nurse.setPatients(tmp);
                ward_nurseRepository.save(nurse);

                //向转入区域的护士长提示
                Message m1=new Message(head_nurseRepository.findByTreatmentArea(area).getUser(),area);
                DateFormat d4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                m1.setDate(new Date());
                m1.setPatient(patient);

                String old="";
                switch (oldarea) {
                    case 0:
                        old="mild";
                        break;
                    case 1:
                        old="middle";
                        break;
                    case 2:
                        old="severe";
                        break;

                }

                m1.setMessage("the patient whose id is "+patient.getPatientId()+" is now transferred from "+old+" to this treatment area. ");
                messageRepository.save(m1);

            }


        }



    }



    public List<Patient> findDischargedPatients(String treatment_area) {
        int area = 3;
        switch (treatment_area) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        if (area != 0)
            throw new AuthErrorException(treatment_area);

        /*
        如何判断是否可以允许出院
        List<Patient> patients=patientRepository.findByTreatmentArea(area);

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


        //或者直接找允许出院的患者

        return patientRepository.findByIsAllowedDischarged(1); //0康复出院 1在院治疗 2死亡

    }


    public List<Sickbed> getSickbeds(String treatment_area) {
        int area = 0;
        switch (treatment_area) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        List res = sickbedRepository.findByTreatmentArea(area);
        return res;

    }

    public void addNurse(Long nurseId, String treatment_area) {
        int area = 0;
        switch (treatment_area) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }
        Ward_nurse ward_nurse = ward_nurseRepository.findByWardNurseId(nurseId);
        if (ward_nurse.getPatientCount() == 0 && ward_nurse.getPatients().size() == 0) {

            ward_nurse.setTreatmentArea(area);
            ward_nurseRepository.save(ward_nurse);
        } else
            throw new MoveNurseException();

    }

    public void addNucTest(Long patientId, Long doctorId, Integer conditionRate, Date date, Integer result) {
        //两次检测至少间隔24h
        Patient patient=patientRepository.findByPatientId(patientId);
        List<Nucleic_acid_test> tests=nucleic_acid_testRepository.findByPatient(patient);
        System.out.println(date);

        if(tests.size()!=0) {
            Collections.sort(tests);
            Nucleic_acid_test recently = tests.get(0);
            Date last = recently.getDate();
            System.out.println(recently.getDate());
            double cha = date.getTime() - last.getTime();
            double interval = cha * 1.0 / (1000.0 * 60.0 * 60.0);
            System.out.println(cha);
            System.out.println(interval);

            if (Math.abs(interval) < 24)
                throw new NucTestIntervalException();
        }

        Nucleic_acid_test test = new Nucleic_acid_test(result, conditionRate, patientRepository.findByPatientId(patientId),
                doctorRepository.findByDoctorId(doctorId), date);
        //System.out.println(patientRepository.findByPatientId(patientId).getPatientId());
        nucleic_acid_testRepository.save(test);


        if(nucleic_acid_testRepository.findByPatient(patient).size()>=2&&treat_recordRepository.findByPatient(patient).size()>=3&&isAllowedToDischarge(patient))
            patient.setIsAllowedDischarged(1);
    }

    public void deleteNurse(Long nurseId, String treatment_area) {
        int area = 0;
        switch (treatment_area) {
            case "mild":
                area = 0;
                break;
            case "middle":
                area = 1;
                break;
            case "severe":
                area = 2;
                break;

        }

        Ward_nurse ward_nurse = ward_nurseRepository.findByWardNurseId(nurseId);

        if (ward_nurse.getTreatmentArea() != area)
            throw new TreatmentAreaException();

        if (ward_nurse.getPatientCount() == 0 && ward_nurse.getPatients().size() == 0) {
            //treatment area ==null 的护士都是等着之后分配的
            ward_nurse.setTreatmentArea(null);
            ward_nurseRepository.save(ward_nurse);
        } else
            throw new MoveNurseException();

    }

    public void allowDischarge(Long patientId, Long doctorId) {
        Doctor doctor = doctorRepository.findByDoctorId(doctorId);
        if (doctor.getTreatmentArea() != 0) {
            throw new TreatmentAreaException();
        }

        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient.getIsAllowedDischarged() != 1)
            throw new DischargedAllowException(patientId);
        else {
            patient.setLiveState(0);
            patientRepository.save(patient);


            //挪出空床
            Integer oldArea=patient.getTreatmentArea();
            Sickbed sickbed = sickbedRepository.findByPatient(patient).get(0);
            sickbed.setPatient(null);

            Ward_nurse ward_nurse = sickbed.getWardNurse();
            sickbed.setWardNurse(null);
            sickbedRepository.save(sickbed);


            List<Patient> hasPatients = ward_nurse.getPatients();
            hasPatients.remove(patient);
            ward_nurse.setPatients(hasPatients);
            ward_nurse.setPatientCount(ward_nurse.getPatientCount() - 1);
            ward_nurseRepository.save(ward_nurse);

            translateToRightArea(oldArea);

        }


    }

    public String initialRecord(String name, Integer age, String gender, Integer conditionRate) {
        Patient patient = new Patient();
        patient.setName(name);
        patient.setAge(age);
        patient.setLiveState(1);
        patient.setConditionRate(conditionRate);
        patient.setGender(gender);
        patientRepository.save(patient);

        int max = 0;
        switch (conditionRate) {
            case 0:
                max = 3;
                break;
            case 1:
                max = 2;
                break;
            case 2:
                max = 1;
                break;

        }

        //检查转入的区域是否有空的病床
        if (sickbedRepository.findByPatientAndTreatmentArea(null, conditionRate).size() != 0) {

            List<Ward_nurse> nurses = ward_nurseRepository.findByTreatmentArea(conditionRate);
            for (Ward_nurse newnurse : nurses) {
                System.out.println(newnurse.getWardNurseId());
                //检查护士是否有空余
                if (newnurse.getPatientCount() < max) {

                    patient.setTreatmentArea(conditionRate);

                    //分配床位和护士
                    List<Sickbed> sickbeds = sickbedRepository.findByPatient(null);
                    //System.out.println(sickbeds.get(0).getSickbedId());
                    Sickbed newbed = sickbeds.get(0);
                    newbed.setPatient(patient);
                    newbed.setWardNurse(newnurse);
                    sickbedRepository.save(newbed);


                    List<Patient> newpatients = newnurse.getPatients();
                    System.out.println(newnurse.getPatients());
                    newpatients.add(patient);
                    newnurse.setPatients(newpatients);
                    newnurse.setPatientCount(newnurse.getPatientCount() + 1);
                    ward_nurseRepository.save(newnurse);
                    patientRepository.save(patient);

                    patientRepository.save(patient);
                    return "success";

                } else
                    continue;


            }
            //都没有空余 改病情评级 不改治疗区域
            return "All busy. The patient is waiting...";
        }
        return "All busy. The patient is waiting...";
    }


    public void dailyRecord(Long patientId,Long nurseId,Float temperature,String symptom,
                            Integer result,Integer liveState,Date date){

        System.out.println("begin");


        Patient patient=patientRepository.findByPatientId(patientId);
        Ward_nurse nurse=ward_nurseRepository.findByWardNurseId(nurseId);

        Treat_record record=new Treat_record();
        record.setPatient(patient);
        record.setWardNurse(nurse);
        record.setTemperature(temperature);
        record.setSymptom(symptom);
        record.setResult(result);
        record.setLiveState(liveState);
        record.setDate(date);
        treat_recordRepository.save(record);

        //修改病人的状态
        patient.setLiveState(liveState);
        Integer oldArea=patient.getTreatmentArea();

        if(patient.getConditionRate()==0&&liveState==1){
            //如果是轻症患者且在院治疗中 检查是否满足条件可以出院了
            if(nucleic_acid_testRepository.findByPatient(patient).size()>=2&&treat_recordRepository.findByPatient(patient).size()>=3&&isAllowedToDischarge(patient))
                patient.setIsAllowedDischarged(1);
        }

        if(liveState==2){
            //该患者于今日死亡 相应的病床和护士都空出来

            //挪出空床
            Sickbed sickbed = sickbedRepository.findByPatient(patient).get(0);
            sickbed.setPatient(null);

            Ward_nurse ward_nurse = sickbed.getWardNurse();
            sickbed.setWardNurse(null);
            sickbedRepository.save(sickbed);


            List<Patient> hasPatients = ward_nurse.getPatients();
            hasPatients.remove(patient);
            ward_nurse.setPatients(hasPatients);
            ward_nurse.setPatientCount(ward_nurse.getPatientCount() - 1);
            ward_nurseRepository.save(ward_nurse);

            translateToRightArea(oldArea);
        }


        patientRepository.save(patient);
    }

    public boolean isAllowedToDischarge(Patient patient){


            List<Treat_record> records=treat_recordRepository.findByPatient(patient);

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
                List<Nucleic_acid_test> tests=nucleic_acid_testRepository.findByPatient(patient);

                Collections.sort(tests);
                int flag2=1;
                for(int i=0;i<2;i++){
                    Nucleic_acid_test t=tests.get(i);
                    if(t.getResult()==1) {
                        flag2=0;
                        break;
                    }

                }
                if(flag2==1)
                    return  true;
            }

            return false;
    }



    public List<Patient> getResponsibledPatients(Long nurseId){
        Ward_nurse ward_nurse=ward_nurseRepository.findByWardNurseId(nurseId);
        return ward_nurse.getPatients();
    }

    public void modifyInfos(Long userId,String name,Integer age,String gender,String pwd){


        Optional<User> userOp=userRepository.findById(userId);
        User user=userOp.get();
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        if(!pwd.equals(user.getPassword()))
            user.setPwd(passwordEncoder.encode(pwd));
        userRepository.save(user);
    }

    public List<Nucleic_acid_test> browseNucTests(Long patientId){
        Patient patient=patientRepository.findByPatientId(patientId);
        List<Nucleic_acid_test> tests=nucleic_acid_testRepository.findByPatient(patient);

        Collections.sort(tests);
        //System.out.println("1 "+tests.get(0).getDate());
        //System.out.println("2 "+tests.get(1).getDate());
        return  tests;

    }

    public List<Treat_record> browseTreatRecords(Long patientId){
        Patient patient=patientRepository.findByPatientId(patientId);
        List<Treat_record> tests=treat_recordRepository.findByPatient(patient);

        Collections.sort(tests);
        //System.out.println("1 "+tests.get(0).getDate());
        //System.out.println("2 "+tests.get(1).getDate());
        return  tests;

    }


    public List<Message> getMessages(Long userId){
        User user=userRepository.findById(userId).get();
        List<Message> messages=messageRepository.findByUser(user);
        Collections.sort(messages);
        return messages;

    }

    public List<Patient> getAllPatients(){
        List<Patient> list=new ArrayList<>();
        Iterable<Patient> patients=patientRepository.findAll();
        patients.forEach(single->{list.add(single);});
       return list;
    }


}