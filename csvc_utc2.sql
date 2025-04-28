-- Tạo bảng TaiKhoan
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(50),
    VaiTro NVARCHAR(20)
);

-- Tạo bảng CoSoVatChat
CREATE TABLE CoSoVatChat (
    MaCSVC VARCHAR(10) PRIMARY KEY,
    TenCSVC NVARCHAR(100),
    LoaiCSVC NVARCHAR(50),
    DonViTinh NVARCHAR(20),
    TinhTrang NVARCHAR(50),
    SoLuong INT CHECK (SoLuong >= 0) DEFAULT 0
);

-- Tạo bảng PhongHoc
CREATE TABLE PhongHoc (
    MaPhong VARCHAR(10) PRIMARY KEY,
    TenPhong NVARCHAR(100),
    LoaiPhong NVARCHAR(50),
    SucChua INT
);

-- Tạo bảng KiemKe
CREATE TABLE KiemKe (
    MaKK VARCHAR(10) PRIMARY KEY,
    NgayKK DATE,
    NguoiThucHien NVARCHAR(100),
    GhiChu NVARCHAR(200)
);

-- Tạo bảng ChiTietKiemKe
CREATE TABLE ChiTietKiemKe (
    MaKK VARCHAR(10),
    MaPhong VARCHAR(10),
    MaCSVC VARCHAR(10),
    SoLuong INT,
    TinhTrang NVARCHAR(50),
    FOREIGN KEY (MaKK) REFERENCES KiemKe(MaKK),
    FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

-- Tạo bảng BaoTri_SuaChua
CREATE TABLE BaoTri_SuaChua (
    MaBT INT PRIMARY KEY IDENTITY(1,1),
    MaCSVC VARCHAR(10),
    NgayBaoTri DATE,
    NoiDung NVARCHAR(200),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

-- Tạo bảng XuatVanBan
CREATE TABLE XuatVanBan (
    MaVB VARCHAR(10) PRIMARY KEY,
    NgayXuat DATE,
    NguoiLap NVARCHAR(100),
    LoaiVB NVARCHAR(50),
    MaPhong VARCHAR(10),
    FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong)
);

-- Tạo bảng XuatVanBan_KiemKe
CREATE TABLE XuatVanBan_KiemKe (
    MaVB VARCHAR(10),
    MaKK VARCHAR(10),
    FOREIGN KEY (MaVB) REFERENCES XuatVanBan(MaVB),
    FOREIGN KEY (MaKK) REFERENCES KiemKe(MaKK)
);

-- Tạo bảng XuatVanBan_CSVC
CREATE TABLE XuatVanBan_CSVC (
    MaVB VARCHAR(10),
    MaCSVC VARCHAR(10),
    FOREIGN KEY (MaVB) REFERENCES XuatVanBan(MaVB),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

-- Tạo bảng PhongHoc_CSVC
CREATE TABLE PhongHoc_CSVC (
    MaPhong VARCHAR(10),
    MaCSVC VARCHAR(10),
    SoLuong INT CHECK (SoLuong >= 0),
    PRIMARY KEY (MaPhong, MaCSVC),
    FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

-- Thêm dữ liệu vào TaiKhoan
INSERT INTO TaiKhoan VALUES 
('admin', 'admin123', N'Admin'),
('nhanvien1', '123456', N'NhanVien'),
('ktv', 'ktv123', N'KyThuatVien');

-- Thêm dữ liệu vào PhongHoc
INSERT INTO PhongHoc VALUES
('P101', N'Phòng Học A1-101', N'Phòng Lý Thuyết', 60),
('P102', N'Phòng Học A1-102', N'Phòng Máy', 40),
('P201', N'Phòng Học A2-201', N'Phòng Thực Hành', 50),
('P104', N'Phòng Học A1-104', N'Phòng Lý Thuyết', 50),
('P105', N'Phòng Máy A1-105', N'Phòng Máy', 35),
('P202', N'Phòng Thực Hành A2-202', N'Phòng Thực Hành', 45),
('P103', N'Phòng Học A1-103', N'Phòng Lý Thuyết', 45);

-- Thêm dữ liệu vào CoSoVatChat
INSERT INTO CoSoVatChat (MaCSVC, TenCSVC, LoaiCSVC, DonViTinh, TinhTrang, SoLuong) VALUES
('TB001', N'Máy chiếu Sony', N'Thiết bị trình chiếu', N'Bộ', N'Tốt', 10),
('TB002', N'Máy tính bàn', N'Thiết bị CNTT', N'Cái', N'Đang sửa', 50),
('TB003', N'Bàn học', N'Nội thất', N'Cái', N'Tốt', 20),
('TB005', N'Máy lạnh Daikin', N'Thiết bị điện', N'Bộ', N'Tốt', 5),
('TB006', N'Bảng trắng', N'Nội thất', N'Cái', N'Tốt', 15),
('TB007', N'Loa âm thanh', N'Thiết bị âm thanh', N'Bộ', N'Xuống cấp', 10),
('TB004', N'Ghế xoay', N'Nội thất', N'Cái', N'Xuống cấp', 30);

-- Thêm dữ liệu vào KiemKe
INSERT INTO KiemKe (MaKK, NgayKK, NguoiThucHien, GhiChu, TrangThaiKiemDuyet) VALUES
('KK001', '2025-03-01', N'Nguyễn Văn A', N'Đợt kiểm kê đầu năm 2025', N'Chưa kiểm duyệt'),
('KK002', '2025-04-01', N'Trần Thị B', N'Kiểm kê chuyên đề thiết bị IT', N'Chưa kiểm duyệt'),
('KK003', '2025-05-01', N'Lê Văn C', N'Kiểm kê phòng P101 tháng 5', N'Chưa kiểm duyệt'),
('KK004', '2025-06-01', N'Phạm Thị D', N'Kiểm kê phòng P102 tháng 6', N'Chưa kiểm duyệt'),
('KK005', '2025-07-01', N'Hoàng Văn E', N'Kiểm kê toàn bộ cơ sở vật chất', N'Đã kiểm duyệt');


-- Thêm dữ liệu vào ChiTietKiemKe
INSERT INTO ChiTietKiemKe VALUES
('KK001', 'P101', 'TB001', 1, N'Tốt'),
('KK001', 'P101', 'TB003', 30, N'Tốt'),
('KK001', 'P101', 'TB004', 30, N'Xuống cấp'),
('KK002', 'P102', 'TB002', 25, N'Đang sửa'),
('KK002', 'P102', 'TB003', 20, N'Tốt'),
('KK002', 'P102', 'TB004', 20, N'Tốt'),
('KK003', 'P101', 'TB005', 2, N'Tốt'),
('KK003', 'P101', 'TB006', 1, N'Tốt'),
('KK004', 'P102', 'TB002', 20, N'Đang sửa'),
('KK004', 'P102', 'TB007', 2, N'Xuống cấp'),
('KK005', 'P104', 'TB003', 25, N'Tốt'),
('KK005', 'P105', 'TB001', 1, N'Tốt');

-- Thêm dữ liệu vào BaoTri_SuaChua
INSERT INTO BaoTri_SuaChua (MaCSVC, NgayBaoTri, NoiDung, TrangThai) VALUES
('TB002', '2025-03-10', N'Sửa chữa mainboard máy tính', N'Đã hoàn thành'),
('TB004', '2025-04-03', N'Sơn lại và thay đệm ghế xoay', N'Đang thực hiện'),
('TB005', '2025-05-10', N'Bảo trì hệ thống làm lạnh', N'Đang thực hiện'),
('TB007', '2025-06-15', N'Thay dây loa và kiểm tra âm thanh', N'Đã hoàn thành'),
('TB006', '2025-07-01', N'Vệ sinh và sơn lại bảng trắng', N'Đang thực hiện');

-- Thêm dữ liệu vào XuatVanBan
INSERT INTO XuatVanBan (MaVB, NgayXuat, NguoiLap, LoaiVB, MaPhong) VALUES
('VB001', '2025-03-05', N'Nguyễn Văn A', N'KiemKe', 'P101'),
('VB002', '2025-04-02', N'Trần Thị B', N'BaoTri', 'P102'),
('VB003', '2025-05-05', N'Lê Văn C', N'KiemKe', 'P103'),
('VB004', '2025-06-02', N'Phạm Thị D', N'BaoTri', 'P105'),
('VB005', '2025-07-02', N'Hoàng Văn E', N'KiemKe', 'P104');

-- Thêm dữ liệu vào PhongHoc_CSVC
INSERT INTO PhongHoc_CSVC VALUES 
('P101', 'TB001', 10),
('P101', 'TB002', 1),
('P102', 'TB001', 5),
('P103', 'TB003', 20),
('P104', 'TB005', 1),
('P104', 'TB006', 2),
('P105', 'TB001', 1),
('P105', 'TB002', 15),
('P202', 'TB003', 20),
('P202', 'TB004', 20);

--Thêm dữ liệu vào XuatVanBan_KiemKe
INSERT INTO XuatVanBan_KiemKe (MaVB, MaKK) VALUES 
('VB003', 'KK003'),
('VB005', 'KK005');

--Thêm dữ liệu vào XuatVanBan_CSVC
INSERT INTO XuatVanBan_CSVC (MaVB, MaCSVC) VALUES 
('VB004', 'TB005'),
('VB004', 'TB007'),
('VB005', 'TB003');


-- Liệt kê số lượng từng thiết bị trong từng phòng học
SELECT 
    PH.TenPhong,
    CSVC.TenCSVC,
    CTKK.SoLuong,
    CTKK.TinhTrang
FROM ChiTietKiemKe CTKK
JOIN KiemKe KK ON CTKK.MaKK = KK.MaKK
JOIN PhongHoc PH ON CTKK.MaPhong = PH.MaPhong
JOIN CoSoVatChat CSVC ON CTKK.MaCSVC = CSVC.MaCSVC
ORDER BY PH.TenPhong, CSVC.TenCSVC;

-- Thống kê số lượng thiết bị theo loại
SELECT 
    LoaiCSVC,
    COUNT(*) AS SoLuongLoai,
    SUM(CASE WHEN TinhTrang = N'Tốt' THEN 1 ELSE 0 END) AS SoLuongTot,
    SUM(CASE WHEN TinhTrang != N'Tốt' THEN 1 ELSE 0 END) AS SoLuongKhac
FROM CoSoVatChat
GROUP BY LoaiCSVC;

-- Danh sách các thiết bị đang được bảo trì
SELECT 
    CSVC.TenCSVC,
    BT.NgayBaoTri,
    BT.NoiDung,
    BT.TrangThai
FROM BaoTri_SuaChua BT
JOIN CoSoVatChat CSVC ON BT.MaCSVC = CSVC.MaCSVC
WHERE BT.TrangThai != N'Đã hoàn thành';

-- Liệt kê phòng học có nhiều hơn 20 thiết bị được kiểm kê
SELECT 
    PH.TenPhong,
    SUM(CTKK.SoLuong) AS TongThietBi
FROM ChiTietKiemKe CTKK
JOIN PhongHoc PH ON CTKK.MaPhong = PH.MaPhong
GROUP BY PH.TenPhong
HAVING SUM(CTKK.SoLuong) > 20;

-- Truy vấn chi tiết các văn bản xuất cho thiết bị đang sửa chữa
SELECT 
    VB.MaVB,
    VB.NgayXuat,
    VB.NguoiLap,
    CSVC.TenCSVC,
    BT.NoiDung,
    BT.TrangThai
FROM XuatVanBan VB
JOIN XuatVanBan_CSVC XVB_CSVC ON VB.MaVB = XVB_CSVC.MaVB
JOIN CoSoVatChat CSVC ON XVB_CSVC.MaCSVC = CSVC.MaCSVC
JOIN BaoTri_SuaChua BT ON CSVC.MaCSVC = BT.MaCSVC
WHERE BT.TrangThai = N'Đang thực hiện';

-- Tổng hợp kiểm kê theo đợt và số phòng học đã kiểm kê
SELECT 
    KK.MaKK,
    KK.NgayKK,
    COUNT(DISTINCT CTKK.MaPhong) AS SoPhongDaKiemKe,
    COUNT(DISTINCT CTKK.MaCSVC) AS SoLoaiThietBiKiemKe
FROM KiemKe KK
JOIN ChiTietKiemKe CTKK ON KK.MaKK = CTKK.MaKK
GROUP BY KK.MaKK, KK.NgayKK;


--MỚI THÊM VÀO(Table)
--1.THÊM CỘT TrangThaiKiemDuyet vào bảng KiemKe
ALTER TABLE KiemKe
ADD TrangThaiKiemDuyet NVARCHAR(20) DEFAULT N'Chưa kiểm duyệt';

--2.Thêm cột MaPhong vào bảng XuatVanBan
ALTER TABLE XuatVanBan
ADD MaPhong VARCHAR(10);
--Tạo ràng buộc khóa ngoại
ALTER TABLE XuatVanBan
ADD CONSTRAINT FK_XuatVanBan_PhongHoc
FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong);

--MỚI THÊM VÀO(CHỨC NĂNG) CÓ THỂ BỊ TRÙNG
--Liệt kê danh sách CSVC (Mã, Tên, Số lượng, Giá, Tình trạng)
SELECT 
    MaCSVC,
    TenCSVC,
    SoLuong,
    Gia,
    TinhTrang
FROM CoSoVatChat
ORDER BY MaCSVC;
--Thêm CSVC
INSERT INTO CoSoVatChat (MaCSVC, TenCSVC, LoaiCSVC, DonViTinh, TinhTrang, SoLuong, Gia)
VALUES ('TB005', N'Máy lạnh Daikin', N'Thiết bị điện', N'Bộ', N'Tốt', 5, 20000000);
--Cập nhật CSVC
UPDATE CoSoVatChat
SET 
    TenCSVC = N'Máy lạnh Daikin 2HP',
    SoLuong = 6,
    Gia = 22000000,
    TinhTrang = N'Tốt'
WHERE MaCSVC = 'TB005';
-- Xóa các bản ghi liên quan trước
DELETE FROM ChiTietKiemKe WHERE MaCSVC = 'TB005';
DELETE FROM BaoTri_SuaChua WHERE MaCSVC = 'TB005';
DELETE FROM XuatVanBan_CSVC WHERE MaCSVC = 'TB005';
DELETE FROM PhongHoc_CSVC WHERE MaCSVC = 'TB005';
DELETE FROM CoSoVatChat WHERE MaCSVC = 'TB005';
--Liệt kê CSVC theo từng phòng
-- Liệt kê CSVC của phòng học cụ thể (VD: P101)
SELECT 
    PH.TenPhong,
    CSVC.TenCSVC,
    PCSVC.SoLuong,
    CSVC.Gia,
    CSVC.TinhTrang
FROM PhongHoc_CSVC PCSVC
JOIN PhongHoc PH ON PCSVC.MaPhong = PH.MaPhong
JOIN CoSoVatChat CSVC ON PCSVC.MaCSVC = CSVC.MaCSVC
WHERE PH.MaPhong = 'P101'
ORDER BY CSVC.TenCSVC;
--Thêm phòng học
INSERT INTO PhongHoc (MaPhong, TenPhong, LoaiPhong, SucChua)
VALUES ('P104', N'Phòng Học A1-104', N'Phòng Lý Thuyết', 50);
--Cập nhật thông tin phòng học
UPDATE PhongHoc
SET 
    TenPhong = N'Phòng Học A1-104 (Cải tạo)',
    LoaiPhong = N'Phòng Đa năng',
    SucChua = 55
WHERE MaPhong = 'P104';
--Xóa phòng học
-- Xóa các bản ghi liên quan trước
DELETE FROM ChiTietKiemKe WHERE MaPhong = 'P104';
DELETE FROM PhongHoc_CSVC WHERE MaPhong = 'P104';
DELETE FROM XuatVanBan WHERE MaPhong = 'P104';
DELETE FROM PhongHoc WHERE MaPhong = 'P104';
--Tạo biên bản kiểm kê CSVC theo phòng học
-- Tạo biên bản kiểm kê mới
INSERT INTO KiemKe (MaKK, NgayKK, NguoiThucHien, GhiChu, TrangThaiKiemDuyet)
VALUES ('KK003', '2025-05-01', N'Lê Văn C', N'Kiểm kê phòng P101 tháng 5', N'Chưa kiểm duyệt');

-- Thêm chi tiết kiểm kê
INSERT INTO ChiTietKiemKe (MaKK, MaPhong, MaCSVC, SoLuong, TinhTrang)
VALUES 
    ('KK003', 'P101', 'TB001', 1, N'Tốt'),
    ('KK003', 'P101', 'TB002', 10, N'Tốt'),
    ('KK003', 'P101', 'TB003', 30, N'Tốt');
--Quản lý biên bản kiểm kê
-- Liệt kê danh sách biên bản chưa kiểm duyệt
SELECT 
    MaKK,
    NgayKK,
    NguoiThucHien,
    GhiChu
FROM KiemKe
WHERE TrangThaiKiemDuyet = N'Chưa kiểm duyệt';

-- Liệt kê danh sách biên bản đã kiểm duyệt
SELECT 
    MaKK,
    NgayKK,
    NguoiThucHien,
    GhiChu
FROM KiemKe
WHERE TrangThaiKiemDuyet = N'Đã kiểm duyệt';

-- Tạo biên bản kiểm kê mới
INSERT INTO KiemKe (MaKK, NgayKK, NguoiThucHien, GhiChu, TrangThaiKiemDuyet)
VALUES ('KK004', '2025-06-01', N'Phạm Thị D', N'Kiểm kê phòng P102 tháng 6', N'Chưa kiểm duyệt');

-- Kiểm duyệt biên bản
UPDATE KiemKe
SET TrangThaiKiemDuyet = N'Đã kiểm duyệt'
WHERE MaKK = 'KK004';

-- Chỉnh sửa biên bản chưa kiểm duyệt
UPDATE KiemKe
SET 
    GhiChu = N'Kiểm kê phòng P102 (Cập nhật tháng 6)',
    NguoiThucHien = N'Phạm Thị D'
WHERE MaKK = 'KK004' AND TrangThaiKiemDuyet = N'Chưa kiểm duyệt';

-- Xem thông tin biên bản đã kiểm duyệt
SELECT 
    KK.MaKK,
    KK.NgayKK,
    KK.NguoiThucHien,
    KK.GhiChu,
    CTKK.MaPhong,
    PH.TenPhong,
    CSVC.TenCSVC,
    CTKK.SoLuong,
    CTKK.TinhTrang
FROM KiemKe KK
JOIN ChiTietKiemKe CTKK ON KK.MaKK = CTKK.MaKK
JOIN PhongHoc PH ON CTKK.MaPhong = PH.MaPhong
JOIN CoSoVatChat CSVC ON CTKK.MaCSVC = CSVC.MaCSVC
WHERE KK.TrangThaiKiemDuyet = N'Đã kiểm duyệt' AND KK.MaKK = 'KK001';

-- Xóa biên bản đã kiểm duyệt
DELETE FROM ChiTietKiemKe WHERE MaKK = 'KK001';
DELETE FROM XuatVanBan_KiemKe WHERE MaKK = 'KK001';
DELETE FROM KiemKe WHERE MaKK = 'KK001' AND TrangThaiKiemDuyet = N'Đã kiểm duyệt';
--Xuất file (SQL Server, COPY TRÊN MẠNG KO BIẾT ĐÚNG KO)
-- Xuất danh sách CSVC ra file CSV bằng SQL Server Management Studio (SSMS):
-- 1. Chạy truy vấn sau trong SSMS:
SELECT 
    MaCSVC,
    TenCSVC,
    SoLuong,
    Gia,
    TinhTrang
FROM CoSoVatChat;

-- 2. Trong SSMS, nhấp chuột phải vào kết quả -> "Save Results As" -> Chọn định dạng CSV.

-- Hoặc sử dụng BCP (Bulk Copy Program) để xuất file từ dòng lệnh:
-- Thay YourDatabase và YourServer bằng tên cơ sở dữ liệu và máy chủ của bạn
-- Ví dụ:
-- bcp "SELECT MaCSVC, TenCSVC, SoLuong, Gia, TinhTrang FROM YourDatabase.dbo.CoSoVatChat" queryout "C:\path\to\csv\csvc_export.csv" -c -T -S YourServer