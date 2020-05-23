package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResultDto {

    private long profileId;
    private String sourceName;
    private String ownersName;
    private String tablesName;
    private String fieldName;
    private String nameDomain;

}
