package com.jun.platform.l2d_api.dto;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Models {

    private final String MODEL_JSON = """
            {
                "models": [
                    "Potion-Maker/Pio",
                    "Potion-Maker/Tia",
                    "bilibili-live/22",
                    "bilibili-live/33",
                    "ShizukuTalk/shizuku-48,ShizukuTalk/shizuku-pajama",

                [
                    "HyperdimensionNeptunia/neptune_classic",
                    "HyperdimensionNeptunia/nepnep",
                    "HyperdimensionNeptunia/neptune_santa",
                    "HyperdimensionNeptunia/nepmaid",
                    "HyperdimensionNeptunia/nepswim",
                    "HyperdimensionNeptunia/noir_classic",
                    "HyperdimensionNeptunia/noir",
                    "HyperdimensionNeptunia/noir_santa",
                    "HyperdimensionNeptunia/noireswim",
                    "HyperdimensionNeptunia/blanc_classic",
                    "HyperdimensionNeptunia/blanc_normal",
                    "HyperdimensionNeptunia/blanc_swimwear",
                    "HyperdimensionNeptunia/vert_classic",
                    "HyperdimensionNeptunia/vert_normal",
                    "HyperdimensionNeptunia/vert_swimwear",
                    "HyperdimensionNeptunia/nepgear",
                    "HyperdimensionNeptunia/nepgear_extra",
                    "HyperdimensionNeptunia/nepgearswim",
                    "HyperdimensionNeptunia/histoire",
                    "HyperdimensionNeptunia/histoirenohover"
                ],
                    "KantaiCollection/murakumo"
                ],
                "messages": [
                    "来自 Potion Maker 的 Pio 酱 ~",
                    "来自 Potion Maker 的 Tia 酱 ~",
                    "来自 Bilibili Live 的 22 哦 ~",
                    "来自 Bilibili Live 的 33 的说",
                    "Shizuku Talk ！这里是 Shizuku ~",
                    "Nep! Nep! 超次元游戏：海王星 系列",
                    "艦隊これくしょん / 叢雲(むらくも)"
                ]
    };
    """;
    private List<List<String>> models;

    private final File modelFile;

    private final String INDEX_JSON_FILE = "index.json";
    private final String MODEL_JSON_FILE = "model.json";
    private final static String MODEL_DIR = "model";

    public Models() {
        String resourceInfo = Objects.requireNonNull(this.getClass().getClassLoader().getResource("static")).getFile();
        this.modelFile = new File(resourceInfo, "model");
        File[] subFiles = modelFile.listFiles();
        if (modelFile.exists() && modelFile.isDirectory() && subFiles != null) {
            extractModelsByPath(subFiles, modelFile);
        }
    }

    private void extractModelsByPath(File[] subFiles, File model) {
        // 获取子节点信息
        models = new ArrayList<>();
        for (File subFile : subFiles) {
            List<String> paths = new ArrayList<>();
            File[] files = subFile.listFiles();
            if (subFile.isDirectory() && files != null) {
                for (File file : files) {
                    String path = model.toURI().relativize(file.toURI()).getPath();
                    paths.add(path);
                }
            }
            models.add(paths);
        }
    }

    public String idToPath(Integer modelId, Integer modelTexturesId) {
        if (models.size() > modelId) {
            List<String> testUserIds = models.get(modelId);
            if (testUserIds.size() > modelTexturesId) {
                return testUserIds.get(modelTexturesId);
            }
        }
        return null;
    }

    public String getIndexJson(String path) {
        File jsonFile = new File(modelFile, path + INDEX_JSON_FILE);
        if (!jsonFile.exists()) {
            jsonFile = new File(modelFile, path + MODEL_JSON_FILE);
        }
        return fileToString(jsonFile);
    }

    private String fileToString(File jsonFile) {
        if (jsonFile == null) return null;
        StringBuilder builder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(jsonFile, StandardCharsets.UTF_8);
            BufferedReader bReader = new BufferedReader(fileReader);
            String line;
            while ((line = bReader.readLine()) != null) {
                builder.append(line);
            }
            bReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public String getModelResponseJson(Integer modelId, Integer mtUid) {
        String path = idToPath(modelId, mtUid);
        if (path == null) return null;
        String indexJson = getIndexJson(path);
        if (StringUtils.isBlank(indexJson)) return null;
        JSONObject indexObj = JSONObject.parseObject(indexJson);
        appendModelPath(indexObj.getJSONArray("textures"), path);
        appendPathToObj(indexObj, "model", path);
        appendPathToObj(indexObj, "physics", path);
        appendPathToObj(indexObj, "pose", path);
        appendModelPathToFile(indexObj, "expressions", path);
        JSONObject motions = indexObj.getJSONObject("motions");
        if (motions != null) {
            appendModelPathToFile(motions, "idle", path);
            appendModelPathToFile(motions, "flick_head", path);
            appendModelPathToFile(motions, "tap_body", path);
            appendModelPathToFile(motions, "thanking", path);
        }
        return indexObj.toString();
    }

    private static void appendPathToObj(JSONObject indexObj, String key, String path) {
        String obj = indexObj.getString(key);
        if (obj != null) {
            indexObj.put(key, path + obj);
        }
    }

    private static void appendModelPath(JSONArray jsonArr, String path) {
        if (jsonArr == null) return;
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonArr.set(i, path + jsonArr.get(i));
        }
    }

    private static void appendModelPathToFile(JSONObject model, String key, String path) {
        if (!model.containsKey(key)) return;
        JSONArray jsonArr = model.getJSONArray(key);
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject item = jsonArr.getJSONObject(i);
            appendPathToObj(item, "file", path);
        }
    }

    public static void main(String[] args) {
        Models model = new Models();
//        String json = model.getModelResponseJson(0, 1);
//        System.out.println(json);
    }

    public String switchNextInfo(Integer id) {
        Integer nextId = (++id) % models.size();
        JSONObject json = new JSONObject();
        JSONObject model = new JSONObject();
        model.put("id", nextId);
        model.put("message", "我来喽！！~");
        json.put("model", model);
        return json.toString();
    }

    public String switchNextTextures(Integer id, Integer modelTexturesId) {
        int size = models.get(id).size();
        String json = """
                {
                    "textures" : { "id": /{size} }
                }
                """;
        STR. """
                {
                    "textures" : { "id": \{size} }
                }
                """
        return null;
    }
}
