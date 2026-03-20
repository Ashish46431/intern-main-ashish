package com.example.demo.dto;

import com.example.demo.model.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStausDto {
    @NotNull
    private Long id;

    private Status status;
}
