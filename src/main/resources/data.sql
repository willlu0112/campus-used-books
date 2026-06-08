-- 🌟 1. 預灌測試會員帳號 (密碼預設都是 1234)
-- 🌟 請確保整個 VALUES(..., '...') 都在同一行，不要斷行喔！
INSERT INTO users (username, password) VALUES ('userA', '$2a$10$vNeqJpyNgx9eRPftGsZE.er8b6JrJpueQDmGrAjcMlYBr0W6G87Wa');
INSERT INTO users (username, password) VALUES ('userB', '$2a$10$vNeqJpyNgx9eRPftGsZE.er8b6JrJpueQDmGrAjcMlYBr0W6G87Wa');


-- 🌟 2. 預灌 3 本包含完整欄位、封面、上架者的二手書資料
-- 第一本書：由 user1 上架
INSERT INTO book (title, price, author, course_name, professor, cover_image, owner_username) 
VALUES ('Java 入門指引', 450, '張三', '物件導向程式設計', '張教授', 'default.png', 'user1');

-- 第二本書：由 user1 上架
INSERT INTO book (title, price, author, course_name, professor, cover_image, owner_username) 
VALUES ('Python 數據分析', 380, '李四', '巨量資料分析', '張教授', 'default.png', 'user1');

-- 第三本書：由 user2 上架
INSERT INTO book (title, price, author, course_name, professor, cover_image, owner_username) 
VALUES ('演算法圖鑑', 500, '王五', '資料結構與演算法', '李教授', 'default.png', 'user2');