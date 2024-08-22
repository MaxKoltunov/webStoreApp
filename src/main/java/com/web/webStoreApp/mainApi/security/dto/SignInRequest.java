package com.web.webStoreApp.mainApi.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Номер телефона", example = "+11234567890")
    @NotBlank(message = "Номер телефона не может быть пустым")
    private String phone_number;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
