package fr.btn.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
    private Integer id;
    private String name;
    private String email;
    private int monthlyAllocation;
    private LocalDate createdDate;
    private String status;
}
