-- Tạo cơ sở dữ liệu QuanLyCv
CREATE DATABASE QuanLyCV;
GO

-- Sử dụng cơ sở dữ liệu QuanLyCv
USE QuanLyCV;
GO

-- Tạo bảng Vai_Tro
CREATE TABLE Vai_Tro (
    vai_tro_id INT PRIMARY KEY IDENTITY(1,1),
    ten_vai_tro VARCHAR(50) NOT NULL
);

-- Thêm các giá trị mặc định cho Vai_Tro
INSERT INTO Vai_Tro (ten_vai_tro) VALUES ('Admin'), ('Manager'), ('Member');
GO

-- Tạo bảng Nguoi_Dung


-- Tạo bảng Phong_Ban
CREATE TABLE Phong_Ban (
    phong_ban_id INT PRIMARY KEY IDENTITY(1,1),
    ma_phong_ban VARCHAR(50) NOT NULL UNIQUE,
    ten_phong_ban VARCHAR(100) NOT NULL,
    trang_thai BIT DEFAULT 1,
    ngay_tao DATETIME DEFAULT GETDATE(),
    ngay_cap_nhat DATETIME DEFAULT GETDATE()
);
GO

CREATE TABLE Level (
level_id  INT PRIMARY KEY IDENTITY(1,1),
ma_level varchar(100) not null,
ten_level varchar(100) not null,
trang_thai BIT DEFAULT 1
);


-- Tạo bảng Vi_Tri_Cong_Viec
CREATE TABLE Vi_Tri_Cong_Viec (
    vi_tri_id INT PRIMARY KEY IDENTITY(1,1),
    ma_vi_tri VARCHAR(50) NOT NULL UNIQUE,
    ten_vi_tri VARCHAR(100) NOT NULL,
   -- level NVARCHAR(20) NOT NULL, -- Sử dụng NVARCHAR để thay thế ENUM
    phong_ban_id INT, -- Thêm liên kết với Phong_Ban
	level_id int,
    trang_thai BIT DEFAULT 1,
    ngay_tao DATETIME DEFAULT GETDATE(),
    ngay_cap_nhat DATETIME DEFAULT GETDATE()
	FOREIGN KEY (level_id) REFERENCES Level(level_id),
    FOREIGN KEY (phong_ban_id) REFERENCES Phong_Ban(phong_ban_id)
);
GO
CREATE TABLE Truong_phong (
  truong_phong_id INT PRIMARY KEY IDENTITY(1,1),
  ma_truong_phong varchar(100) not null,
  ten_truong_phong VARCHAR(100) NOT NULL,
  trang_thai BIT DEFAULT 1
);
-- Tạo bảng Nhan_Vien
CREATE TABLE Nhan_Vien (
    nhan_vien_id INT PRIMARY KEY IDENTITY(1,1),
	ma varchar(100),
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    sdt VARCHAR(15)  NOT NULL UNIQUE,
    vi_tri_id INT,
    vai_tro_id INT,
    qr_code VARCHAR(255),
	truong_phong_id int,
    phong_ban_id INT, -- Thêm liên kết với Phong_Ban
	--nguoi_dung_id int,
    ngay_tao DATETIME DEFAULT GETDATE(),
    ngay_cap_nhat DATETIME DEFAULT GETDATE(),
	trang_thai bit

    FOREIGN KEY (vi_tri_id) REFERENCES Vi_Tri_Cong_Viec(vi_tri_id),
    FOREIGN KEY (vai_tro_id) REFERENCES Vai_Tro(vai_tro_id),
    FOREIGN KEY (phong_ban_id) REFERENCES Phong_Ban(phong_ban_id),
	 FOREIGN KEY (truong_phong_id) REFERENCES Truong_phong(truong_phong_id),
	 --FOREIGN KEY (nguoi_dung_id) REFERENCES Nguoi_Dung(nguoi_dung_id)

);
GO
CREATE TABLE Nguoi_Dung (
    nguoi_dung_id INT PRIMARY KEY IDENTITY(1,1),
    ho_ten VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    sdt VARCHAR(15),
    vai_tro_id INT,
	nhan_vien_id int,
	mat_khau VARCHAR(255) NOT NULL,
    trang_thai BIT DEFAULT 1, -- 1: kích hoạt, 0: tạm khóa
    FOREIGN KEY (vai_tro_id) REFERENCES Vai_Tro(vai_tro_id),
	FOREIGN KEY (nhan_vien_id) REFERENCES Nhan_Vien(nhan_vien_id)
);
GO


-- Tạo bảng Dot_Tuyen_Dung
CREATE TABLE Dot_Tuyen_Dung (
    dot_tuyen_dung_id INT PRIMARY KEY IDENTITY(1,1),
    ma_dot VARCHAR(50) NOT NULL,
    ten_dot VARCHAR(100) NOT NULL,
    noi_dung TEXT,
    deadline DATE,
    nhan_vien_id INT,
    FOREIGN KEY (nhan_vien_id) REFERENCES Nhan_Vien(nhan_vien_id)
);
GO
CREATE TABLE CV_status (
cv_status_id INT PRIMARY KEY IDENTITY(1,1),
ma_cv_status varchar(100) not null,
ten_cv_status varchar(100) not null
);


-- Tạo bảng CV
CREATE TABLE CV (
    cv_id INT PRIMARY KEY IDENTITY(1,1),
    apply_datetime DATETIME NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    gioi_tinh NVARCHAR(10) NOT NULL, -- Sử dụng NVARCHAR để thay thế ENUM
    email VARCHAR(100) NOT NULL,
    sdt VARCHAR(15),
    thanh_pho VARCHAR(100),
    ten_cong_viec VARCHAR(100),
    so_nam_kinh_nghiem INT,
    ghi_chu TEXT,
    link_cv VARCHAR(255),
    nguon_tuyen_dung NVARCHAR(20), -- Sử dụng NVARCHAR để thay thế ENUM
   -- trang_thai NVARCHAR(50) NOT NULL, -- Sử dụng NVARCHAR để thay thế ENUM
   cv_status_id int,
    nhan_su_quan_ly_id INT,
    FOREIGN KEY (nhan_su_quan_ly_id) REFERENCES Nguoi_Dung(nguoi_dung_id),
	 FOREIGN KEY (cv_status_id) REFERENCES CV_status(cv_status_id)
);
GO

-- Tạo bảng Hoat_Dong

CREATE TABLE Loai_Hoat_Dong (
    loai_hoat_dong_id INT PRIMARY KEY IDENTITY(1,1),
    ten_loai NVARCHAR(50) NOT NULL
);
-- Tạo bảng Hoat_Dong
CREATE TABLE Hoat_Dong (
    hoat_dong_id INT PRIMARY KEY IDENTITY(1,1),
    ghi_chu TEXT, -- Trường ghi chú
    chia_se_voi NVARCHAR(50), -- Trường chia sẻ
    ngay_tao DATETIME DEFAULT GETDATE(), -- Trường ngày tạo
    loai_hoat_dong_id INT, -- Khóa ngoại tới Loai_Hoat_Dong
    nguoi_dung_id INT, -- Khóa ngoại tới Nguoi_Dung
    FOREIGN KEY (loai_hoat_dong_id) REFERENCES Loai_Hoat_Dong(loai_hoat_dong_id),
    FOREIGN KEY (nguoi_dung_id) REFERENCES Nguoi_Dung(nguoi_dung_id)
);
GO



INSERT INTO Phong_Ban (ma_phong_ban, ten_phong_ban) 
VALUES 
('PB001', 'Phong IT'),
('PB002', 'Phong Marketing');

INSERT INTO Level (ma_level, ten_level) 
VALUES 
('L1', 'Junior'), 
('L2', 'Mid'), 
('L3', 'Senior');

INSERT INTO Vi_Tri_Cong_Viec (ma_vi_tri, ten_vi_tri, phong_ban_id, level_id) 
VALUES 
('VT001', 'Developer', 1, 1), 
('VT002', 'Designer', 2, 2);


INSERT INTO Truong_phong (ma_truong_phong, ten_truong_phong)
VALUES 
('TP001', 'Nguyen Van D'),
('TP002', 'Le Thi E');

INSERT INTO Nhan_Vien (ho_ten, email, sdt, vi_tri_id, vai_tro_id, truong_phong_id, phong_ban_id) 
VALUES 
('Pham Van F', 'phamvanf@gmail.com', '0971234567', 1, 3, 1, 1),
('Hoang Thi G', 'hoangthig@gmail.com', '0931234567', 2, 3, 2, 2);

INSERT INTO Nguoi_Dung (ho_ten, email, sdt, vai_tro_id,nhan_vien_id,mat_khau, trang_thai) 
VALUES 
('Nguyen Van A', 'nguyenvana@gmail.com', '0123456789', 1, 1, N'$2b$12$WcEb8dTu/rHNS/ZVicn/huOBdhcDcEhiH1eYGg6vo9XThxfreFMq2',1),--a123
('Le Thi B', 'lethib@gmail.com', '0987654321', 2,2,N'$2b$12$3jt..XLOVoI3jhJx3saXwew/mabKt59W/GhAKn/gwfQaVn5gmRHLS', 1),--b456
('Tran Van C', 'tranvanc@gmail.com', '0912345678', 3,1, N'$2b$12$dpL39i23/vbQ4IjPKCgy9eOFZhx833fIxp01D9fVa2PcyV5QS90RG', 1);--c789

INSERT INTO Dot_Tuyen_Dung (ma_dot, ten_dot, noi_dung, deadline, nhan_vien_id) 
VALUES 
('TD001', 'Dot tuyen thang 9', 'Tuyen nhan su IT', '2023-09-30', 1),
('TD002', 'Dot tuyen thang 10', 'Tuyen nhan su Marketing', '2023-10-30', 2);

INSERT INTO CV_status (ma_cv_status, ten_cv_status)
VALUES 
('CV001', 'Chưa xử lý'), 
('CV002', 'Đang xử lý'), 
('CV003', 'Đã duyệt');

INSERT INTO CV (apply_datetime, ho_ten, gioi_tinh, email, sdt, thanh_pho, ten_cong_viec, so_nam_kinh_nghiem, ghi_chu, link_cv, nguon_tuyen_dung, cv_status_id, nhan_su_quan_ly_id) 
VALUES 
('2023-09-01 08:00:00', 'Nguyen Van H', 'Nam', 'nguyenvanh@gmail.com', '0901234567', 'Hanoi', 'Developer', 2, 'Co kinh nghiem lap trinh web', 'http://example.com/cv1', 'LinkedIn', 1, 1);

INSERT INTO Loai_Hoat_Dong (ten_loai) 
VALUES 
('Tao CV'), 
('Phong van');


INSERT INTO Hoat_Dong (ghi_chu, chia_se_voi, loai_hoat_dong_id, nguoi_dung_id) VALUES 
(N'Gọi điện cho ứng viên A', 'Nguyễn Văn A', 1, 1),  -- Gọi điện
(N'Họp về quy trình tuyển dụng', 'Trần Thị B', 2, 1),  -- Meeting
(N'Chat Zalo với ứng viên B', 'Lê Văn C', 1, 2),  -- Chat Zalo
(N'Thiết lập cuộc hẹn với ứng viên D', 'Nguyễn Văn A', 1, 2),  -- Gọi điện
(N'Họp đánh giá ứng viên E', 'Trần Thị B', 2, 2),  -- Meeting
(N'Tra đổi thông tin với ứng viên F', 'Lê Văn C', 1, 3),  -- Chat Zalo
(N'Gọi điện cho ứng viên G', 'Nguyễn Văn A', 1, 1),  -- Gọi điện
(N'Họp bàn về yêu cầu công việc', 'Trần Thị B', 2, 1),  -- Meeting
(N'Phỏng vấn ứng viên H', 'Lê Văn C', 2, 2),  -- Chat Zalo
(N'Gọi điện cho ứng viên I', 'Nguyễn Văn A', 1, 2),  -- Gọi điện
(N'Tham gia cuộc họp với đội ngũ', 'Trần Thị B', 2, 2),  -- Meeting
(N'Trò chuyện với ứng viên J', 'Lê Văn C', 1, 3);  -- Chat Zalo

--ALTER TABLE Nguoi_Dung
--ADD password  VARCHAR(100) ;



