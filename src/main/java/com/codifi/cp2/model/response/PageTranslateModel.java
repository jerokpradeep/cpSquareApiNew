package com.codifi.cp2.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageTranslateModel {
    private String textId;
    private List<TranslateResponseModel> languages;
}
