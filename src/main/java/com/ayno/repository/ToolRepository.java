package com.ayno.repository;

import com.ayno.dto.tool.ShowToolDetailsResponseDTO;
import com.ayno.dto.tool.ShowToolResponseDTO;
import com.ayno.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, String> {
    Optional<Tool> findByToolName(String nickName);

    @Query("""
    SELECT new com.ayno.dto.tool.ShowToolResponseDTO(
        t.toolName,
        t.logoUrl,
        t.category,
        t.toolLink,
        t.toolInfor,
        t.isFree,
        r.rank,
        r.score
    )
    FROM Tool t
    JOIN Rank r ON r.tool = t
    WHERE r.isLatest = true
    ORDER BY r.rank ASC
    """)
    Optional<List<ShowToolResponseDTO>> findAllToolAsDTO();

    @Query("""
    SELECT new com.ayno.dto.tool.ShowToolDetailsResponseDTO(
        t.toolName,
        t.logoUrl,
        t.category,
        t.toolLink,
        t.toolInfor,
        t.isFree,
        r.rank,
        r.score,
        r.mauScore,
        r.monthMauChangeRateScore,
        r.avgStayTimeScore,
        r.avgPagesPerVisitScore,
        r.boundRateScore
    )
    FROM Tool t
    JOIN Rank r ON r.tool = t
    WHERE r.isLatest = true AND t.toolName = :toolName
    """)
    Optional<ShowToolDetailsResponseDTO> findToolDetailsAsDTO(@Param("toolName") String toolName);
}
