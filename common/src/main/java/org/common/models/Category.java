package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Category {

    @NotBlank(message = "ID cannot be blank")
    private String id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private List<Book> booksList;

}
