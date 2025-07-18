package com.ayno.repository;

import com.ayno.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, String> {
    Optional<Tool> findByToolName(String nickName);
}
