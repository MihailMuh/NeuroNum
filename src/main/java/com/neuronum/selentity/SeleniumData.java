package com.neuronum.selentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "seleniums")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeleniumData {
    @Id
    @Size(min = 128, max = 128, message = "Key is not valid")
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "CHAR(128)")
    private String id;

    @NotNull
    @Column(name = "interrupted", nullable = false)
    private boolean interrupted;

    @NotBlank(message = "Password must be not blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Login must be not blank")
    @Column(name = "login", nullable = false)
    private String login;
}
