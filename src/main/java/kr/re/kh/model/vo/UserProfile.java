package kr.re.kh.model.vo;

public class UserProfile {
    private String saveFileName;
    private String username;

    // 기본 생성자
    public UserProfile() {}

    // 생성자
    public UserProfile(String saveFileName, String username) {
        this.saveFileName = saveFileName;
        this.username = username;
    }

    // Getter, Setter
    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
