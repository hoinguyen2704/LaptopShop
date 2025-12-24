package com.hoz.laptopshop.dto.request;

import com.hoz.laptopshop.service.validator.RegisterChecked;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RegisterChecked
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {
    @Size(min = 3, message = "FirstName phải có tối thiểu 3 ký tự")
    private String firstName;

    private String lastName;

    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    private String password;

    @Size(min = 3, message = "confirmPassword phải có tối thiểu 3 ký tự")
    private String confirmPassword;

}
