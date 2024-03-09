package com.ydsy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobApplication {
    private int jobApplicationId;
    private int jobApplicantId;
    private int applicationJob;
    private int jobApproverId;
    private int jobApprovalStatus;
    private Timestamp jobCreatedAt;
}
