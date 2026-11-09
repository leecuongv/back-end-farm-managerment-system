# 🌾 Farm Management System (FarmSys) - Backend API

> **Hệ thống Quản lý Trang trại Thông minh (Đa trang trại - Multi-farm)** xây dựng trên nền tảng **Java 17, Spring Boot 3.5.7, MongoDB Atlas** và **Redis Cache**.

---

## 📌 Mục lục
- [1. Giới thiệu](#1-giới-thiệu)
- [2. Công nghệ sử dụng](#2-công-nghệ-sử-dụng)
- [3. Kiến trúc & Tính năng nổi bật](#3-kiến-trúc--tính-năng-nổi-bật)
- [4. Cấu trúc thư mục](#4-cấu-trúc-thư-mục)
- [5. Hướng dẫn lấy và thiết lập các thông số cấu hình](#5-hướng-dẫn-lấy-và-thiết-lập-các-thông-số-cấu-hình)
- [6. Hướng dẫn cài đặt & Khởi chạy cục bộ](#6-hướng-dẫn-cài-đặt--khởi-chạy-cục-bộ)
- [7. Hướng dẫn Deploy lên Render.com](#7-hướng-dẫn-deploy-lên-rendercom)
- [8. Tài liệu API (Swagger UI)](#8-tài-liệu-api-swagger-ui)
- [9. Phân quyền người dùng (RBAC)](#9-phân-quyền-người-dùng-rbac)

---

## 1. Giới thiệu
**FarmSys Backend** cung cấp hệ thống RESTful API toàn diện cho việc số hóa quy trình vận hành nông nghiệp:
- Quản lý mô hình đa trang trại (**Multi-farm Architecture**).
- Theo dõi vật nuôi, chuồng trại, kế hoạch dinh dưỡng và sự kiện sinh sản.
- Quản lý mùa vụ, lô đất trồng trọt và nhật ký canh tác.
- Quản lý kho vật tư, kiểm kê, định mức tồn kho và truy xuất nguồn gốc theo lô (**Batch Tracking**).
- Quản lý công việc (Tasks), tài chính thu/chi (P&L) và báo cáo hiệu suất nhân sự.
- Tối ưu hóa hiệu năng cao nhờ tích hợp **Redis Caching 2 tầng** chịu lỗi (**Fault-tolerant**).

---

## 2. Công nghệ sử dụng
- **Ngôn ngữ**: Java 17 (LTS)
- **Framework chính**: Spring Boot 3.5.7
  - `spring-boot-starter-web`
  - `spring-boot-starter-security`
  - `spring-boot-starter-data-mongodb`
  - `spring-boot-starter-data-redis`
  - `spring-boot-starter-cache`
  - `spring-boot-starter-oauth2-client`
- **Bảo mật & Xác thực**: Spring Security, JWT (io.jsonwebtoken 0.11.5), Google OAuth2
- **Cơ sở dữ liệu**: MongoDB Atlas (Cloud NoSQL Database)
- **Bộ nhớ đệm (Caching)**: Redis Cloud / Redis 7 (Lettuce client)
- **Tài liệu API**: Springdoc OpenAPI 2.5.0 (Swagger UI)
- **Công cụ hỗ trợ**: Project Lombok 1.18.32, Jackson JSR-310 (JavaTimeModule)

---

## 3. Kiến trúc & Tính năng nổi bật

### ⚡ Hệ thống Redis Cache 2 Tầng Chịu Lỗi (Fault-Tolerant)
- Tích hợp cấu hình [`RedisCacheConfig.java`](src/main/java/com/farmmanagement/system/config/RedisCacheConfig.java) với `GenericJackson2JsonRedisSerializer` hỗ trợ serialize các kiểu thời gian `LocalDate`, `LocalDateTime`.
- Thiết lập thời gian sống (TTL) linh hoạt theo từng miền dữ liệu:
  - `farms`: 30 phút.
  - `users`, `enclosures`, `plots`, `seasons`, `feedPlans`: 15 phút.
  - `animals`, `batches`, `inventoryAudits`: 10 phút.
  - `inventoryItems`, `tasks`, `financialTransactions`, `animalEvents`, `cropEvents`, `inventoryLogs`: 5 phút.
- **Cơ chế chịu lỗi thông minh (Custom CacheErrorHandler)**: Nếu Redis Cloud tạm thời chưa kết nối hoặc mất mạng, hệ thống tự động ghi log cảnh báo và truy vấn trực tiếp từ MongoDB mà không gây sập server hay trả về lỗi 500.

### 🛡️ Bảo mật & Phân quyền (RBAC)
- Xác thực bằng **JWT Token** kết hợp **Google OAuth2 Login**.
- Hỗ trợ 3 nhóm vai trò: `ADMIN`, `MANAGER`, `STAFF` gắn liền với từng trang trại (`farmIds`).
- Tự động ghi nhật ký kiểm toán hệ thống (`AuditService`) cho mọi tác vụ thêm, sửa, xóa dữ liệu.

---

## 4. Cấu trúc thư mục
```bash
back-end-farm-managerment-system/
├── pom.xml                                   # Cấu hình dependencies Maven
├── src/
│   ├── main/
│   │   ├── java/com/farmmanagement/system/
│   │   │   ├── FarmManagementSystemApplication.java  # Main application entry point
│   │   │   ├── config/                       # Cấu hình hệ thống (Redis, OpenAPI, Mongo, Security)
│   │   │   │   ├── RedisCacheConfig.java
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/                   # 15 REST Controllers (API Endpoints)
│   │   │   ├── model/                        # MongoDB Entities / DTOs
│   │   │   ├── repository/                   # Spring Data MongoDB Repositories
│   │   │   ├── security/                     # Bộ lọc JWT, OAuth2 Handler, SecurityUtils
│   │   │   └── service/                      # Business Services & Audit Logging
│   │   └── resources/
│   │       ├── application.properties        # File cấu hình mặc định của Spring Boot
│   │       └── application-prod.properties   # Cấu hình môi trường Production (tùy chọn)
└── README.md
```

---

## 5. Hướng dẫn lấy và thiết lập các thông số cấu hình

Để hệ thống hoạt động đầy đủ tính năng (cơ sở dữ liệu, bộ nhớ đệm, bảo mật và đăng nhập Google), bạn cần chuẩn bị 4 thành phần sau:

### 1. Chuỗi kết nối MongoDB Atlas (Database Connection URI)
1. Đăng nhập hoặc tạo tài khoản miễn phí tại [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
2. Tạo một **Cluster** miễn phí (M0 Free Tier).
3. Vào mục **Database Access** -> bấm **Add New Database User** -> tạo tên tài khoản và mật khẩu (lưu lại mật khẩu này).
4. Vào mục **Network Access** -> bấm **Add IP Address** -> chọn **Allow Access from Anywhere** (`0.0.0.0/0`) để cho phép server (Render hoặc máy local) kết nối.
5. Quay lại **Database** -> bấm nút **Connect** -> chọn **Drivers (Java)** -> copy chuỗi kết nối dạng:
   ```text
   mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```
6. Thay thế `<username>` và `<password>` bằng tài khoản vừa tạo ở bước 3.

---

### 2. Thông số kết nối Redis Cloud (Redis Cache URI)
1. Đăng ký tài khoản miễn phí tại [Redis.io Cloud](https://redis.io/try-free/) hoặc [Upstash Redis](https://upstash.com/).
2. Tạo một database mới (chọn gói Free).
3. Sau khi tạo xong, trong bảng điều khiển database:
   - Copy **Public endpoint** (chính là Host và Port, ví dụ: `dope-bushes-whimsical-67602.db.redis.io:14644`).
   - Vào mục **Security** copy chuỗi **Default user password**.
4. Hoặc copy trực tiếp chuỗi kết nối **Redis URI / redis-cli string** có dạng:
   ```text
   redis://default:<password>@<host>:<port>
   ```

---

### 3. Khóa bí mật JWT (JWT Secret Key)
Hệ thống sử dụng thuật toán HMAC-SHA256 để ký và xác thực token JWT, yêu cầu một chuỗi bí mật có độ dài tối thiểu 256 bits (32 ký tự).
- Để tạo một khóa bí mật an toàn ngẫu nhiên bằng terminal:
  ```bash
  # Cách 1: Dùng lệnh openssl trên Linux / macOS
  openssl rand -base64 32

  # Cách 2: Chuỗi ký tự ngẫu nhiên bất kỳ dài trên 32 ký tự
  ```
- Chuỗi kết quả sinh ra sẽ được gán làm khóa bí mật cho hệ thống xác thực.

---

### 4. Google OAuth2 Client ID & Client Secret (Đăng nhập bằng Google)
1. Truy cập [Google Cloud Console](https://console.cloud.google.com/).
2. Tạo một dự án mới (ví dụ: `Farm-Management-System`).
3. Vào mục **APIs & Services** -> **OAuth consent screen**:
   - Chọn loại **External** -> điền tên ứng dụng và email hỗ trợ -> Lưu lại.
4. Vào mục **Credentials** -> bấm **Create Credentials** -> chọn **OAuth client ID**:
   - **Application type**: Chọn `Web application`.
   - **Authorized redirect URIs**: Thêm URL callback của backend (khi chạy local là `http://localhost:8080/login/oauth2/code/google`, khi deploy Render là `https://<ten-mien-render>/login/oauth2/code/google`).
5. Bấm **Create**, một cửa sổ sẽ hiện ra hiển thị:
   - **Your Client ID** (dạng `xxxxx.apps.googleusercontent.com`)
   - **Your Client Secret** (dạng `GOCSPX-xxxxx`)
6. Lưu lại 2 giá trị này để cấu hình cho hệ thống.

---

## 6. Hướng dẫn cài đặt & Khởi chạy cục bộ

### Yêu cầu tiên quyết
- **Java 17 JDK** trở lên (Oracle OpenJDK, Eclipse Temurin, Corretto,...)
- **Maven 3.8+** (hoặc dùng trực tiếp `./mvnw` đính kèm trong repository)

### Các bước khởi chạy
1. **Clone repository**:
   ```bash
   git clone https://github.com/your-username/back-end-farm-managerment-system.git
   cd back-end-farm-managerment-system
   ```

2. **Kiểm tra phiên bản Java**:
   ```bash
   java -version
   # Đảm bảo kết quả trả về Java 17 hoặc mới hơn
   ```

3. **Cập nhật thông số kết nối**:
   - Mở file `src/main/resources/application.properties`.
   - Điền các thông số MongoDB, Redis, JWT và Google OAuth2 bạn đã lấy được từ [Phần 5](#5-hướng-dẫn-lấy-và-thiết-lập-các-thông-số-cấu-hình).

4. **Biên dịch mã nguồn**:
   ```bash
   ./mvnw clean compile
   ```

5. **Khởi chạy ứng dụng**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Server sẽ khởi động tại: `http://localhost:8080`

---

## 7. Hướng dẫn Deploy lên Render.com

1. Đăng nhập vào [Render.com](https://render.com) và chọn **New +** -> **Web Service**.
2. Chọn kho lưu trữ Git chứa mã nguồn Backend.
3. Thiết lập thông số cơ bản:
   - **Name**: `farm-management-backend` (hoặc tên bạn muốn)
   - **Region**: Singapore (hoặc vùng gần bạn nhất)
   - **Language / Environment**: `Java`
   - **Build Command**:
     ```bash
     ./mvnw clean package -DskipTests
     ```
   - **Start Command**:
     ```bash
     java -jar target/farm-managerment-system-0.0.1-SNAPSHOT.jar
     ```
4. Điền các thông số bạn đã tạo từ [Phần 5](#5-hướng-dẫn-lấy-và-thiết-lập-các-thông-số-cấu-hình) vào mục **Environment Variables** trên Render:
   - Thêm chuỗi kết nối MongoDB Atlas.
   - Thêm chuỗi kết nối Redis Cloud.
   - Thêm khóa JWT và Google OAuth2 Credentials.
5. Bấm **Create Web Service**. Render sẽ tự động kéo code, build file JAR và kích hoạt dịch vụ online.

---

## 8. Tài liệu API (Swagger UI)

Khi ứng dụng đang chạy (local hoặc production), bạn có thể kiểm thử trực quan toàn bộ các endpoint và cấu trúc JSON thông qua Swagger:
- **Giao diện Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Đặc tả OpenAPI JSON**: `http://localhost:8080/api-docs`

### Danh sách các nhóm API chính:
- `/api/v1/farms`: Quản lý danh sách các trang trại.
- `/api/v1/users`: Quản lý tài khoản người dùng, gán trang trại, kích hoạt tài khoản.
- `/api/v1/animals`: Quản lý hồ sơ vật nuôi, chuồng trại, trạng thái sức khỏe.
- `/api/v1/animal-events`: Ghi nhận nhật ký tiêm phòng, dịch bệnh, xuất/nhập vật nuôi.
- `/api/v1/enclosures`: Quản lý chuồng trại, sức chứa và phân loại khu vực nuôi.
- `/api/v1/feed-plans`: Quản lý chế độ ăn, khẩu phần dinh dưỡng theo từng giai đoạn.
- `/api/v1/plots`: Quản lý diện tích, vị trí các thửa đất / lô trồng trọt.
- `/api/v1/seasons`: Quản lý các vụ mùa, tạo lô thu hoạch liên kết.
- `/api/v1/crop-events`: Nhật ký gieo hạt, bón phân, tưới tiêu và thu hoạch.
- `/api/v1/inventory-items`: Danh mục vật tư, cảnh báo chạm ngưỡng hết hàng (`lowStockThreshold`).
- `/api/v1/inventory-logs`: Ghi nhận lịch sử xuất/nhập kho (gắn với chuồng hoặc vật nuôi cụ thể).
- `/api/v1/inventory-audits`: Tạo phiên kiểm kê kho, đối soát chênh lệch tồn kho thực tế.
- `/api/v1/batches`: Quản lý mã lô hàng để truy xuất nguồn gốc (`Batch Tracking`).
- `/api/v1/tasks`: Giao việc, cập nhật tiến độ công việc theo lịch biểu.
- `/api/v1/financial-transactions`: Quản lý giao dịch thu / chi và báo cáo lợi nhuận ròng.

---

## 9. Phân quyền người dùng (RBAC)

| Vai trò | Phạm vi truy cập & Quyền hạn |
| :--- | :--- |
| **`ADMIN`** | Quyền cao nhất toàn hệ thống. Quản lý toàn bộ danh sách Trang trại (`/farms`), Người dùng (`/users`), gán vai trò, kích hoạt tài khoản và xem dữ liệu của tất cả trang trại. |
| **`MANAGER`** | Quản lý vận hành trong các trang trại được chỉ định (`farmIds`): Quản lý vật nuôi, cây trồng, kho vật tư, phân công việc cho nhân viên, xem báo cáo tài chính (P&L). |
| **`STAFF`** | Nhân viên thuộc trang trại cụ thể: Xem và cập nhật danh sách việc được giao ("Việc của tôi"), ghi nhật ký cho ăn, xuất nhập vật tư và theo dõi đàn. |

---

## 📄 Bản quyền & Đóng góp
Dự án được phát triển phục vụ mục đích học tập và triển khai giải pháp chuyển đổi số nông nghiệp. Mọi đóng góp và báo lỗi xin vui lòng tạo issue hoặc pull request.
