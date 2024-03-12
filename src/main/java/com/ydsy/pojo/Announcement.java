package com.ydsy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Announcement {
    private int announcementId;
    private int creatorId;
    private String announcementContent;
    private LocalDateTime createdAt;
}
