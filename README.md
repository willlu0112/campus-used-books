# 校園教科書二手拍賣平台 (Campus Used Books)

![SDGs](https://img.shields.io/badge/SDGs-Goal%204%20%7C%20Goal%2012-green)
![Java](https://img.shields.io/badge/Language-Java%2025%20%7C%2026-orange)
![Framework](https://img.shields.io/badge/Framework-Spring%20Boot%204.0.6-brightgreen)
![IDE](https://img.shields.io/badge/IDE-VS%20Code-blue)

本專案為 JAVA 程式設計課程之期末專題，是以**聯合國永續發展目標 (SDGs)** 為題材的程式開發訓練。我們希望透過系統設計回應教育資源不均、降低學習成本 (SDG 4.3)，以及資源浪費與環境負擔 (SDG 12.5 & 12.8) 之校園痛點。藉由平台推導「知識被延續、資源被珍惜」的理念，建立更永續的校園學習環境。

---

## 📂 專案目錄架構與檔案職責

本專案採用 **Spring Boot + Thymeleaf 網頁專案架構**，請組員務必依據以下職責規範進行檔案放置與 Git 協作：

```text
campus-used-books/
├── .mvn/                    # Maven 專案核心封裝元件（請勿更動）
├── doc/                     # 存放設計圖與期中報告文件
│   └── 校園教科書二手拍賣平台.pdf
├── src/
│   ├── main/
│   │   ├── java/com/example/campus_used_books/
│   │   │   ├── controller/
│   │   │   │   └── HomeController.java      #【後端】負責管理網址路由、導向網頁畫面
│   │   │   └── CampusUsedBooksApplication.java # 專案發動引擎（點擊 Run 執行網頁伺服器）
│   │   └── resources/
│   │       ├── templates/                   #【前端】存放 HTML 網頁範本（Thymeleaf 渲染區）
│   │       │   └── index.html               # 首頁畫面
│   │       └── application.properties        # 專案環境設定檔（未來配置資料庫密碼處）
│   └── test/                # 單元測試程式碼存放區
├── .gitignore               # Git 忽略清單（自動排除 target/ 等編譯暫存檔）
├── mvnw / mvnw.cmd          # Maven 指令工具（大掃除、終端機啟動專案專用）
├── pom.xml                  # 專案套件管理清單（已配置 Web 與 Thymeleaf 套件）
└── README.md                # 本說明文件（本檔案）