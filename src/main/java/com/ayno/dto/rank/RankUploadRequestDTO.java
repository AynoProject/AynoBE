package com.ayno.dto.rank;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RankUploadRequestDTO {
    private String toolName;
    private Long rank;
    private Long score;
    private Long mauScore;
    private Long monthMauChangeRateScore;
    private Long avgStayTimeScore;
    private Long avgPagesPerVisitScore;
    private Long boundRateScore;
}
