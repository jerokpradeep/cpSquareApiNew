package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".ARTICLE_HIERARCHY")
public class ArticleHierarchyEntity extends CommonEntity {
    @Column(name = "NAME")
    private String name;
}
