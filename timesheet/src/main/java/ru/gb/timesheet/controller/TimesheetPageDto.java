package ru.gb.timesheet.controller;


import lombok.Data;

@Data
public class TimesheetPageDto {

    private String projectId;
    private String projectName;
    private String id;
    private String minutes;
    private String createdAt;

}
