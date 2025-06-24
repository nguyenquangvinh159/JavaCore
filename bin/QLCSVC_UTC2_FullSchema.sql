-- ========================================================
-- Hệ thống Quản lý Cơ sở Vật chất UTC2 - SQL Server Schema (Cập nhật)
-- Updated on 2025-06-13
-- ========================================================

-- 1. Tạo cơ sở dữ liệu
CREATE DATABASE QLCSVC_u2;
GO

USE QLCSVC_u2;
GO

-- 2. Bảng TaiKhoan - Đăng nhập và phân quyền
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(50) PRIMARY KEY,
    MatKhau VARCHAR(100) NOT NULL,
    VaiTro VARCHAR(20) NOT NULL CHECK (VaiTro IN ('sinhvien', 'nhanvien', 'admin', 'quanly', 'kythuat')),
    HoTen NVARCHAR(100) NOT NULL,
    Avatar NVARCHAR(255) DEFAULT 'default_avatar.png', -- avatar
    Email NVARCHAR(100), -- email
    SoDienThoai VARCHAR(15), -- Thêm số điện thoại
    NgayTao DATETIME DEFAULT GETDATE(), -- Ngày tạo tài khoản
    TrangThai BIT DEFAULT 1 -- 1: Hoạt động, 0: Vô hiệu hóa
);
ALTER TABLE TaiKhoan
ADD 
    MaXacNhan VARCHAR(100),
    HanHieuLucMaXacNhan DATETIME,
    DaXacThucEmail BIT DEFAULT 0;
UPDATE TaiKhoan
SET DaXacThucEmail = 1

-- 3. Bảng ThietBi - Danh sách thiết bị 
CREATE TABLE ThietBi (
    MaTB VARCHAR(10) PRIMARY KEY,
    TenTB NVARCHAR(100) NOT NULL,
    LoaiTB NVARCHAR(50),
    SoLuong INT CHECK (SoLuong >= 0),
    TinhTrang NVARCHAR(50),
    DonViTinh NVARCHAR(50),
    HinhAnh NVARCHAR(255), 
    GiaTriTaiSan DECIMAL(15,2), -- Thêm giá trị tài sản
    NgayMua DATE, -- Ngày mua
    HanBaoHanh DATE, -- Hạn bảo hành
    NhaCungCap NVARCHAR(100), -- Nhà cung cấp
    GhiChu NVARCHAR(500) -- Ghi chú thêm
);

-- 4. Bảng PhongHoc - Danh sách phòng học 
CREATE TABLE PhongHoc (
    MaPhong VARCHAR(10) PRIMARY KEY,
    TenPhong NVARCHAR(50) NOT NULL,
    ToaNha NVARCHAR(50),
    Tang INT CHECK (Tang >= 0),
    SucChua INT, -- Sức chứa phòng
    LoaiPhong NVARCHAR(50), -- Loại phòng (lý thuyết, thực hành, hội thảo...)
    TrangThai NVARCHAR(50) DEFAULT N'Hoạt động' -- Trạng thái phòng
);

-- 5. Bảng CSVC_Phong - Thiết bị trong từng phòng học
CREATE TABLE CSVC_Phong (
    MaPhong VARCHAR(10) FOREIGN KEY REFERENCES PhongHoc(MaPhong),
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    SoLuong INT CHECK (SoLuong >= 0),
    NgayLapDat DATE DEFAULT GETDATE(), -- Ngày lắp đặt
    PRIMARY KEY (MaPhong, MaTB)
);

-- 6. Bảng YeuCauMuon - Yêu cầu mượn thiết bị
CREATE TABLE YeuCauMuon (
    MaYeuCau INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    SoLuongMuon INT CHECK (SoLuongMuon > 0),
    NgayMuon DATE,
    NgayTraDuKien DATE, -- Ngày trả dự kiến
    NgayTraThucTe DATE, -- Ngày trả thực tế
    MucDichSuDung NVARCHAR(200), -- Mục đích sử dụng
    TrangThai NVARCHAR(50) DEFAULT N'Chờ duyệt',
    NguoiDuyet VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap), -- Người duyệt
    NgayDuyet DATETIME, -- Ngày duyệt
    LyDoTuChoi NVARCHAR(500) -- Lý do từ chối (nếu có)
);
ALTER TABLE YeuCauMuon
ADD CONSTRAINT CK_YeuCauMuon_NgayTraDuKien
CHECK (NgayTraDuKien IS NULL OR NgayTraDuKien >= NgayMuon);

ALTER TABLE YeuCauMuon
ADD CONSTRAINT CK_YeuCauMuon_NgayTraThucTe
CHECK (NgayTraThucTe IS NULL OR NgayTraThucTe >= NgayMuon);

-- 7. Bảng BaoCaoSuCo - Báo cáo sự cố thiết bị
CREATE TABLE BaoCaoSuCo (
    MaBC INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    MaPhong VARCHAR(10) FOREIGN KEY REFERENCES PhongHoc(MaPhong), -- Thêm phòng xảy ra sự cố
    MoTa NVARCHAR(500),
    MucDoNghiemTrong NVARCHAR(20) DEFAULT N'Thấp', -- Thấp, Trung bình, Cao, Nghiêm trọng
    NgayBaoCao DATE DEFAULT GETDATE(),
    TrangThai NVARCHAR(50) DEFAULT N'Chưa xử lý',
    NguoiXuLy VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap), -- Người xử lý
    NgayXuLy DATETIME, -- Ngày xử lý
    KetQuaXuLy NVARCHAR(500) -- Kết quả xử lý
);

-- 8. Bảng LichSuCapNhat - Lịch sử cập nhật thiết bị
CREATE TABLE LichSuCapNhat (
    MaCapNhat INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    NgayCapNhat DATE DEFAULT GETDATE(),
    NoiDung NVARCHAR(500),
    LoaiCapNhat NVARCHAR(50) -- Thêm, Sửa, Xóa, Bảo trì
);

-- 9. Bảng KiemKe - Đợt kiểm kê 
CREATE TABLE KiemKe (
    MaKiemKe INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    MaPhong VARCHAR(10) FOREIGN KEY REFERENCES PhongHoc(MaPhong),
    NgayKiemKe DATE DEFAULT GETDATE(),
    LoaiKiemKe NVARCHAR(50) DEFAULT N'Định kỳ', -- Định kỳ, Đột xuất, Bàn giao
    TrangThai NVARCHAR(50) DEFAULT N'Đang thực hiện', -- Đang thực hiện, Hoàn thành
    GhiChu NVARCHAR(500)
);
ALTER TABLE KiemKe
ADD CONSTRAINT CK_KiemKe_NgayKiemKe
CHECK (NgayKiemKe <= GETDATE());

-- 10. Bảng ChiTietKiemKe - Thiết bị cụ thể trong đợt kiểm kê 
CREATE TABLE ChiTietKiemKe (
    MaKiemKe INT FOREIGN KEY REFERENCES KiemKe(MaKiemKe),
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    SoLuongSoSach INT, -- Số lượng theo sổ sách
    SoLuongThucTe INT CHECK (SoLuongThucTe >= 0), -- Số lượng thực tế
    TinhTrang NVARCHAR(50),
    GhiChu NVARCHAR(200), -- Ghi chú riêng cho từng thiết bị
    PRIMARY KEY (MaKiemKe, MaTB)
);
ALTER TABLE ChiTietKiemKe
ADD CONSTRAINT CK_ChiTietKiemKe_SoLuong
CHECK (SoLuongThucTe <= SoLuongSoSach);


-- 11. Bảng LichBaoTri - Lịch bảo trì thiết bị 
CREATE TABLE LichBaoTri (
    MaBaoTri INT IDENTITY(1,1) PRIMARY KEY,
    MaTB VARCHAR(10) FOREIGN KEY REFERENCES ThietBi(MaTB),
    LoaiBaoTri NVARCHAR(50) DEFAULT N'Định kỳ', -- Định kỳ, Sửa chữa, Bảo dưỡng
    NgayDuKien DATE,
    NgayThucTe DATE,
    NoiDung NVARCHAR(500),
    NguoiThucHien VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap), -- Người thực hiện
    ChiPhi DECIMAL(10,2), -- Chi phí bảo trì
    TrangThai NVARCHAR(50) DEFAULT N'Chưa thực hiện'
);
ALTER TABLE LichBaoTri
ADD CONSTRAINT CK_LichBaoTri_NgayDuKien
CHECK (NgayDuKien IS NULL OR NgayDuKien >= '2000-01-01');

ALTER TABLE LichBaoTri
ADD CONSTRAINT CK_LichBaoTri_NgayThucTe
CHECK (NgayThucTe IS NULL OR NgayThucTe >= NgayDuKien);


-- 12. Bảng LichSuDangNhap - Lưu lịch sử đăng nhập 
CREATE TABLE LichSuDangNhap (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    ThoiGianDangNhap DATETIME DEFAULT GETDATE(),
    ThoiGianDangXuat DATETIME, -- Thời gian đăng xuất
    DiaChiIP VARCHAR(50),
    ThietBi NVARCHAR(100), -- Loại thiết bị đăng nhập
    TrinhDuyet NVARCHAR(100) -- Trình duyệt sử dụng
);

-- 13. Bảng PhanQuyen - Quản lý phân quyền chi tiết 
CREATE TABLE PhanQuyen (
    MaPhanQuyen INT IDENTITY(1,1) PRIMARY KEY,
    VaiTro VARCHAR(20) NOT NULL,
    ChucNang NVARCHAR(100) NOT NULL, -- Tên chức năng
    MaChucNang VARCHAR(20) NOT NULL, -- Mã chức năng để code xử lý
    CoQuyen BIT DEFAULT 0 -- 1: Có quyền, 0: Không có quyền
);

-- 14. Bảng ThongBao - Hệ thống thông báo
CREATE TABLE ThongBao (
    MaThongBao INT IDENTITY(1,1) PRIMARY KEY,
    TieuDe NVARCHAR(200) NOT NULL,
    NoiDung NVARCHAR(1000),
    NguoiGui VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    NguoiNhan VARCHAR(50) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    NgayGui DATETIME DEFAULT GETDATE(),
    DaDoc BIT DEFAULT 0, -- 0: Chưa đọc, 1: Đã đọc
    LoaiThongBao NVARCHAR(50) -- Hệ thống, Cá nhân, Khẩn cấp
);

-- 15. Dữ liệu mẫu cập nhật
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, VaiTro, HoTen, Avatar, Email, SoDienThoai) VALUES 
('admin2', '123456', 'admin', N'Quản trị viên hệ thống', 'admin_avatar.png', 'admin2@utc2.edu.vn', '0123456780'),
('admin1', '123456', 'admin', N'Quản trị viên hệ thống', 'admin_avatar.png', 'admin@utc2.edu.vn', '0123456789'),
('nhanvien1', '123456', 'nhanvien', N'Nguyễn Văn An', 'default_avatar.png', 'nva@utc2.edu.vn', '0987654321'),
('sinhvien1', '123456', 'sinhvien', N'Lê Thị Bình', 'default_avatar.png', 'ltb@student.utc2.edu.vn', '0912345678'),
('quanly1', '123456', 'quanly', N'Trần Văn Quản', 'manager_avatar.png', 'tvq@utc2.edu.vn', '0945678912'),
('kythuat1', '123456', 'kythuat', N'Phạm Thị Kỹ', 'tech_avatar.png', 'ptk@utc2.edu.vn', '0934567891');

INSERT INTO ThietBi (MaTB, TenTB, LoaiTB, SoLuong, TinhTrang, DonViTinh, HinhAnh, GiaTriTaiSan, NgayMua, NhaCungCap) VALUES 
('TB01', N'Máy chiếu Sony VPL-DX271', N'Thiết bị trình chiếu', 5, N'Hoạt động tốt', N'Cái', 'projector_sony.jpg', 15000000, '2024-01-15', N'Sony Vietnam'),
('TB02', N'Quạt đứng Toshiba F-LSA10', N'Thiết bị làm mát', 10, N'Bình thường', N'Cái', 'fan_toshiba.jpg', 2500000, '2023-08-20', N'Toshiba Vietnam'),
('TB03', N'Micro không dây Shure', N'Thiết bị âm thanh', 3, N'Hư hỏng nhẹ', N'Cái', 'mic_shure.jpg', 8000000, '2023-05-10', N'Shure Incorporated');

INSERT INTO PhongHoc (MaPhong, TenPhong, ToaNha, Tang, SucChua, LoaiPhong, TrangThai) VALUES 
('P101', N'Phòng học 101', N'Tòa A', 1, 50, N'Lý thuyết', N'Hoạt động'),
('P202', N'Phòng học 202', N'Tòa B', 2, 30, N'Thực hành', N'Hoạt động'),
('P303', N'Phòng hội thảo 303', N'Tòa C', 3, 100, N'Hội thảo', N'Hoạt động');

-- Cập nhật dữ liệu mẫu cho các bảng khác...
INSERT INTO CSVC_Phong (MaPhong, MaTB, SoLuong, NgayLapDat) VALUES 
('P101', 'TB01', 1, '2024-02-01'),
('P101', 'TB02', 2, '2023-09-01'),
('P202', 'TB03', 1, '2023-06-01');

-- Dữ liệu phân quyền mẫu
INSERT INTO PhanQuyen (VaiTro, ChucNang, MaChucNang, CoQuyen) VALUES
-- Admin có tất cả quyền
('admin', N'Quản lý tài khoản', 'MANAGE_ACCOUNT', 1),
('admin', N'Quản lý thiết bị', 'MANAGE_DEVICE', 1),
('admin', N'Quản lý phòng học', 'MANAGE_ROOM', 1),
('admin', N'Duyệt yêu cầu mượn', 'APPROVE_BORROW', 1),
('admin', N'Xem báo cáo', 'VIEW_REPORT', 1),
('admin', N'Quản lý kiểm kê', 'MANAGE_INVENTORY', 1),
('admin', N'Quản lý bảo trì', 'MANAGE_MAINTENANCE', 1),

-- Nhân viên
('nhanvien', N'Quản lý thiết bị', 'MANAGE_DEVICE', 1),
('nhanvien', N'Duyệt yêu cầu mượn', 'APPROVE_BORROW', 1),
('nhanvien', N'Xem báo cáo', 'VIEW_REPORT', 1),
('nhanvien', N'Quản lý kiểm kê', 'MANAGE_INVENTORY', 1),

-- Sinh viên
('sinhvien', N'Yêu cầu mượn thiết bị', 'REQUEST_BORROW', 1),
('sinhvien', N'Báo cáo sự cố', 'REPORT_ISSUE', 1),
('sinhvien', N'Lịch sử mượn thiết bị', 'lichsu_muon', 1),
('sinhvien', N'Lịch sử báo cáo', 'lichsu_baocao', 1),


-- Quản lý
('quanly', N'Xem báo cáo', 'VIEW_REPORT', 1),
('quanly', N'Quản lý thiết bị', 'MANAGE_DEVICE', 1),
('quanly', N'Quản lý phòng học', 'MANAGE_ROOM', 1),
('quanly', N'Duyệt yêu cầu mượn', 'APPROVE_BORROW', 1),

-- Kỹ thuật
('kythuat', N'Quản lý bảo trì', 'MANAGE_MAINTENANCE', 1),
('kythuat', N'Xử lý sự cố', 'HANDLE_ISSUE', 1),
('kythuat', N'Quản lý thiết bị', 'MANAGE_DEVICE', 1);

-- Tạo các index để tối ưu hiệu suất
CREATE INDEX IX_TaiKhoan_VaiTro ON TaiKhoan(VaiTro);
CREATE INDEX IX_ThietBi_LoaiTB ON ThietBi(LoaiTB);
CREATE INDEX IX_YeuCauMuon_TrangThai ON YeuCauMuon(TrangThai);
CREATE INDEX IX_BaoCaoSuCo_TrangThai ON BaoCaoSuCo(TrangThai);
CREATE INDEX IX_LichSuDangNhap_ThoiGian ON LichSuDangNhap(ThoiGianDangNhap);
