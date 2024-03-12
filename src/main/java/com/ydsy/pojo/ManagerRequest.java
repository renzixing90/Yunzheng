package com.ydsy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManagerRequest {
    private String name;
    private int directionId;    //1 设计 2 全栈 3 Java 4 CPU&OS 5 数据科学
    private String stage;
    private int jobId;  // 1 学员 2 管理员 3 大总管
}
