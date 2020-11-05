package com.project.dco.dto.model.base;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    Integer id;

    @Column(name = "create_on")
    String createOn;

    @Column(name = "update_on")
    String updateOn;
}
