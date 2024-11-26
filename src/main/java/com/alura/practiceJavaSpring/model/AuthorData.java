package com.alura.practiceJavaSpring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents author information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") String yearOfBirth
) {
}
