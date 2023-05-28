package com.example.library.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO {
    private Boolean bestSeller;
    private Boolean releaseDateSort;
    private Boolean priceSortIncrease;
}
