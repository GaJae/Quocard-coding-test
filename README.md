
---

# 📖 BookNest プロジェクト

## 📌 概要
BookNestは書籍管理システムであり、ユーザーが書籍を作成・取得・更新・削除できる **RESTful API** を提供します。  
このプロジェクトは **Kotlin** と **Spring Boot** をベースに開発されており、データベース管理には **JOOQ** と **PostgreSQL** を使用しています。

---

## 🔧 技術スタック
| カテゴリ | 使用技術 |
| --- | --- |
| **言語** | Kotlin, Java |
| **フレームワーク** | Spring Boot |
| **ビルドツール** | Gradle |
| **データベース** | PostgreSQL |
| **ORM** | JOOQ |
| **テスト** | JUnit 5, Mockito, MockMvc |
| **コンテナ化** | Docker, Docker Compose |

---

## 🚀 主な機能
- **📌 書籍作成**：新しい書籍を追加
- **🔍 書籍取得**：
   - 特定の書籍IDで取得
   - すべての書籍リストを取得
- **✏️ 書籍更新**：既存の書籍情報を更新
- **🗑️ 書籍削除**：特定の書籍を削除
- **📖 状態管理**：書籍の状態（例: 出版状況）を管理

---

## 📂 プロジェクト構造
```bash
src/
├── main/
│   ├── kotlin/com/example/BookNest/
│   │   ├── controller/  # RESTコントローラー
│   │   ├── service/     # ビジネスロジック
│   │   ├── repository/  # データベースアクセス層
│   │   ├── dto/         # データ転送オブジェクト
│   │   └── config/      # 設定ファイル
│   └── resources/
│       ├── application.yml  # Spring Boot設定
│       └── db/migration/    # Flywayマイグレーションスクリプト
└── test/
    ├── kotlin/com/example/BookNest/
    │   ├── controller/  # コントローラーテスト
    │   ├── service/     # サービステスト
    │   └── repository/  # リポジトリテスト
```

---

## 🛠️ 設定と実行方法

### ✅ 必須要件
- JDK 17以上
- Gradle 7.x以上
- Docker & Docker Compose
- PostgreSQL

### 🚀 ローカル環境での実行
```bash
# リポジトリのクローン
git clone <repository-url>
cd BookNest

# Docker Composeの実行
docker-compose up -d

# データベースマイグレーション (Flyway)
./gradlew flywayMigrate

# アプリケーションの実行
./gradlew bootRun
```

---

## 📑 APIドキュメント

### 📌 書籍作成
**URL:** `POST /books`  
**リクエストボディ:**
```json
{
  "id": 1,
  "title": "Test Book",
  "price": 1000,
  "status": "UNPUBLISHED"
}
```
**レスポンス:**
```json
{
  "id": 1,
  "title": "Test Book",
  "price": 1000,
  "status": "UNPUBLISHED"
}
```

### 🔍 書籍取得
**URL:** `GET /books/{id}`  
**レスポンス:**
```json
{
  "id": 1,
  "title": "Test Book",
  "price": 1000,
  "status": "UNPUBLISHED"
}
```

### 📋 すべての書籍取得
**URL:** `GET /books`  
**レスポンス:**
```json
[
  {
    "id": 1,
    "title": "Book One",
    "price": 1000,
    "status": "UNPUBLISHED"
  },
  {
    "id": 2,
    "title": "Book Two",
    "price": 1500,
    "status": "PUBLISHED"
  }
]
```

---

## 🧪 テスト
```bash
# テストの実行
./gradlew test
```
JUnit 5とMockitoを使用した **単体テスト** および **統合テスト** を実施。  
MockMvcを使用して **コントローラーテスト** を実施。

---

## 🤝 コントリビューション
- **Issue** を作成するか、**Pull Request** を送信してコントリビューション可能
- **コードスタイル** は [Kotlin公式スタイルガイド](https://kotlinlang.org/docs/coding-conventions.html) に従う

---

## 📝 ライセンス
このプロジェクトは **MITライセンス** の下で提供されます。

---

修正後のマークダウンでは、視認性が向上し、可読性も改善されました。  
特にコードブロックの整備、表の活用、適切なセクション整理を行うことで、構造が一目でわかるようになっています。

どこかさらに調整したい点があれば、教えてください！ 😊
