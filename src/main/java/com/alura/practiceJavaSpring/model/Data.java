package com.alura.practiceJavaSpring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Represents data containing a list of BookData objects.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
        @JsonAlias("results") List<BookData> results) {
}
