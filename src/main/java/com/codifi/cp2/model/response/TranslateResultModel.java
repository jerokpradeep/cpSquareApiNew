package com.codifi.cp2.model.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
public class TranslateResultModel {
    private String pageId;
    private List<PageTranslateModel> pageTranslateModel;
}
