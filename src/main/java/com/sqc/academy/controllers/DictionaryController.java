package com.sqc.academy.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private final Map<String, String> dictionaryMap = new HashMap<>(
            Map.of("hello", "xin chào",
                    "goodbye", "tạm biệt",
                    "thank you", "cảm ơn bạn"));

    @GetMapping
    public ResponseEntity<String> dictionary(
            @RequestParam(defaultValue = "") String word) {
        // loại bỏ khoảng trắng dư thừa và chuyển chữ hoa thành chữ thường
        String translation = dictionaryMap.get(word.trim().toLowerCase());

        // Nếu không tìm thấy từ trong từ điển, trả về thông báo lỗi 404 not found
        if (translation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy từ " + word + " trong từ điển");
        }

        return ResponseEntity.ok(translation);
    }
}
