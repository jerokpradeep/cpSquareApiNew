package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".NOTE_LIST")
public class NoteListEntity extends CommonEntity {
    @Column(name = "NOTE")
    private String note;
    @Column(name = "MESSAGE_ID")
    private String messageId;
    @Column(name = "type")
    private String type;
}
