package com.pj.database_design;

import com.pj.database_design.domain.*;
import com.pj.database_design.repository.*;

import com.pj.database_design.service.JwtUserDetailsService;
import com.pj.database_design.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class DatabaseDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseDesignApplication.class, args);
    }




    @Bean
    public CommandLineRunner dataLoader(DoctorRepository doctorRepository, Head_nurseRepository head_nurseRepository, Ward_nurseRepository ward_nurseRepository, Emergency_nurseRepository emergency_nurseRepository,
                                        UserRepository userRepository,AuthorityRepository authorityRepository,
                                        PatientRepository patientRepository,SickroomRepository sickroomRepository,SickbedRepository sickbedRepository,
                                        AuthenticationManager authenticationManager,Nucleic_acid_testRepository nucleic_acid_testRepository,Treat_recordRepository treat_recordRepository,
                                        JwtUserDetailsService jwtUserDetailsService,PasswordEncoder passwordEncoder) {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Create authorities if not exist.
                Authority adminAuthority = getOrCreateAuthority("Doctor", authorityRepository);
                getOrCreateAuthority("Head_nurse", authorityRepository);
                getOrCreateAuthority("Ward_nurse", authorityRepository);
                getOrCreateAuthority("Emergency_nurse", authorityRepository);

                UserService userService=new UserService(userRepository,authorityRepository,authenticationManager,jwtUserDetailsService,passwordEncoder,doctorRepository,
                        emergency_nurseRepository,head_nurseRepository,nucleic_acid_testRepository,patientRepository,sickbedRepository,ward_nurseRepository,treat_recordRepository,sickroomRepository);

                DateFormat d4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   //定义日期格式
                Date date = d4.parse("2021-01-01 19:20:40");
//                userService.addNucTest(24L,8L,0,d4.parse("2021-01-09 19:20:40"),0);
//                userService.addNucTest(24L,8L,0,d4.parse("2021-01-07 19:20:40"),0);
////                userService.dailyRecord(24L,28L,37.2f,"no",0,1,d4.parse("2021-01-01 19:20:40"));
//                userService.dailyRecord(24L,28L,37.1f,"no",0,1,d4.parse("2021-01-02 19:20:40"));
//                userService.dailyRecord(24L,28L,37.2f,"no",0,1,d4.parse("2021-01-03 19:20:40"));

                //System.out.println(userService.isAllowedToDischarge(patientRepository.findByPatientId(24L)));

//                userService.browseNucTests(24L);

/*
                Set<Authority> authorities=new HashSet<>();
                authorities.add(authorityRepository.findByAuthority("Doctor"));
                User u1=new User("zhangyi",passwordEncoder.encode("1111111"),23,"male",authorities);
                userRepository.save(u1);
                User u2=new User("zhanger",passwordEncoder.encode("2222222"),34,"male",authorities);
                userRepository.save(u2);
                User u3=new User("zhangsan",passwordEncoder.encode("3333333"),45,"female",authorities);
                userRepository.save(u3);


                Doctor d1=new Doctor(0,u1);
                Doctor d2=new Doctor(1,u2);
                Doctor d3=new Doctor(2,u3);
                doctorRepository.save(d1);
                doctorRepository.save(d2);
                doctorRepository.save(d3);

                authorities.clear();
                authorities.add(authorityRepository.findByAuthority("Head_nurse"));
                u1=new User("wangyi",passwordEncoder.encode("wangyi"),56,"female",authorities);
                userRepository.save(u1);
                u2=new User("wanger",passwordEncoder.encode("wanger"),36,"female",authorities);
                userRepository.save(u2);
                u3=new User("wangsan",passwordEncoder.encode("wangsan"),46,"female",authorities);
                userRepository.save(u3);
                Head_nurse h1=new Head_nurse(0,u1);
                Head_nurse h2=new Head_nurse(1,u2);
                Head_nurse h3=new Head_nurse(2,u3);
                head_nurseRepository.save(h1);
                head_nurseRepository.save(h2);
                head_nurseRepository.save(h3);


                Sickroom sr1=new Sickroom("302","第三号楼","3",0);
                Sickroom sr2=new Sickroom("202","第三号楼","2",1);
                Sickroom sr3=new Sickroom("402","第4号楼","4",2);
                sickroomRepository.save(sr1);
                sickroomRepository.save(sr2);
                sickroomRepository.save(sr3);
                for (int i=0;i<4;i++) {
                    Sickbed sickbed1 = new Sickbed(sr1);
                    sickbedRepository.save(sickbed1);
                }

                for (int i=0;i<2;i++) {
                    Sickbed sickbed1 = new Sickbed(sr2);
                    sickbedRepository.save(sickbed1);
                }


                    Sickbed sickbed1 = new Sickbed(sr3);
                    sickbedRepository.save(sickbed1);




                String name="wa";
                for(int i=0;i<3;i++){
                    name+="ha";
                    Patient p=new Patient(0,name,"female",0,45-i);
                    p.setLiveState(1);
                    patientRepository.save(p);

                }
                List<Patient> patientList=new ArrayList<>();
                List<Patient> list=patientRepository.findByTreatmentArea(0);
                for(int k=0;k<3;k++)
                    patientList.add(list.get(k));

                authorities.clear();
                authorities.add(authorityRepository.findByAuthority("Ward_nurse"));
                u1=new User("lisi",passwordEncoder.encode("wwwww"),38,"male",authorities);
                userRepository.save(u1);
                Ward_nurse ward_nurse=new Ward_nurse(0,u1);
                ward_nurse.setPatients(patientList);
                ward_nurse.setPatientCount(3);
                ward_nurseRepository.save(ward_nurse);

                List<Sickbed> s=sickbedRepository.findByTreatmentArea(0);
                for(int k=0;k<3;k++){
                    Sickbed tmp=s.get(k);
                    tmp.setPatient(patientList.get(k));
                    tmp.setWardNurse(ward_nurse);
                    sickbedRepository.save(tmp);
                }

                u1=new User("lisisi",passwordEncoder.encode("wwwww"),24,"female",authorities);
                userRepository.save(u1);
                ward_nurseRepository.save(new Ward_nurse(0,u1));


                //treatment =1
                u1=new User("wangsisi",passwordEncoder.encode("wwwww"),34,"female",authorities);
                userRepository.save(u1);
                Ward_nurse w1=new Ward_nurse(1,u1);

                s=sickbedRepository.findByTreatmentArea(1);
                Patient patient=new Patient(1,"xiaoming","male",1,22);
                patient.setLiveState(1);
                patientRepository.save(patient);

                Sickbed sickbed=s.get(0);

                sickbed.setPatient(patient);
                patientList.clear();
                patientList.add(patient);
                w1.setPatients(patientList);
                w1.setPatientCount(1);
                ward_nurseRepository.save(w1);
                sickbed.setWardNurse(w1);
                sickbedRepository.save(sickbed);


                //treatment =2
                u1=new User("chensisi",passwordEncoder.encode("wwwww"),34,"female",authorities);
                userRepository.save(u1);
                Ward_nurse w2=new Ward_nurse(2,u1);

                s=sickbedRepository.findByTreatmentArea(2);
                patient=new Patient(2,"xiaomei","male",2,22);
                sickbed=s.get(0);
                patient.setLiveState(1);
                patientRepository.save(patient);

                sickbed.setPatient(patient);
                patientList.clear();
                patientList.add(patient);
                w2.setPatients(patientList);
                w2.setPatientCount(w2.getPatientCount()+1);
                ward_nurseRepository.save(w2);
                sickbed.setWardNurse(w2);
                sickbedRepository.save(sickbed);

                //急诊护士
                authorities.clear();
                authorities.add(authorityRepository.findByAuthority("Emergency_nurse"));
                User e=new User("zhangshanshan",passwordEncoder.encode("zhangshanshan"),55,"female",authorities);
                userRepository.save(e);
                Emergency_nurse emergency_nurse=new Emergency_nurse(e);
                emergency_nurseRepository.save(emergency_nurse);
                e=new User("sunhaoren",passwordEncoder.encode("sssss"),43,"male",authorities);
                userRepository.save(e);
                emergency_nurse=new Emergency_nurse(e);
                emergency_nurseRepository.save(emergency_nurse);

*/
            }

            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText);
                    authorityRepository.save(authority);
                }
                return authority;
            }


        };
    }



}
