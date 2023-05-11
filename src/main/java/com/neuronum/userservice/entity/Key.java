package com.neuronum.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "keys")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Key {
    @Id
    @Length(min = 128, max = 128, message = "Key is not valid")
    @Column(name = "value", nullable = false, unique = true, columnDefinition = "CHAR(128)")
    private String value;
}
