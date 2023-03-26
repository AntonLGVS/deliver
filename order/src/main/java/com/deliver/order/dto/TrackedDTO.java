package com.deliver.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrackedDTO {

    private String createdBy;
    private Date createdAt;
    private String modifiedBy;
    private Date modifiedAt;
}
