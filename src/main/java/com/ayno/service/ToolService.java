package com.ayno.service;

import com.ayno.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;
}
