package com.ayno.service;

import com.ayno.config.exception.CustomException;
import com.ayno.config.security.CustomUserDetails;
import com.ayno.dto.rank.DeleteCsvResponseDTO;
import com.ayno.dto.rank.GetCsvHistoryResponseDTO;
import com.ayno.dto.rank.RankUploadRequestDTO;
import com.ayno.dto.rank.RankUploadResponseDTO;
import com.ayno.entity.Log;
import com.ayno.entity.Rank;
import com.ayno.entity.Tool;
import com.ayno.entity.enums.ActionType;
import com.ayno.entity.enums.Category;
import com.ayno.entity.enums.TargetType;
import com.ayno.repository.AdminRepository;
import com.ayno.repository.LogRepository;
import com.ayno.repository.RankRepository;
import com.ayno.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

    private final ToolRepository toolRepository;
    private final RankRepository rankRepository;
    private final LogRepository logRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public RankUploadResponseDTO uploadCsv(MultipartFile file, CustomUserDetails userDetails) {
        List<RankUploadRequestDTO> aiToolList = parseCsv(file);

        for (RankUploadRequestDTO dto : aiToolList) {
            // 1. 툴 조회 or 자동 생성
            Tool tool = toolRepository.findByToolName(dto.getToolName())
                    .orElseGet(() -> {
                        Tool newTool = Tool.builder()
                                .toolName(dto.getToolName())
                                .category(Category.NONE)
                                .build();
                        return toolRepository.save(newTool);
                    });

            // 2. 기존 최신 Rank false 처리 (toolName 기준)
            rankRepository.updateIsLatestFalse(tool.getToolName());

            // 3. 새 랭킹 등록 (isLatest = true, createdAt은 자동)
            Rank rank = Rank.builder()
                    .tool(tool)
                    .rank(dto.getRank())
                    .score(dto.getScore())
                    .mauScore(dto.getMauScore())
                    .monthMauChangeRateScore(dto.getMonthMauChangeRateScore())
                    .avgStayTimeScore(dto.getAvgStayTimeScore())
                    .avgPagesPerVisitScore(dto.getAvgPagesPerVisitScore())
                    .boundRateScore(dto.getBoundRateScore())
                    .isLatest(true)
                    .build();

            rankRepository.save(rank);
        }

        // 로그 기록 추가
        String fileLabel = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.M'M' dd일 HH:mm:ss")) + " AI Ranking.CSV";
        saveRankLog(userDetails, fileLabel, ActionType.UPLOAD);


        return new RankUploadResponseDTO("랭킹 등록 완료");
    }

    /**
     * CSV 파싱 메소드
     */
    private List<RankUploadRequestDTO> parseCsv(MultipartFile file) {
        List<RankUploadRequestDTO> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser parser = CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                try {
                    RankUploadRequestDTO dto = new RankUploadRequestDTO();
                    dto.setToolName(record.get("toolName"));
                    dto.setRank(Long.parseLong(record.get("rank")));
                    dto.setScore(Long.parseLong(record.get("score")));
                    dto.setMauScore(Long.parseLong(record.get("mauScore")));
                    dto.setMonthMauChangeRateScore(Long.parseLong(record.get("monthMauChangeRateScore")));
                    dto.setAvgStayTimeScore(Long.parseLong(record.get("avgStayTimeScore")));
                    dto.setAvgPagesPerVisitScore(Long.parseLong(record.get("avgPagesPerVisitScore")));
                    dto.setBoundRateScore(Long.parseLong(record.get("boundRateScore")));
                    list.add(dto);
                } catch (Exception e) {
                    throw new CustomException("CSV_PARSE_ERROR", "CSV 파싱 실패 - 레코드: " + record.toString());
                }
            }

        } catch (IOException e) {
            throw new CustomException("FILE_READ_ERROR", "CSV 파일 읽기 실패: " + e.getMessage());
        }

        return list;
    }

    @Transactional
    public DeleteCsvResponseDTO deleteLatestCsvUpload(CustomUserDetails userDetails) {
        // 1. 모든 최신 랭킹 데이터 삭제
        int deleted = rankRepository.deleteAllByIsLatestTrue();
        if (deleted == 0) {
            throw new CustomException("DELETE_FAIL", "삭제된 랭킹이 없습니다.");
        }

        // 2. 툴별로 가장 최근 Rank 찾아서 isLatest 다시 true
        List<Rank> latestPerTool = rankRepository.findLatestPerTool();
        if (latestPerTool.isEmpty()) {
            throw new CustomException("RESTORE_FAIL", "복구할 데이터가 없습니다.");
        }

        for (Rank rank : latestPerTool) {
            rank.setIsLatest(true);
        }

        // 로그 기록 추가
        String fileLabel = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.M'M' dd일 HH:mm:ss")) + " AI Ranking.CSV";
        saveRankLog(userDetails, fileLabel, ActionType.DELETE);

        return new DeleteCsvResponseDTO("삭제 및 복구 완료", latestPerTool.size());

    }

    public List<GetCsvHistoryResponseDTO> getRankHistory(){
        return logRepository.findActiveRankUploadsAsDTO();
    }

    private void saveRankLog(CustomUserDetails userDetails, String fileLabel, ActionType actionType) {
        logRepository.save(Log.builder()
                .admin(adminRepository.findById(userDetails.getUsername())
                        .orElseThrow(() -> new CustomException("NOT_FOUND", "관리자 정보가 없습니다.")))
                .actionType(actionType)
                .targetType(TargetType.RANK)
                .targetName(fileLabel) // 예: "2025.7M AI Ranking.CSV"
                .build());
    }
}
