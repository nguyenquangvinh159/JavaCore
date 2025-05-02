CREATE TABLE TaiKhoan(
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(50),
    VaiTro NVARCHAR(20)
);

CREATE TABLE CoSoVatChat(
    MaCSVC VARCHAR(10) PRIMARY KEY,
    TenCSVC NVARCHAR(100),
    LoaiCSVC NVARCHAR(50),
    DonViTinh NVARCHAR(20),
    TinhTrang NVARCHAR(50)
);

CREATE TABLE PhongHoc(
    MaPhong VARCHAR(10) PRIMARY KEY,
    TenPhong NVARCHAR(100),
    LoaiPhong NVARCHAR(50),
    SucChua INT
);

CREATE TABLE KiemKe(
    MaKK VARCHAR(10) PRIMARY KEY,
    NgayKK DATE,
    NguoiThucHien NVARCHAR(100),
    GhiChu NVARCHAR(200)
);

CREATE TABLE ChiTietKiemKe(
    MaKK VARCHAR(10),
    MaPhong VARCHAR(10),
    MaCSVC VARCHAR(10),
    SoLuong INT,
    TinhTrang NVARCHAR(50),
    FOREIGN KEY (MaKK) REFERENCES KiemKe(MaKK),
    FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

CREATE TABLE BaoTri_SuaChua(
    MaBT INT PRIMARY KEY IDENTITY(1,1),
    MaCSVC VARCHAR(10),
    NgayBaoTri DATE,
    NoiDung NVARCHAR(200),
    TrangThai NVARCHAR(50),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

CREATE TABLE XuatVanBan(
    MaVB VARCHAR(10) PRIMARY KEY,
    NgayXuat DATE,
    NguoiLap NVARCHAR(100),
    LoaiVB NVARCHAR(50) -- "KiemKe", "BaoTri",...
);

CREATE TABLE XuatVanBan_KiemKe(
    MaVB VARCHAR(10),
    MaKK VARCHAR(10),
    FOREIGN KEY (MaVB) REFERENCES XuatVanBan(MaVB),
    FOREIGN KEY (MaKK) REFERENCES KiemKe(MaKK)
)

CREATE TABLE XuatVanBan_CSVC(
    MaVB VARCHAR(10),
    MaCSVC VARCHAR(10),
    FOREIGN KEY (MaVB) REFERENCES XuatVanBan(MaVB),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
)

INSERT INTO TaiKhoan VALUES 
('admin', 'admin123', N'Admin'),
('nhanvien1', '123456', N'NhanVien'),
('ktv', 'ktv123', N'KyThuatVien');

INSERT INTO KiemKe VALUES
('KK001', '2025-03-01', N'Nguyễn Văn A', N'Đợt kiểm kê đầu năm 2025'),
('KK002', '2025-04-01', N'Trần Thị B', N'Kiểm kê chuyên đề thiết bị IT');

INSERT INTO ChiTietKiemKe VALUES
('KK001', 'P101', 'TB001', 1, N'Tốt'),
('KK001', 'P101', 'TB003', 30, N'Tốt'),
('KK001', 'P101', 'TB004', 30, N'Xuống cấp'),
('KK002', 'P102', 'TB002', 25, N'Đang sửa'),
('KK002', 'P102', 'TB003', 20, N'Tốt'),
('KK002', 'P102', 'TB004', 20, N'Tốt');

INSERT INTO BaoTri_SuaChua(MaCSVC, NgayBaoTri, NoiDung, TrangThai) VALUES
('TB002', '2025-03-10', N'Sửa chữa mainboard máy tính', N'Đã hoàn thành'),
('TB004', '2025-04-03', N'Sơn lại và thay đệm ghế xoay', N'Đang thực hiện');

INSERT INTO XuatVanBan VALUES
('VB001', '2025-03-05', N'Nguyễn Văn A', N'KiemKe'),
('VB002', '2025-04-02', N'Trần Thị B', N'BaoTri');

--Liệt kê số lượng từng thiết bị trong từng phòng học

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

--Thống kê số lượng thiết bị theo loại

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

--Liệt kê phòng học có nhiều hơn 20 thiết bị được kiểm kê

SELECT 
    PH.TenPhong,
    SUM(CTKK.SoLuong) AS TongThietBi
FROM ChiTietKiemKe CTKK
JOIN PhongHoc PH ON CTKK.MaPhong = PH.MaPhong
GROUP BY PH.TenPhong
HAVING SUM(CTKK.SoLuong) > 20;

--Truy vấn chi tiết các văn bản xuất cho thiết bị đang sửa chữa

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

ALTER TABLE CoSoVatChat
ADD SoLuong INT CHECK (SoLuong >= 0) DEFAULT 0;
SELECT * FROM CoSoVatChat;

UPDATE CoSoVatChat SET SoLuong = 10 WHERE MaCSVC = 'TB001';
UPDATE CoSoVatChat SET SoLuong = 50 WHERE MaCSVC = 'TB002';
UPDATE CoSoVatChat SET SoLuong = 20 WHERE MaCSVC = 'TB003';
UPDATE CoSoVatChat SET SoLuong = 30 WHERE MaCSVC = 'TB004';

CREATE TABLE PhongHoc_CSVC (
    MaPhong VARCHAR(10),
    MaCSVC VARCHAR(10),
    SoLuong INT CHECK (SoLuong >= 0),

    PRIMARY KEY (MaPhong, MaCSVC),
    FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong),
    FOREIGN KEY (MaCSVC) REFERENCES CoSoVatChat(MaCSVC)
);

INSERT INTO PhongHoc VALUES
('P101', N'Phòng Học A1-101', N'Phòng Lý Thuyết', 60),
('P102', N'Phòng Học A1-102', N'Phòng Máy', 40),
('P201', N'Phòng Học A2-201', N'Phòng Thực Hành', 50),
('P103', N'Phòng Học A1-103', N'Phòng Lý Thuyết', 45);

INSERT INTO CoSoVatChat VALUES
('TB001', N'Máy chiếu Sony', N'Thiết bị trình chiếu', N'Bộ', N'Tốt'),
('TB002', N'Máy tính bàn', N'Thiết bị CNTT', N'Cái', N'Đang sửa'),
('TB003', N'Bàn học', N'Nội thất', N'Cái', N'Tốt'),
('TB004', N'Ghế xoay', N'Nội thất', N'Cái', N'Xuống cấp');

INSERT INTO PhongHoc_CSVC VALUES 
('P101', 'TB001', 10),
('P101', 'TB002', 1),
('P102', 'TB001', 5),
('P103', 'TB003', 20);


SELECT tb.TenCSVC, tb.LoaiCSVC, pcs.SoLuong, tb.DonViTinh
FROM PhongHoc_CSVC pcs
JOIN CoSoVatChat tb ON pcs.MaCSVC = tb.MaCSVC
WHERE pcs.MaPhong = 'P101';

-- 1. Thêm cột MaPhong vào bảng XuatVanBan (nếu chưa có)
ALTER TABLE XuatVanBan
ADD MaPhong VARCHAR(10);

-- 2. Tạo khóa ngoại liên kết với bảng PhongHoc
ALTER TABLE XuatVanBan
ADD CONSTRAINT FK_XuatVanBan_PhongHoc
FOREIGN KEY (MaPhong) REFERENCES PhongHoc(MaPhong);

UPDATE XuatVanBan
SET MaPhong = 'P101'
WHERE MaVB = 'VB001';

UPDATE XuatVanBan
SET MaPhong = 'P102'
WHERE MaVB = 'VB002';


