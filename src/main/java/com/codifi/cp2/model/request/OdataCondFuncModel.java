package com.codifi.cp2.model.request;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class OdataCondFuncModel {
    private List<OdataConditionModel> conditionArray;
}
