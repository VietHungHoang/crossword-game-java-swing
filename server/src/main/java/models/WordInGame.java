package models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordInGame implements Serializable{
    private String keyword;
    private char[] characters;
    private static final long serialVersionUID = 1L;
    // Constructor
    public WordInGame(String keyword, char[] characters) {
        this.keyword = keyword;
        this.characters = characters;
    }
    public String getKeyword() {
        return keyword;
    }
    public char[] getCharacters() {
        return characters;
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
          if (!charList.contains(c)) { // Đảm bảo không thêm ký tự trùng lặp
              charList.add(c);
          }
      }
  
      // Bổ sung các ký tự còn thiếu từ bảng chữ cái
      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (char c : alphabet.toCharArray()) {
          if (charList.size() >= 36) break;
          if (!charList.contains(c)) { // Đảm bảo không thêm ký tự trùng lặp
              charList.add(c);
          }
      }
  
      // Trộn ngẫu nhiên các ký tự
      Collections.shuffle(charList);
  
      // Đảm bảo danh sách có đủ 36 phần tử
      while (charList.size() < 36) {
          charList.add(' '); // Thêm ký tự trống hoặc ký tự mặc định nếu thiếu
      }
  
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
