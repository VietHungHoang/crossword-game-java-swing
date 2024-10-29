package models;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordInGame {
    private String keyword;
    private char[] characters;

    // Constructor
    public WordInGame(String keyword, char[] characters) {
        this.keyword = keyword;
        this.characters = characters;
    }

    // Phương thức để tạo danh sách các WordInGame với dữ liệu cứng
    public static List<WordInGame> generateSampleData() {
        List<WordInGame> wordGames = new ArrayList<>();

        // Danh sách các từ khóa mẫu
        String[] keywords = {
            "Professional", "Education", "Technology", "Innovation", "Development",
            "Experience", "Management", "Communication", "Leadership", "Motivation"
        };

        for (String keyword : keywords) {
            char[] characters = generateCharacterArray(keyword);
            wordGames.add(new WordInGame(keyword, characters));
        }
        
        return wordGames;
    }

    // Phương thức tạo mảng ký tự có độ dài cố định là 36
    private static char[] generateCharacterArray(String keyword) {
        List<Character> charList = new ArrayList<>();

        // Thêm các ký tự từ từ khóa vào danh sách
        for (char c : keyword.toCharArray()) {
            charList.add(c);
        }

        // Bổ sung các ký tự còn thiếu từ bảng chữ cái
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c : alphabet.toCharArray()) {
            if (charList.size() >= 36) break;
            charList.add(c);
        }

        // Trộn ngẫu nhiên các ký tự
        Collections.shuffle(charList);

        // Chuyển danh sách ký tự thành mảng `char[]`
        char[] characterArray = new char[36];
        for (int i = 0; i < 36; i++) {
            characterArray[i] = charList.get(i);
        }
        return characterArray;
    }

    @Override
    public String toString() {
        return "WordInGame{" +
                "keyword='" + keyword + '\'' +
                ", characters=" + new String(characters) +
                '}';
    }
}
