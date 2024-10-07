const toggle = document.querySelector("#toggle-btn");
toggle.addEventListener("click",function(){
    document.querySelector("#sidebar").classList.toggle("expand");
});


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
$(document).ready(function() {
    $('#editModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Nút chỉnh sửa
        var id = button.data('id');
        var maPhongBan = button.data('ma-phong-ban');
        var tenPhongBan = button.data('ten-phong-ban');
        var trangThai = button.data('trang-thai'); // Giá trị là 1 hoặc 0
        var ngayTao = button.data('ngay-tao');
        var ngayCapNhat = button.data('ngay-cap-nhat');

        var modal = $(this);
        modal.find('#editId').val(id);
        modal.find('#editMaPhongBan').val(maPhongBan);
        modal.find('#editTenPhongBan').val(tenPhongBan);
        // modal.find('#editTrangThai').val(trangThai); // Đặt giá trị là số 1 hoặc 0
        modal.find('#editTrangThai').val(trangThai);
    });
});


$(document).ready(function() {
    // Lắng nghe sự kiện khi modal được mở
    $('#detailModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget); // Nhận nút đã nhấn
        var id = button.data('id');
        var maPhongBan = button.data('ma-phong-ban');
        var tenPhongBan = button.data('ten-phong-ban');
        var trangThai = button.data('trang-thai') ? 'Đang Hoạt Động' : 'Ngừng Hoạt Động';
        var ngayTao = button.data('ngay-tao');
        var ngayCapNhat = button.data('ngay-cap-nhat');

        // Cập nhật các giá trị vào modal
        var modal = $(this);
        modal.find('#detailId').val(id);
        modal.find('#detailMaPhongBan').val(maPhongBan);
        modal.find('#detailTenPhongBan').val(tenPhongBan);
        modal.find('#detailTrangThai').val(trangThai);
        modal.find('#detailNgayTao').val(new Date(ngayTao).toLocaleString());
        modal.find('#detailNgayCapNhat').val(new Date(ngayCapNhat).toLocaleString());
    });
});

