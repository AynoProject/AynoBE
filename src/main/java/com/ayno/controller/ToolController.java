package com.ayno.controller;

import com.ayno.dto.common.Response;
import com.ayno.dto.tool.ShowToolDetailsResponseDTO;
import com.ayno.dto.tool.ShowToolResponseDTO;
import com.ayno.dto.tool.UpdateToolRequestDTO;
import com.ayno.dto.tool.UpdateToolResponseDTO;
import com.ayno.service.ToolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @Operation(
            summary = "AI 툴 정보 및 랭킹 가져오기",
            description = "AI 툴 정보 및 랭킹 가져오기"
    )
    @GetMapping("/tool")
    public ResponseEntity<Response<List<ShowToolResponseDTO>>> showToolInfor(){
        return ResponseEntity.ok(Response.success(toolService.getToolAllInfor()));
    }

    @Operation(
            summary = "AI 툴 정보 및 랭킹 상세정보 가져오기",
            description = "AI 툴 정보 및 랭킹 상세정보 가져오기"
    )
    @GetMapping("/tool/{toolName}")
    public ResponseEntity<Response<ShowToolDetailsResponseDTO>> showToolDetailsInfor(
            @PathVariable String toolName
    ){
        return ResponseEntity.ok(Response.success(toolService.getToolDetailsInfor(toolName)));
    }

    @Operation(
            summary = "AI 툴 정보 수정하기",
            description = "AI 툴 정보 수정하기"
    )
    @PutMapping("/admin/tool/{toolName}")
    public ResponseEntity<Response<UpdateToolResponseDTO>> updateToolInfor(
            @PathVariable String toolName,
            @RequestBody UpdateToolRequestDTO request
    ){
        return ResponseEntity.ok(Response.success(toolService.updateToolInfor(toolName, request)));
    }


}