package com.neuronum.familia.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;

@Entity
@Table(name = "calls")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Call {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String text;

    @NotEmpty
    @Column(name = "date", nullable = false)
    private String date;

    @NotEmpty
    @Column(name = "time", nullable = false)
    private String time;

    @NotEmpty
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "analysis")
    private String analysis;

    @Type(JsonType.class)
    @Column(name = "tags", columnDefinition = "jsonb")
    private Map<String, String> tags;
}
