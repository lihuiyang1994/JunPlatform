package com.jun.platform.controller;

import com.jun.platform.l2d_api.dto.Models;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("model")
public class L2dModelController {

    private final Models models;

    @GetMapping("get")
    public String getModelById(@RequestParam("id") String id) {
        String[] ids = id.split("-");
        Integer modelId = Integer.parseInt(ids[0]);
        Integer modelTexturesId = Integer.parseInt(ids[1]);
        return models.getModelResponseJson(modelId, modelTexturesId);
    }

    @GetMapping("switch")
    public String switchWaiFu(@RequestParam("id") Integer id) {
        return models.switchNextInfo(id);
    }

    @GetMapping("rand_textures")
    public String switchRandTextures(@RequestParam("id") String id) {
        String[] ids = id.split("-");
        Integer modelId = Integer.parseInt(ids[0]);
        Integer modelTexturesId = Integer.parseInt(ids[1]);
        return models.switchNextTextures(modelId, modelTexturesId);
    }

    public L2dModelController(Models models) {
        this.models = models;
    }

}
