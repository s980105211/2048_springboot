markdown
複製
編輯
2048 Spring Boot 後端專案
這是一個以 Spring Boot 架構開發的後端專案，並透過 Docker 容器化後部署至 Azure 雲端平台，後端資料儲存使用 Azure 資料庫 for MySQL。

---

🛠️ 技術架構
| 類別       | 技術                       |
|------------|----------------------------|
| 語言       | Java 17                    |
| 後端框架   | Spring Boot 3.5            |
| 編譯方式   | JAR 打包                   |
| 容器化     | Docker                     |
| 雲端平台   | Azure App Service (Linux)  |
| 資料庫     | Azure Database for MySQL   |
| MySQL版本 | 8.x（彈性伺服器）         |

---🐳 Docker 容器化流程
建構映像檔

bash
複製
編輯
docker build -t 2048-springboot-app .
本地執行測試

bash
複製
編輯
docker run -p 8080:8080 2048-springboot-app
推送至 Azure App Service

使用 Azure CLI 或 GitHub Actions 部署到 Azure Web App（Linux）。

🧰 Azure 設定
App Service Plan：Linux App Service

部署方式：Container Registry / Dockerfile

資料庫：Azure Database for MySQL（彈性伺服器）
