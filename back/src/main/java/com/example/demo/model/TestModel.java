package com.example.demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TestModel {



    @NotNull
    String name;

     @Email
             @Min(value = 5)
   String email;

}
