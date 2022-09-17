package com.codifi.cp2.model.request;

import java.util.List;
import org.springframework.stereotype.Component;

import lombok.*;
import lombok.Setter;

@Component
@Getter
@Setter
public class OdataCdhdrJsonModel {
    private List<CdhdrJsonModel> cdhdrJson;
}
