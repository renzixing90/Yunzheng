package com.ydsy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInOut {
    private int signId;
    private int signType; // 1 签到 2 签退
    private int signUserId;
    private LocalDate signDate;
}
