package com.codifi.cp2.model.request;

import java.util.List;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class FTWReceiverJsonModel {
    private List<DistributionListModel> distributionList;
    private List<FTWReceiverGroupModel> ftwReceiverGroupModels;
}
