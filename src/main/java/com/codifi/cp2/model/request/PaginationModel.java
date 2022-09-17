package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PaginationModel {
    private int limit;
    private int offset;
}
