# 校園教科書二手拍賣平台 (Campus Used Books)

![SDGs](https://img.shields.io/badge/SDGs-Goal%204%20%7C%20Goal%2012-green)
![Java](https://img.shields.io/badge/Language-Java-orange)
![VSCode](https://img.shields.io/badge/IDE-VS%20Code-blue)

本專案為 JAVA 程式設計課程之期末專題，是以**聯合國永續發展目標 (SDGs)** 為題材的程式開發訓練。我們希望透過系統設計回應教育資源不均、降低學習成本 (SDG 4.3)，以及資源浪費與環境負擔 (SDG 12.5 & 12.8) 之校園痛點。藉由平台推導「知識被延續、資源被珍惜」的理念，建立更永續的校園學習環境。

---

## 📂 專案目錄架構說明

本專案採用扁平、標準的 Java 開發架構，便於團隊進行 Git 版本控制與協作：

```text
campus-used-books/
├── .gitignore               # 排除不需要上傳到 GitHub 的暫存檔與 IDE 設定
├── README.md                # 專案說明文件（本檔案）
├── settings.json            # VS Code 專案環境設定
├── doc/                     # 存放設計圖與期中報告文件
│   └── 校園教科書二手拍賣平台.pdf
├── lib/                     # 存放外部導入的 Java .jar 驅動庫（如資料庫連接驅動）
└── src/                     # Java 原始碼核心
    └── App.java             # 程式主入口 (Main Class)
