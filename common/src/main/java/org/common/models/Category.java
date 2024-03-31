package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;

/**
 * This class represents a Category entity in the application.
 * It uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses validation annotations to ensure that the fields meet the requirements.
 */
@Data
@RequiredArgsConstructor
public class Category {

    /**
     * The ID of the category. It cannot be blank.
     */
    @NotBlank(message = "ID cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    /**
     * The name of the category. It cannot be blank.
     */
    @NotBlank(message = "Category name cannot be blank")
    @CsvColumn(name = "Name")
    private String name;

}