let deleteForm;

// Confirm delete modal visibility
function confirmDelete() {
    $('#deleteModal').modal('show');
    return false; // Prevent immediate form submission
}

// Handle delete button clicks
document.querySelectorAll('.btn-delete').forEach(button => {
    button.addEventListener('click', function () {
        // Store the form to submit later
        deleteForm = this.closest('form');
        confirmDelete();
    });
});

// Confirm deletion and submit the stored form
document.getElementById('confirmDelete').addEventListener('click', function () {
    if (deleteForm) {
        deleteForm.submit(); // Sends a POST request to delete
    }
});

// Close modal without action
function closeModal() {
    $('#deleteModal').modal('hide');
}

// Add event listeners for modal close buttons
document.querySelectorAll('.close, .btn-secondary').forEach(btn => {
    btn.addEventListener('click', closeModal);
});

// Khi modal được mở, cập nhật dữ liệu phòng ban vào form
$(document).ready(function () {
    // Lắng nghe sự kiện khi modal chỉnh sửa được mở
    $('#editModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Nút đã nhấn để mở modal
        var id = button.data('id'); // Lấy id của phòng ban

        // Gửi yêu cầu Ajax tới server để lấy dữ liệu phòng ban chi tiết
        $.ajax({
            url: '/getPhongBanById/' + id, // Gọi API lấy thông tin phòng ban theo id
            method: 'GET',
            success: function (data) {
                var modal = $('#editModal');

                // Điền thông tin vào các trường trong modal
                modal.find('#editId').val(data.id);
                modal.find('#editMaPhongBan').val(data.maPhongBan);
                modal.find('#editTenPhongBan').val(data.tenPhongBan);
                modal.find('#editTrangThai').val(data.trangThai ? '1' : '0'); // Set giá trị cho trạng thái (1 hoặc 0)
            },
            error: function () {
                alert('Không thể lấy thông tin phòng ban.');
            }
        });
    });
});

$(document).ready(function () {
    // Lắng nghe sự kiện khi modal được mở
    $('#detailModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Nhận nút đã nhấn
        var id = button.data('id'); // Lấy id từ thuộc tính data-id

        // Gửi yêu cầu Ajax tới server để lấy dữ liệu phòng ban chi tiết
        $.ajax({
            url: '/getPhongBanById/' + id,
            method: 'GET',
            success: function (data) {
                // Cập nhật các giá trị vào modal
                var modal = $('#detailModal');
                modal.find('#detailId').val(data.id);
                modal.find('#detailMaPhongBan').val(data.maPhongBan);
                modal.find('#detailTenPhongBan').val(data.tenPhongBan);
                modal.find('#detailTrangThai').val(data.trangThai ? 'Đang Hoạt Động' : 'Ngừng Hoạt Động');
                modal.find('#detailNgayTao').val(new Date(data.ngayTao).toLocaleString());
                modal.find('#detailNgayCapNhat').val(new Date(data.ngayCapNhat).toLocaleString());
            },
            error: function () {
                alert('Không thể lấy thông tin chi tiết phòng ban.');
            }
        });
    });
});

function validateFormAdd() {
    return new Promise(async (resolve) => {
        const maPhongBan = document.getElementById("maPhongBan").value.trim();
        const tenPhongBan = document.getElementById("tenPhongBan").value.trim();

        let valid = true; // Biến kiểm tra tính hợp lệ

        // Kiểm tra mã phòng ban
        const maPhongBanError = document.getElementById("maPhongBanError");
        maPhongBanError.style.display = "none"; // Ẩn thông báo lỗi trước khi kiểm tra
        if (!maPhongBan) {
            maPhongBanError.textContent = "Mã phòng ban không được để trống!";
            maPhongBanError.style.display = "block"; // Hiện thông báo lỗi
            valid = false; // Đặt valid là false
        } else if (maPhongBan.length > 50) {
            maPhongBanError.textContent = "Mã phòng ban không được vượt quá 50 ký tự!";
            maPhongBanError.style.display = "block"; // Hiện thông báo lỗi
            valid = false; // Đặt valid là false
        }

        // Kiểm tra mã phòng ban đã tồn tại
        const response = await fetch(`/checkMaPhongBan?maPhongBan=${encodeURIComponent(maPhongBan)}`);
        const exists = await response.json();
        if (exists) {
            maPhongBanError.textContent = "Mã phòng ban đã tồn tại!";
            maPhongBanError.style.display = "block"; // Hiện thông báo lỗi
            valid = false; // Đặt valid là false
        }

        // Kiểm tra tên phòng ban
        const tenPhongBanError = document.getElementById("tenPhongBanError");
        tenPhongBanError.style.display = "none"; // Ẩn thông báo lỗi trước khi kiểm tra
        if (!tenPhongBan) {
            tenPhongBanError.textContent = "Tên phòng ban không được để trống!";
            tenPhongBanError.style.display = "block"; // Hiện thông báo lỗi
            valid = false; // Đặt valid là false
        } else if (tenPhongBan.length > 100) {
            tenPhongBanError.textContent = "Tên phòng ban không được vượt quá 100 ký tự!";
            tenPhongBanError.style.display = "block"; // Hiện thông báo lỗi
            valid = false; // Đặt valid là false
        }

        resolve(valid); // Trả về kết quả
    });
}

function validateFormUpdate() {
    const maPhongBan = document.getElementById("editMaPhongBan").value.trim();
    const tenPhongBan = document.getElementById("editTenPhongBan").value.trim();

    let valid = true; // Biến kiểm tra tính hợp lệ

    // Kiểm tra mã phòng ban
    const editmaPhongBanError = document.getElementById("editmaPhongBanError");
    editmaPhongBanError.style.display = "none"; // Ẩn thông báo lỗi trước khi kiểm tra
    if (!maPhongBan) {
        editmaPhongBanError.textContent = "Mã phòng ban không được để trống!";
        editmaPhongBanError.style.display = "block"; // Hiện thông báo lỗi
        valid = false; // Đặt valid là false
    } else if (maPhongBan.length > 50) {
        editmaPhongBanError.textContent = "Mã phòng ban không được vượt quá 50 ký tự!";
        editmaPhongBanError.style.display = "block"; // Hiện thông báo lỗi
        valid = false; // Đặt valid là false
    }

    // Kiểm tra tên phòng ban
    const edittenPhongBanError = document.getElementById("edittenPhongBanError");
    edittenPhongBanError.style.display = "none"; // Ẩn thông báo lỗi trước khi kiểm tra
    if (!tenPhongBan) {
        edittenPhongBanError.textContent = "Tên phòng ban không được để trống!";
        edittenPhongBanError.style.display = "block"; // Hiện thông báo lỗi
        valid = false; // Đặt valid là false
    } else if (tenPhongBan.length > 100) {
        edittenPhongBanError.textContent = "Tên phòng ban không được vượt quá 100 ký tự!";
        edittenPhongBanError.style.display = "block"; // Hiện thông báo lỗi
        valid = false; // Đặt valid là false
    }

    return valid; // Trả về kết quả
}
