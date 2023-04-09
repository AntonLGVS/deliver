package com.deliver.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrackedDTO {
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private String modifiedBy;
    @JsonIgnore
    private Date modifiedAt;
}
