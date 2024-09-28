const toggle = document.querySelector("#toggle-btn");
toggle.addEventListener("click",function(){
    document.querySelector("#sidebar").classList.toggle("expand");
});

const showModal = document.querySelector("#btnNV");
const modal1 = new bootstrap.Modal(document.getElementById('exampleModal'));
const modal2 = new bootstrap.Modal(document.getElementById('modalNV'));
showModal.addEventListener("click",function (){
    modal2.show();
    modal1.hide();
});

document.getElementById('modalNV').addEventListener('hidden.bs.modal', function () {
    modal1.show(); // Hiển thị lại modal 1
});

function getNhanVienId(button) {
    // Lấy giá trị của thuộc tính data-id
    var nhanVienId = button.getAttribute("data-id");

    // Kiểm tra xem giá trị ID có tồn tại không
    if (nhanVienId) {
        console.log("ID của nhân viên là: " + nhanVienId);

        var apiUrl = "/admin/role/" + nhanVienId;
        console.log("Gọi API đến URL: " + apiUrl);

        // Gọi API bằng jQuery
        $.ajax({
            url: apiUrl,
            type: "GET",
            contentType: "application/json",
            success: function(data) {
                console.log("Dữ liệu nhận được từ API:", data);
                if(data.id && data.hoTen){
                    console.log("Dữ liệu nhận được từ API:", data.id);
                    console.log("Dữ liệu nhận được từ API:", data.hoTen);

                    $('#idNhanvienHidden').val(data.id);
                    $('#nhanvienID').val(data.hoTen);
                    modal2.hide();
                }
            },
            error: function(xhr, status, error) {
                console.error("Lỗi khi gọi API:", status, error);
            }
        });
    } else {
        console.log("Không lấy được ID của nhân viên");
    }
}

function hideAlert() {
    const successAlert = document.getElementById("success-alert");
    const errorAlert = document.getElementById("error-alert");
    if (successAlert) {
        setTimeout(() => {
            successAlert.style.display = "none";
        }, 3000);
    }
    if (errorAlert) {
        setTimeout(() => {
            errorAlert.style.display = "none";
        }, 5000);
    }
}

window.onload = hideAlert;
