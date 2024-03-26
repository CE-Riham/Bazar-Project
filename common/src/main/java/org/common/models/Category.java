package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Category {

    @NotBlank(message = "ID cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    @NotBlank(message = "Category name cannot be blank")
    @CsvColumn(name = "Name")
    private String name;

    private List<Book> booksList;

}
