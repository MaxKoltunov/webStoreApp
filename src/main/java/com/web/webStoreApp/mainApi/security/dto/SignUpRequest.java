package com.web.webStoreApp.mainApi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя", example = "Имя")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Schema(description = "Номер телефона", example = "+11234567890")
    @NotBlank(message = "Номер телефона не может быть пустым")
    private String phone_number;

    @Schema(description = "Дата рождения", example = "YYYY-MM-DD")
    @NotNull(message = "Дата рождения не может быть пустым")
    private Date birthDay;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
