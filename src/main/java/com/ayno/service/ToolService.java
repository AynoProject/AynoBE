package com.ayno.service;

import com.ayno.config.exception.CustomException;
import com.ayno.dto.tool.ShowToolDetailsResponseDTO;
import com.ayno.dto.tool.ShowToolResponseDTO;
import com.ayno.dto.tool.UpdateToolRequestDTO;
import com.ayno.dto.tool.UpdateToolResponseDTO;
import com.ayno.entity.Tool;
import com.ayno.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<ShowToolResponseDTO> getToolAllInfor(){
        return toolRepository.findAllToolAsDTO()
                .orElseThrow(() -> new CustomException("TOOL_NOT_FOUND", "툴이 존재하지 않습니다"));
    }

    public ShowToolDetailsResponseDTO getToolDetailsInfor(String toolName){
        return toolRepository.findToolDetailsAsDTO(toolName)
                .orElseThrow(() -> new CustomException("TOOL_NOT_FOUND", "해당 툴이 존재하지 않습니다"));
    }

    @Transactional
    public UpdateToolResponseDTO updateToolInfor(String toolName, UpdateToolRequestDTO request){
        Tool tool = toolRepository.findByToolName(toolName)
                .orElseThrow(() -> new CustomException("TOOL_NOT_FOUND", "해당 툴이 존재하지 않습니다"));

        tool.updateToolInfor(request.getToolName(), request.getLogoUrl(), request.getCategory(),
                request.getToolLink(), request.getToolInfor(), request.getIsFree());

        return new UpdateToolResponseDTO("해당 툴 정보가 성공적으로 수정되었습니다");
    }
}
