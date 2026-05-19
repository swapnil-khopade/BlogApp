package com.devsk.blog.domain.dtos;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagRequest {

    @NotEmpty(message = "At least one tag name is required")
    @Size(max = 10, message = "Maximum {max} tags allowed")

    private Set<
        @Size(min = 2, max = 30)
        @Pattern(regexp = "^\\w+$", message = "Only letters, digits, and underscore allowed")
            String> names;
}
