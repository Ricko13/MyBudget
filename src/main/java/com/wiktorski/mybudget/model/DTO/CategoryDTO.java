package com.wiktorski.mybudget.model.DTO;

import com.wiktorski.mybudget.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO implements Serializable {
    private int id;
    private String name;

    public static CategoryDTO of(Category cat){
        return new CategoryDTO(cat.getId(),cat.getName());
    }
}
