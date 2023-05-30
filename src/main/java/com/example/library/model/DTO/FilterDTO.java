package com.example.library.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO {
    private String sort;
    private Integer authorId;
    private Integer categoryId;
    private String keyword;
}
