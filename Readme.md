Kế hoạch phát triển Hệ thống Quản lý Trang trại (Farm Management)
Dưới đây là kế hoạch chi tiết đã được cập nhật với các tính năng nâng cao, hỗ trợ đa trang trại (multi-farm) và phân tích chi tiết.
1. Kiến trúc tổng quan (High-Level Architecture)
Hệ thống sẽ được xây dựng theo kiến trúc Monolith (Spring Boot) nhưng sẵn sàng để tách thành Microservices khi cần.
Frontend (Client): Ứng dụng Single Page Application (SPA) xây dựng bằng ReactJS.
Sử dụng thư viện biểu đồ như Recharts hoặc Chart.js để trực quan hóa dữ liệu.
Tích hợp thư viện @react-oauth/google cho luồng đăng nhập Google.
Backend (Server): Ứng dụng Java 17 (sử dụng Spring Boot 3.x) cung cấp các RESTful APIs.
API Versioning: APIs sẽ được phiên bản hóa (ví dụ: /api/v1/...).
Security: Sử dụng Spring Security (JWT và tích hợp OAuth 2.0/OIDC để xác thực token từ Google).
Job Scheduler: Sử dụng Spring Scheduler (@Scheduled) cho các tác vụ định kỳ (cảnh báo, nhắc nhở).
Notification Service: Tích hợp service gửi Email (JavaMail) hoặc Push Notification (Firebase Cloud Messaging - FCM).
Database: MongoDB (NoSQL).
Cần cấu hình cơ chế Backup tự động (ví dụ: sử dụng mongodump với cronjob).
Sử dụng tính năng Logging/Auditing (ví dụ: Spring Data Envers for MongoDB hoặc ghi log thủ công) để theo dõi các thay đổi quan trọng.
2. Phân quyền (Roles) - Cập nhật cho Multi-Farm
Việc phân quyền sẽ được gắn liền với (các) trang trại cụ thể:
ADMIN (Quản trị viên Hệ thống):
Quyền cao nhất.
Quản lý tất cả tài khoản người dùng (tạo, sửa, xóa, gán vai trò, kích hoạt).
Quản lý Trang trại (CRUD Farms): Tạo, cấu hình các trang trại mới.
Gán MANAGER vào các trang trại.
Truy cập dữ liệu của toàn bộ các trang trại.
MANAGER (Người quản lý Farm):
(Phải được ADMIN kích hoạt tài khoản).
Được gán vào một hoặc nhiều trang trại.
Chỉ có quyền vận hành (tạo, sửa, xóa dữ liệu) trên các trang trại mình quản lý.
Giao việc (Task) cho nhân viên trong farm của mình.
Xem báo cáo, thống kê, tài chính của farm mình quản lý.
STAFF (Nhân viên Farm):
(Phải được ADMIN kích hoạt tài khoản).
Thuộc về một trang trại cụ thể.
Chỉ xem và cập nhật các công việc được giao cho mình tại farm đó.
Chỉ ghi log dữ liệu (cho ăn, tưới nước) cho farm của mình.
3. Danh sách chức năng (Features) - Đã mở rộng
Module 1: Quản lý Hệ thống & Farm
(ADMIN) Quản lý Trang trại (CRUD): Tạo, sửa, xem thông tin các trang trại.
(ADMIN) Quản lý Người dùng (CRUD):
Tạo tài khoản (nhập email là bắt buộc, tài khoản mặc định ở trạng thái 'chưa kích hoạt').
Gán họ vào các farm với vai trò (MANAGER, STAFF).
(MỚI) Kích hoạt / Vô hiệu hóa tài khoản người dùng.
Module 2: Xác thực (Auth)
(Public) Đăng nhập (bằng mật khẩu):
Kiểm tra isActive == true. Nếu false, trả về lỗi "Tài khoản chưa được kích hoạt".
Nếu hợp lệ, trả về JWT và danh sách các farm mà user có quyền truy cập.
(Public) Đăng nhập bằng Google (OAuth 2.0): (Cập nhật)
Người dùng đăng nhập bằng Google, FE gửi id_token lên BE.
BE xác thực id_token, tìm user bằng email đã được Admin tạo trước.
Kiểm tra isActive == true. Nếu false, trả về lỗi "Tài khoản chưa được kích hoạt".
Nếu hợp lệ, BE trả về JWT (token của hệ thống) để FE sử dụng.
(User) Đổi mật khẩu, Đăng xuất.
Module 3: Bảng điều khiển (Dashboard)
Hiển thị thông tin tổng quan (lọc theo farm nếu user có nhiều farm).
Sử dụng biểu đồ (Recharts/Chart.js) để hiển thị:
Tổng quan tài chính nhanh (doanh thu/chi phí).
Số lượng công việc (hôm nay, quá hạn).
Cảnh báo kho (sắp hết hàng).
Module 4: Quản lý Vật nuôi (Livestock) - (Cập nhật chi tiết chuyên sâu)
(MANAGER) Quản lý Chuồng (CRUD): Tạo, sửa, quản lý các chuồng/khu vực nhốt (gắn với farmId).
(MANAGER) CRUD Vật nuôi: Tạo mới (ID, loài, giống, ngày sinh, trạng thái sức khỏe, enclosureId, animalType).
**(MANAGER) Tích hợp theo dõi lô (Batch Tracking).
(MANAGER/STAFF) Ghi nhật ký Sự kiện: Ghi nhận (nhập đàn, xuất bán, chết, chọn làm hậu bị/giống).
(MANAGER/STAFF) Ghi nhật ký sức khỏe.
(MANAGER) Quản lý Dinh dưỡng & Thức ăn:
Tạo Kế hoạch ăn uống (Feed Plans) theo từng giai đoạn (hậu bị, mang thai, cho con bú, vỗ béo, tập ăn).
Gán Kế hoạch ăn uống cho vật nuôi hoặc theo chuồng.
Theo dõi tiêu thụ thức ăn, thuốc, vật tư theo từng khu nuôi/chuồng.
(MANAGER/STAFF) Quản lý Sinh sản & Đỡ đẻ:
Theo dõi lịch sử phối giống, dự kiến đẻ.
Ghi nhận hỗ trợ đỡ đẻ (đẻ khó, đẻ non), lưu tiền sử đẻ khó.
Ghi nhận tình trạng con non sau sinh (sức khỏe, bú sữa đầu).
Theo dõi lượng sữa của con mẹ, chế độ ăn sau sinh.
(MANAGER) Quản lý Vật nuôi Hậu bị/Giống:
Đánh dấu/Ghi nhận sự kiện chọn lọc vật nuôi hậu bị/giống từ lứa.
Áp dụng chế độ nuôi dưỡng/kế hoạch ăn uống riêng cho vật nuôi hậu bị.
Module 5: Quản lý Cây trồng (Crop)
(MANAGER) CRUD Khu vực/Thửa ruộng (gắn với farmId).
(MANAGER) CRUD Mùa vụ (gắn với farmId).
(MANAGER/STAFF) Ghi nhật ký canh tác.
(MANAGER) Tích hợp theo dõi lô thu hoạch (Batch Tracking).
Module 6: Quản lý Kho & Cung ứng (Inventory) - (Cập nhật)
(MANAGER) CRUD Vật tư (thức ăn, thuốc, vắc-xin...).
(MANAGER/STAFF) Ghi nhật ký Xuất/Nhập kho.
Khi xuất kho (thức ăn, thuốc) phải có tùy chọn ghi nhận cho chuồng nào hoặc vật nuôi nào.
(MANAGER) Theo dõi vật tư theo lô (Batch Tracking).
(MANAGER) Kiểm kê kho định kỳ:
Tạo phiên kiểm kê.
Ghi nhận số lượng thực tế, so sánh với hệ thống và tạo báo cáo chênh lệch.
Module 7: Quản lý Công việc & Nhân sự (Task & HR)
(MANAGER) CRUD Công việc (giao cho STAFF trong farm).
(STAFF) Cập nhật công việc ("My Tasks").
(MANAGER/STAFF) Xem Lịch làm việc (Calendar View): Hiển thị công việc theo lịch (ngày/tuần/tháng).
(MANAGER) Báo cáo hiệu suất nhân viên: Thống kê số lượng task hoàn thành, đúng hạn.
Module 8: Quản lý Tài chính (Finance)
(MANAGER) Ghi nhận Chi phí: (Mua vật tư, nhân công, vận hành...).
(MANAGER) Ghi nhận Doanh thu: (Bán sản phẩm từ lô hàng...).
(MANAGER/ADMIN) Xem Báo cáo Lợi nhuận & Thua lỗ (P&L): Lọc theo thời gian, theo farm.
Module 9: Báo cáo & Thống kê (Analytics)
(MANAGER/ADMIN) Báo cáo sức khỏe đàn (tỷ lệ bệnh, tỷ lệ chết, tỷ lệ tăng trưởng).
(MANAGER/ADMIN) Báo cáo sản lượng mùa vụ.
(MANAGER/ADMIN) Biểu đồ tồn kho theo thời gian.
(MANAGER/ADMIN) Báo cáo truy xuất nguồn gốc (theo lô).
Module 10: Hệ thống & Thông báo (System)
(System) Job Scheduler:
Quét và gửi email/thông báo cho task sắp hết hạn.
Quét và gửi cảnh báo low-stock.
Nhắc lịch kiểm kê kho định kỳ.
(System) Ghi log tự động: Các thay đổi quan trọng (tài chính, kho) sẽ được lưu vào collection auditLogs.
4. Mô hình dữ liệu (MongoDB Data Models) - Cập nhật
(Quan trọng: Hầu hết các collection chính đều phải thêm farmId để hỗ trợ multi-farm)
farms (MỚI)
{
  "_id": "ObjectId('...')",
  "name": "Trang trại Đà Lạt",
  "location": "Lâm Đồng",
  "createdAt": "ISODate('...')"
}


users (Cập nhật)
{
  "_id": "ObjectId('...')",
  "username": "manager_dalat", // Có thể dùng làm tên đăng nhập dự phòng
  "email": "manager.dalat@gmail.com", // **MỚI - BẮT BUỘC & UNIQUE**
  "password": "[hashed_password]", // **MỚI - Optional** (Có thể null nếu chỉ đăng nhập bằng Google)
  "authProvider": "LOCAL", // **MỚI** (LOCAL, GOOGLE)
  "googleId": "...", // **MỚI** (Lưu 'sub' ID của Google để liên kết)
  "fullName": "Quản lý Farm Đà Lạt",
  "role": "MANAGER", // ADMIN, MANAGER, STAFF
  "farmIds": ["ObjectId('...farm_dalat')"], // ADMIN có thể là mảng rỗng (toàn quyền)
  "isActive": false, // **CẬP NHẬT** Mặc định là false. Phải được ADMIN kích hoạt.
  "createdAt": "ISODate('...')"
}


enclosures (Cập nhật - Chuồng trại)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...farm_dalat')",
  "name": "Chuồng Nái A-01",
  "type": "BREEDING_PEN", // BREEDING_PEN (chuồng sinh sản/nái), DEVELOPMENT_PEN (chuồng hậu bị), FATTENING_PEN (vỗ béo), YOUNG_PEN (chuồng con non)
  "capacity": 20,
  "currentOccupancy": 15,
  "feedConsumptionLogs": [ // Ghi nhận thức ăn theo chuồng
    {
      "date": "ISODate('...')",
      "feedItemId": "ObjectId('...')", // ref to inventoryItems
      "quantity": 50, // kg
      "recordedBy": "ObjectId('...')"
    }
  ],
  "medicineConsumptionLogs": [ // Ghi nhận thuốc theo chuồng
     {
      "date": "ISODate('...')",
      "medicineItemId": "ObjectId('...')",
      "quantity": 0.5, // Lít
      "notes": "Trộn vào thức ăn",
      "recordedBy": "ObjectId('...')"
    }
  ]
}


feedPlans (MỚI - Kế hoạch ăn uống)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "name": "Kế hoạch ăn cho nái mang thai (Tuần 1-4)",
  "stage": "GESTATION_EARLY", // GESTATION_LATE (mang thai cuối kỳ), LACTATION (cho con bú), STARTER (tập ăn), DEVELOPMENT (hậu bị), FATTENING (vỗ béo)
  "description": "Cung cấp dinh dưỡng cơ bản...",
  "feedDetails": [
    {
      "feedItemId": "ObjectId('...cam_nai_mang_thai')",
      "quantityPerDay": 2.5, // kg/con/ngày
      "unit": "kg"
    }
  ]
}


animals (Cập nhật)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...farm_dalat')", // **BẮT BUỘC**
  "tagId": "PIG-001",
  "species": "Lợn", // Ví dụ: Lợn, Bò, Gà...
  "animalType": "BREEDING_FEMALE", // **MỚI** BREEDING_FEMALE (con nái/mẹ), DEVELOPMENT (hậu bị), FATTENING (vỗ béo), YOUNG (con non)
  "batchId": "ObjectId('...batch_nhap_lon_t10')", // ref to 'batches'
  "enclosureId": "ObjectId('...chuong_A01')", // ref to 'enclosures'
  "feedPlanId": "ObjectId('...plan_nai_mang_thai')", // **MỚI** ref to 'feedPlans'
  "birthDate": "ISODate('...')",
  "status": "HEALTHY", // HEALTHY, SICK, SOLD, DEAD
  "healthRecords": [
    {
      "date": "ISODate('...')",
      "eventType": "VACCINATION",
      "notes": "Tiêm vắc-xin 5 bệnh",
      "recordedBy": "ObjectId('...')"
    }
  ],
  "growthRecords": [
    {
      "date": "ISODate('...')",
      "weight": 85.5, // kg
      "recordedBy": "ObjectId('...')"
    }
  ],
  "reproductionLogs": [ // Cập nhật chi tiết
    {
      "breedingDate": "ISODate('...')",
      "sireId": "PIG-SIRE-002", // Mã con đực
      "expectedFarrowDate": "ISODate('...')",
      "farrowDate": "ISODate('...')",
      "pigletsBorn": 12, // Số con non sinh ra
      "pigletsWeaned": 10, // Số con non cai sữa
      "qualityRating": "GOOD", // Đánh giá chất lượng sinh sản (con mẹ)
      "farrowingNotes": "Đẻ khó, cần hỗ trợ. Con yếu.", // **MỚI** Ghi chú đỡ đẻ
      "difficultBirth": true, // **MỚI** Tiền sử đẻ khó
      "milkSupply": "GOOD", // **MỚI** GOOD, AVERAGE, POOR
      "pigletHealthStatus": "Tốt, 1 con yếu" // **MỚI** Sức khỏe con non
    }
  ]
}


animalEvents (Cập nhật - Sự kiện vật nuôi)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "animalId": "ObjectId('...animal_PIG-001')",
  "type": "ENTRY", // ENTRY (nhập đàn), SALE (xuất bán), DEATH (chết), SELECT_BREEDER (chọn giống/hậu bị)
  "date": "ISODate('...')",
  "notes": "Chọn từ lứa [Mã Lứa] (batch ...)",
  "price": 0,
  "recordedBy": "ObjectId('...')"
}


inventoryItems (Cập nhật)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...farm_dalat')", // **BẮT BUỘC**
  "name": "Cám con non tập ăn",
  "category": "FEED", // FEED, MEDICINE, FERTILIZER, SEED
  "quantity": 150.5, // Tổng số lượng
  "unit": "kg",
  "lowStockThreshold": 50
}


batches (MỚI - Truy xuất nguồn gốc)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "batchCode": "LOT-2025-10-A", // Mã lô (có thể do người dùng nhập)
  "type": "ANIMAL", // ANIMAL, CROP, INVENTORY
  "description": "Lô bò sữa nhập ngày 20/10",
  "source": "Nhà cung cấp A",
  "entryDate": "ISODate('...')",
  "relatedItemIds": ["ObjectId('...animal1')", "ObjectId('...animal2')"]
}


inventoryLogs (Cập nhật - Thêm Batch và Ghi cho Chuồng)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "itemId": "ObjectId('...')", // ref to 'inventoryItems'
  "batchCode": "LOT-CAM-B-001",
  "type": "OUT",
  "quantity": 5.0,
  "notes": "Sử dụng cho chuồng A-01",
  "usageTarget": { // **MỚI**
    "type": "ENCLOSURE", // ENCLOSURE hoặc ANIMAL
    "id": "ObjectId('...chuong_A01')"
  },
  "recordedBy": "ObjectId('...')",
  "date": "ISODate('...')"
}


inventoryAudits (MỚI - Kiểm kê)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "date": "ISODate('...')",
  "conductedBy": "ObjectId('...')",
  "status": "COMPLETED",
  "items": [
    {
      "itemId": "ObjectId('...')",
      "itemName": "Cám gà con",
      "systemQuantity": 150,
      "actualQuantity": 148,
      "discrepancy": -2,
      "notes": "Hao hụt"
    }
  ]
}


m
financialTransactions (MỚI - Tài chính)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...')",
  "type": "EXPENSE", // EXPENSE, REVENUE
  "amount": 5000000,
  "description": "Mua 10 bao cám Bò",
  "category": "Vật tư",
  "relatedBatchId": "ObjectId('...batch_cam_bo')", // Tùy chọn
  "date": "ISODate('...')",
  "recordedBy": "ObjectId('...')"
}


tasks (Cập nhật)
{
  "_id": "ObjectId('...')",
  "farmId": "ObjectId('...farm_dalat')", // **BẮT BUỘC**
  "title": "Cho bò chuồng A1 ăn",
  // ... (các trường khác)
  "assignedTo": "ObjectId('...staff_A')",
  "status": "TODO",
  "dueDate": "ISODate('...')"
}


5. Lộ trình phát triển (Roadmap) - Mở rộng
Giai đoạn 1: Lõi (Core) & Multi-Farm (Sprint 1-3)
Backend:
Thiết lập Spring Boot, MongoDB, Spring Security + JWT.
(MỚI) Tích hợp Spring Security OAuth 2.0/OIDC.
Tạo model farms.
Tạo model users (hỗ trợ farmIds, role, email, authProvider, isActive mặc định false).
API Quản lý Farm (ADMIN).
API Quản lý User (ADMIN) (phải nhập email, thêm endpoint Kích hoạt/Vô hiệu hóa).
API Đăng nhập (mật khẩu) và API Đăng nhập Google (cả hai phải kiểm tra cờ 'isActive').
Cấu hình API versioning.
Frontend:
Thiết lập React, router, axios.
(MỚI) Tích hợp @react-oauth/google.
Tạo Context quản lý Auth và Farm (chọn farm hiện tại).
Trang Đăng nhập (Thêm nút "Đăng nhập bằng Google", xử lý lỗi "Chưa kích hoạt").
Xử lý luồng callback OAuth, gửi id_token cho BE.
Trang Quản lý Farm, Quản lý User (cho ADMIN) (thêm nút Kích hoạt/Vô hiệu hóa user).
Thiết lập Protected Routes.
Giai đoạn 2: Quản lý Vận hành (Livestock, Crop) (Sprint 4-6)
(Giai đoạn này được mở rộng do thêm tính năng chi tiết)
Backend: Tạo Models và APIs (CRUD) cho enclosures, animals (với các trường theo dõi chi tiết), animalEvents, feedPlans, plots, crops. (Tất cả đều phải có farmId).
Frontend: Xây dựng các trang UI (CRUD) cho Vật nuôi, Mùa vụ, và Chuồng trại.
Bổ sung UI ghi nhận sự kiện (nhập, bán, chết, chọn hậu bị).
Bổ sung UI theo dõi sinh sản, đỡ đẻ.
Bổ sung UI quản lý Kế hoạch ăn uống (Feed Plans).
Giai đoạn 3: Kho & Công việc (Sprint 7-8)
Backend: Tạo Models và APIs (CRUD) cho inventoryItems (cập nhật inventoryLogs để hỗ trợ ghi log theo chuồng), và tasks.
Frontend:
UI Quản lý Kho (cập nhật UI xuất kho để chọn chuồng).
UI Quản lý Công việc (Tạo/Giao việc).
Trang "My Tasks" cho STAFF.
Thêm UI Lịch (Calendar View) cho công việc.
Giai đoạn 4: Tài chính & Truy xuất Nguồn gốc (Sprint 9-10)
Backend:
Tạo Models và APIs cho financialTransactions (Tài chính).
Tạo Models và APIs cho batches (Lô).
Cập nhật APIs Vật nuôi, Cây trồng, Kho để hỗ trợ batchId.
Frontend:
UI Module Tài chính (nhập liệu, xem báo cáo P&L).
UI Module Quản lý Lô (Batch).
Tích hợp chọn/xem Lô vào các màn hình liên quan.
Giai đoạn 5: Báo cáo & Kiểm kê (Sprint 11-12)
Backend:
Tạo Models và APIs cho inventoryAudits (Kiểm kê).
Xây dựng các API tổng hợp (Aggregation) phức tạp cho Báo cáo (hiệu suất NV, sức khỏe đàn...).
Frontend:
UI Module Kiểm kê kho.
Trang Dashboard (hoàn thiện với Recharts).
Trang Báo cáo Hiệu suất Nhân viên.
Giai đoạn 6: Hệ thống & Hoàn thiện (Sprint 13)
Backend:
Thiết lập Spring Schedulers (thông báo, cảnh báo).
Tích hợp Email/Notification Service.
Cấu hình cơ chế Ghi log (Auditing).
Thiết lập kịch bản Backup DB.
Frontend:
Hiển thị thông báo (in-app).
Hoàn thiện: Kiểm thử toàn diện, sửa lỗi, và tinh chỉnh UI/UX.
