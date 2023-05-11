package com.neuronum.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
    @Id
    @Size(min = 128, max = 128, message = "Key is not valid")
    @Column(name = "access_key", nullable = false, unique = true, columnDefinition = "CHAR(128)")
    private String accessKey;

    @Email(message = "Email is not valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "User name must be not blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "User surname must be not blank")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotBlank(message = "Organization must be not blank")
    @Column(name = "organization", nullable = false)
    private String organization;
}
