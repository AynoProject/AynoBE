package com.ayno.repository;

import com.ayno.dto.rank.GetCsvHistoryResponseDTO;
import com.ayno.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {
    @Query("""
    SELECT new com.ayno.dto.rank.GetCsvHistoryResponseDTO(
        l.createdAt, l.targetName)
    FROM Log l
    WHERE l.targetType = 'RANK'
      AND l.actionType = 'UPLOAD'
      AND NOT EXISTS (
          SELECT 1 FROM Log d
          WHERE d.targetType = 'RANK'
            AND d.actionType = 'DELETE'
            AND d.targetName = l.targetName
      )
    ORDER BY l.createdAt DESC
    """)
    List<GetCsvHistoryResponseDTO> findActiveRankUploadsAsDTO();

}
