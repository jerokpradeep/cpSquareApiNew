package com.codifi.cp2.model.request;

import java.util.List;
import org.springframework.stereotype.Component;

import lombok.*;

@Component
@Getter
@Setter
public class OdataCdposJsonModel {
    public List<CdposJsonModel> cdposJson;
}
