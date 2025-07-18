package com.ayno.dto.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCsvHistoryResponseDTO {
    private LocalDateTime uploadedAt;
    private String fileName;
}
