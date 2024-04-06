package com.example.jpa;

import com.example.jpa.entities.Role;
import com.example.jpa.entities.User;
import com.example.jpa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class JpaApplication {

    public static void main(String[] args) {

        SpringApplication.run(JpaApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UserService userService){

        return args -> {
         User u =    new User();
         u.setUsername("user1");
         u.setPassword("123456");

         userService.addNewUser(u);

         User u2 =    new User();
         u.setUsername("user2");
         u.setPassword("1236789");
         userService.addNewUser(u2);

         Stream.of("STUDENT","USER","ADMIN").forEach(r->{
             Role role1=    new Role();
             role1.setRoleName(r);
             userService.addNewRole(role1);
         });

         userService.addRoleToUser("user1","STUDENT");
         userService.addRoleToUser("user1","USER");
         userService.addRoleToUser("admin","USER");
         userService.addRoleToUser("admin","ADMIN");

         try {
             User user= userService.authenticate("user1","123456");
             System.out.println(user.getUserId());
             System.out.println(user.getUsername());
             System.out.println("Roles=>");
             user.getRoles().forEach(r->{
                 System.out.println("Roles=>"+r);
             });
         }
         catch (Exception e){
             e.printStackTrace();

            }


        };
    }

}
