package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".ARTICLE_TYPE")
public class ArticleTypeEntity extends CommonEntity {
    @Column(name = "CODE")
    private String code;
    @Column(name = "FIELD")
    private String field;
}
