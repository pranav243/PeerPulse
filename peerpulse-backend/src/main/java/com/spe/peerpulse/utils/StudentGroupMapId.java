package com.spe.peerpulse.utils;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupMapId implements Serializable {

    private Long studentId;

    private Long groupId;
}
