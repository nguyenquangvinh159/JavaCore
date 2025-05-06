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

ALTER TABLE PhongHoc_CSVC
ADD TinhTrang NVARCHAR(50);

INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro) VALUES
('user01', 'pass01', N'NhanVien'),
('user02', 'pass02', N'NhanVien'),
('user03', 'pass03', N'NhanVien'),
('user04', 'pass04', N'NhanVien'),
('user05', 'pass05', N'NhanVien'),
('user06', 'pass06', N'NhanVien'),
('user07', 'pass07', N'NhanVien'),
('user08', 'pass08', N'NhanVien'),
('user09', 'pass09', N'NhanVien'),
('user10', 'pass10', N'NhanVien'),
('user11', 'pass11', N'NhanVien'),
('user12', 'pass12', N'NhanVien'),
('user13', 'pass13', N'NhanVien'),
('user14', 'pass14', N'NhanVien'),
('user15', 'pass15', N'NhanVien');

INSERT INTO CoSoVatChat (MaCSVC, TenCSVC, LoaiCSVC, DonViTinh, TinhTrang, SoLuong) VALUES
('TB005', N'Máy in HP', N'Thiết bị văn phòng', N'Cái', N'Tốt', 5),
('TB006', N'Máy quét Canon', N'Thiết bị văn phòng', N'Cái', N'Tốt', 3),
('TB007', N'Bảng trắng', N'Thiết bị giảng dạy', N'Cái', N'Tốt', 10),
('TB008', N'Micro không dây', N'Thiết bị âm thanh', N'Cái', N'Tốt', 7),
('TB009', N'Loa JBL', N'Thiết bị âm thanh', N'Cái', N'Tốt', 4),
('TB010', N'Máy chiếu Epson', N'Thiết bị trình chiếu', N'Cái', N'Tốt', 6),
('TB011', N'Bàn giáo viên', N'Nội thất', N'Cái', N'Tốt', 2),
('TB012', N'Ghế giáo viên', N'Nội thất', N'Cái', N'Tốt', 2),
('TB013', N'Máy lạnh Daikin', N'Thiết bị điện', N'Cái', N'Tốt', 3),
('TB014', N'Quạt trần', N'Thiết bị điện', N'Cái', N'Tốt', 8),
('TB015', N'Máy tính xách tay', N'Thiết bị CNTT', N'Cái', N'Tốt', 10),
('TB016', N'Máy tính bảng', N'Thiết bị CNTT', N'Cái', N'Tốt', 12),
('TB017', N'Bảng tương tác', N'Thiết bị giảng dạy', N'Cái', N'Tốt', 2),
('TB018', N'Máy ảnh kỹ thuật số', N'Thiết bị ghi hình', N'Cái', N'Tốt', 3),
('TB019', N'Máy quay phim', N'Thiết bị ghi hình', N'Cái', N'Tốt', 2);

INSERT INTO PhongHoc (MaPhong, TenPhong, LoaiPhong, SucChua) VALUES
('P104', N'Phòng Học A1-104', N'Phòng Lý Thuyết', 60),
('P106', N'Phòng Học A1-106', N'Phòng Thực Hành', 50),
('P107', N'Phòng Học A1-107', N'Phòng Lý Thuyết', 45),
('P108', N'Phòng Học A1-108', N'Phòng Máy', 40),
('P109', N'Phòng Học A1-109', N'Phòng Thực Hành', 50),
('P110', N'Phòng Học A1-110', N'Phòng Lý Thuyết', 60),
('P111', N'Phòng Học A1-111', N'Phòng Máy', 40),
('P112', N'Phòng Học A1-112', N'Phòng Thực Hành', 50),
('P113', N'Phòng Học A1-113', N'Phòng Lý Thuyết', 45),
('P114', N'Phòng Học A1-114', N'Phòng Máy', 40),
('P115', N'Phòng Học A1-115', N'Phòng Thực Hành', 50),
('P116', N'Phòng Học A1-116', N'Phòng Lý Thuyết', 60),
('P117', N'Phòng Học A1-117', N'Phòng Máy', 40),
('P118', N'Phòng Học A1-118', N'Phòng Thực Hành', 50);

INSERT INTO KiemKe(MaKK, NgayKK, NguoiThucHien, GhiChu) VALUES
('KK003', '2024-06-01', N'Người thực hiện 3', N'Ghi chú KK003'),
('KK004', '2024-06-02', N'Người thực hiện 4', N'Ghi chú KK004'),
('KK005', '2024-06-03', N'Người thực hiện 5', N'Ghi chú KK005'),
('KK006', '2024-06-04', N'Người thực hiện 6', N'Ghi chú KK006'),
('KK007', '2024-06-05', N'Người thực hiện 7', N'Ghi chú KK007'),
('KK008', '2024-06-06', N'Người thực hiện 8', N'Ghi chú KK008'),
('KK009', '2024-06-07', N'Người thực hiện 9', N'Ghi chú KK009'),
('KK010', '2024-06-08', N'Người thực hiện 10', N'Ghi chú KK010');

INSERT INTO ChiTietKiemKe (MaKK, MaPhong, MaCSVC, SoLuong, TinhTrang) VALUES
('KK003', 'P104', 'TB005', 2, N'Tốt'),
('KK003', 'P104', 'TB006', 1, N'Tốt'),
('KK004', 'P105', 'TB007', 3, N'Tốt'),
('KK004', 'P105', 'TB008', 2, N'Tốt'),
('KK005', 'P106', 'TB009', 4, N'Tốt'),
('KK005', 'P106', 'TB010', 1, N'Tốt'),
('KK006', 'P107', 'TB011', 2, N'Tốt'),
('KK006', 'P107', 'TB012', 2, N'Tốt'),
('KK007', 'P108', 'TB013', 1, N'Tốt'),
('KK007', 'P108', 'TB014', 2, N'Tốt'),
('KK008', 'P109', 'TB015', 5, N'Tốt'),
('KK008', 'P109', 'TB016', 3, N'Tốt'),
('KK009', 'P110', 'TB017', 1, N'Tốt'),
('KK009', 'P110', 'TB018', 2, N'Tốt'),
('KK010', 'P111', 'TB019', 1, N'Tốt');

INSERT INTO BaoTri_SuaChua (MaCSVC, NgayBaoTri, NoiDung, TrangThai) VALUES
('TB005', '2025-05-10', N'Bảo trì định kỳ', N'Đã hoàn thành'),
('TB006', '2025-06-15', N'Sửa chữa lỗi phần mềm', N'Đang thực hiện'),
('TB007', '2025-07-20', N'Thay thế linh kiện', N'Đã hoàn thành'),
('TB008', '2025-08-25', N'Bảo trì định kỳ', N'Đang thực hiện'),
('TB009', '2025-09-30', N'Sửa chữa lỗi phần cứng', N'Đã hoàn thành'),
('TB010', '2025-10-05', N'Thay thế bóng đèn', N'Đang thực hiện');
 
 INSERT INTO XuatVanBan (MaVB, NgayXuat, LoaiVB) VALUES
('VB003', '2025-01-10', N'Tổng hợp'),
('VB004', '2025-02-15', N'Xuất kho'),
('VB005', '2025-03-20', N'Bàn giao'),
('VB006', '2025-04-01', N'Tổng hợp'),
('VB007', '2025-04-12', N'Sửa chữa'),
('VB008', '2025-05-05', N'Kiểm kê'),
('VB009', '2025-06-01', N'Nhập kho'),
('VB010', '2025-06-30', N'Tổng hợp'),
('VB011', '2025-07-10', N'Điều chuyển'),
('VB012', '2025-08-01', N'Bảo trì'),
('VB013', '2025-12-20', N'Tổng hợp'),
('VB014', '2025-09-10', N'Tổng hợp'),
('VB015', '2025-10-01', N'Sửa chữa');

INSERT INTO XuatVanBan_KiemKe (MaVB, MaKK) VALUES
('VB001', 'KK001'),
('VB001', 'KK002'),
('VB003', 'KK003'),
('VB006', 'KK004'),
('VB008', 'KK005'),
('VB010', 'KK006'),
('VB014', 'KK007'),
('VB013', 'KK008'),
('VB014', 'KK009'),
('VB008', 'KK010'),
('VB003', 'KK010'),
('VB006', 'KK002'),
('VB014', 'KK005'),
('VB001', 'KK007'),
('VB006', 'KK003');

INSERT INTO XuatVanBan_CSVC (MaVB, MaCSVC) VALUES
('VB002', 'TB001'),
('VB002', 'TB002'),
('VB004', 'TB003'),
('VB005', 'TB004'),
('VB006', 'TB005'),
('VB007', 'TB006'),
('VB008', 'TB007'),
('VB009', 'TB008'),
('VB010', 'TB009'),
('VB011', 'TB010'),
('VB012', 'TB011'),
('VB013', 'TB012'),
('VB014', 'TB013'),
('VB015', 'TB014'),
('VB013', 'TB015');

INSERT INTO PhongHoc_CSVC (MaPhong, MaCSVC, SoLuong) VALUES
('P101', 'TB001', 20),
('P109', 'TB002', 1),
('P101', 'TB003', 1),
('P109', 'TB004', 25),
('P108', 'TB005', 1),
('P106', 'TB006', 1),
('P102', 'TB007', 30),
('P108', 'TB008', 2),
('P109', 'TB009', 1),
('P103', 'TB010', 15),
('P112', 'TB011', 1),
('P105', 'TB012', 1),
('P104', 'TB013', 1),
('P103', 'TB014', 1),
('P101', 'TB015', 1);
