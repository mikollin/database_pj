package com.pj.database_design;

import com.pj.database_design.domain.*;
import com.pj.database_design.repository.*;

import com.pj.database_design.service.JwtUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                                        JwtUserDetailsService jwtUserDetailsService) {

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Create authorities if not exist.
                Authority adminAuthority = getOrCreateAuthority("Doctor", authorityRepository);
                getOrCreateAuthority("Ward_nurse", authorityRepository);
                getOrCreateAuthority("Head_nurse", authorityRepository);

                Doctor d1=new Doctor(0,"zhangyi","male","1111111",23);
                Doctor d2=new Doctor(1,"zhanger","male","222222",34);
                Doctor d3=new Doctor(2,"zhangsan","female","3333333",45);
                doctorRepository.save(d1);
                doctorRepository.save(d2);
                doctorRepository.save(d3);

                Head_nurse h1=new Head_nurse(0,"wangyi","female","wangyi",56);
                Head_nurse h2=new Head_nurse(1,"wanger","female","wanger",36);
                Head_nurse h3=new Head_nurse(2,"wangsan","female","wangsan",46);
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


                String name="chen";
                for(int i=0;i<20;i++){
                    name+="ha";
                    Patient p=new Patient(1,name,"male",1,45-i);
                    p.setLiveState(1);
                    patientRepository.save(p);

                }

                name="wa";
                for(int i=0;i<10;i++){
                    name+="ha";
                    Patient p=new Patient(2,name,"female",1,45-i);
                    p.setLiveState(1);
                    patientRepository.save(p);

                }

                name="la";
                for(int i=0;i<10;i++){
                    name+="ha";
                    Patient p=new Patient(0,name,"female",1,45-i);
                    p.setLiveState(1);
                    patientRepository.save(p);

                }
                List<Patient> patientList=new ArrayList<>();
                List<Patient> list=patientRepository.findByTreatmentArea(0);
                for(int k=0;k<3;k++)
                    patientList.add(list.get(k));
                Ward_nurse ward_nurse=new Ward_nurse(0,3,"lisi","male","1111",38);
                ward_nurse.setPatients(patientList);
                ward_nurseRepository.save(ward_nurse);

                List<Sickbed> s=sickbedRepository.findByTreatmentArea(0);
                for(int k=0;k<3;k++){
                    Sickbed tmp=s.get(k);
                    tmp.setPatient(patientList.get(k));
                    tmp.setWardNurse(ward_nurse);
                    sickbedRepository.save(tmp);
                }

                ward_nurseRepository.save(new Ward_nurse(0,0,"lisisi","feale","1111",24));



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
