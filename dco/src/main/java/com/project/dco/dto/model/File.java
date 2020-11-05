package com.project.dco.dto.model;

import com.project.dco.dto.model.base.EntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "files")
public class File extends EntityBase {

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_type")
    String fileType;

    @Column(name = "file_url")
    String fileUrl;

    String status;
}
