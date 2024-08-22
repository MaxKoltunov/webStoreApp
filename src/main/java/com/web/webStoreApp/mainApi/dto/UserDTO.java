package com.web.webStoreApp.mainApi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private Date birthDay;

    private String phoneNumber;

    private String levelName;

    private boolean existing;

    private String roleName;

    private String password;
}
