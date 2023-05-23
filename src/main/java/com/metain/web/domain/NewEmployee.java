package com.metain.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEmployee {
    private Long newId;
    private String newName;
    private String newIdnum;
    private String newPhone;
    private String newAddr;
    private String newEmail;
    private String newDept;
}
