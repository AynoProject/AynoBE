package com.ayno.repository;

import com.ayno.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    @Modifying
    @Query("UPDATE Rank r " +
            "SET r.isLatest = false " +
            "WHERE r.tool.toolName = :toolName AND r.isLatest = true")
    void updateIsLatestFalse(@Param("toolName") String toolName);

    @Modifying
    @Query("DELETE " +
            "FROM Rank r " +
            "WHERE r.isLatest = true" )
    int deleteAllByIsLatestTrue();

    @Query("SELECT r FROM Rank r " +
            "WHERE r.createdAt IN (" +
            "    SELECT MAX(r2.createdAt) FROM Rank r2 GROUP BY r2.tool.toolName" +
            ")")
    List<Rank> findLatestPerTool();

}
