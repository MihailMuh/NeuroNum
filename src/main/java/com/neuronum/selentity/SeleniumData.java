package com.neuronum.selentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeleniumData {
    @NotNull
    private boolean interrupted;

    @NotBlank(message = "Password must be not blank")
    private String password;

    @NotBlank(message = "Login must be not blank")
    private String login;
}
