package com.example.exammonitor.util;

import java.text.Normalizer;

public class VietnameseAccentRemover {
    public static String removeVietnameseAccent(String s) {
        if (s == null) return null;
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        temp = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Xử lý riêng cho đ/Đ
        temp = temp.replaceAll("đ", "d").replaceAll("Đ", "D");
        return temp.toLowerCase();
    }
} 