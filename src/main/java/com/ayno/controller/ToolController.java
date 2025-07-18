package com.ayno.controller;

import com.ayno.service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

}