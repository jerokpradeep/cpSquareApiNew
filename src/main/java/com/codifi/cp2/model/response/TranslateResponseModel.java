package com.codifi.cp2.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslateResponseModel {
    private Long id;
    private String textName;
    private String description;
    private Long pageId;
    private Long textId;
    private Long languageId;
}
