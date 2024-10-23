//const toggle = document.querySelector("#toggle-btn");
const showModal = document.querySelector("#btnNV");//button show modal 2
const modal1 = new bootstrap.Modal(document.getElementById('exampleModal'));
const modal2 = new bootstrap.Modal(document.getElementById('modalNV'));

const modal3 = new bootstrap.Modal(document.getElementById('modal-show-3'));
const modal4 = new bootstrap.Modal(document.getElementById('modal-show-4'));
//const showModal1 = document.querySelector("#show-modal-1");


// toggle.addEventListener("click",function(){
//     document.querySelector("#sidebar").classList.toggle("expand");
// });

showModal.addEventListener("click",function (){
    modal2.show();
    modal1.hide();
});

showModal1.addEventListener("click",function (){

    $('#hoTen').val('') ;
    $('#emailID').val('') ;
    $('#sdtID').val('') ;
    $('#vaiTroID').val('');
    $('#idNhanvienHidden').val('');
    $('#nhanvienID').val('');
    $('#flexRadioDefault1').prop('checked', true);

    $('#btnUpdate').hide();
    $('#btnSave').show();
    $('#passwordField').show();

    modal1.show();
});

document.getElementById('modalNV').addEventListener('hidden.bs.modal', function () {
    modal1.show();
});
function getNhanVienId(button) {
    // Lấy giá trị của thuộc tính data-id
    var nhanVienId = button.getAttribute("data-id");

    if (nhanVienId) {
        var apiUrl = "/admin/role/" + nhanVienId;

        $.ajax({
            url: apiUrl,
            type: "GET",
            contentType: "application/json",
            success: function(data) {
                if(data.id && data.hoTen){
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

function  edit(id){
    const urlEdit ="/admin/role/edit/"+id;
    console.log('DataID:', id);
    $.ajax({
        url: urlEdit,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('Data:', data);
            $('#hoTen').val(data.hoTen) ;
            $('#emailID').val(data.email) ;
            $('#sdtID').val(data.sdt) ;
            $('#vaiTroID').val(data.vaiTro.id);


            if(data.nhanVien && data.nhanVien.id != null){
                $('#idNhanvienHidden').val(data.nhanVien.id);
                $('#nhanvienID').val(data.nhanVien.hoTen);
            }else {
                $('#idNhanvienHidden').val('');
                $('#nhanvienID').val('');
            }

            if(data.trangThai == true){
                $('#flexRadioDefault1').prop('checked', true);
            }else {
                $('#flexRadioDefault2').prop('checked', true);
            }
            $('#btnUpdate').attr('formaction', '/admin/role/update/' + data.id);

            $('#btnUpdate').show();
            $('#btnSave').hide();
            $('#passwordField').hide();
        },
        error: function(xhr, status, error) {
            console.error('Error:', status, error);
        }
    });
    modal1.show();
}


function editRoles(button){
    var key = button.getAttribute("data-key");
    const urlEdit ="/admin/role/edit/quyen-truy-cap/"+key;
    console.log('DataID:', key);
    $.ajax({
        url: urlEdit,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('Data:', data);
            if (Array.isArray(data) && data.length > 0) {
                const firstItem = data[0];
                if (firstItem.vaiTro) {
                    console.log('Vai trò ID:', firstItem.vaiTro.id);
                    $('#vaiTroIDSS').val(firstItem.vaiTro.id);
                } else {
                    console.error('Vai trò không tồn tại trong phần tử đầu tiên.');
                }
            } else {
                console.error('Dữ liệu không hợp lệ hoặc rỗng.');
            }

            if (Array.isArray(data)) {
                data.forEach(item => {
                    if (item.quyenTruyCap) {
                        const quyenTruyCapId = item.quyenTruyCap.id; // lấy id của quyền truy cập
                        // Đánh dấu checkbox nếu id tương ứng với quyền truy cập
                        $(`#quyenTruyCapContainer input[type="checkbox"][value="${quyenTruyCapId}"]`).prop('checked', true);
                    }
                });
            }else {
                console.error('Dữ liệu không hợp lệ hoặc rỗng.');
            }



            modal4.show();
        },
        error: function(xhr, status, error) {
            console.error('Error:', status, error);
        }
    });
    //modal1.show();
}

function updateRole() {
    var roleId = $('#vaiTroIDS').val();
    var selectedPermissions = [];

    $('#quyenTruyCapContainer input[type="checkbox"]:checked').each(function() {
        selectedPermissions.push($(this).val());
    });

    $.ajax({
        url: `/admin/role/update/${roleId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(selectedPermissions),
        success: function(response) {
            console.log(response);
            alert("Cập nhật thành công.");
            // Có thể đóng modal hoặc làm gì đó khác
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert("Cập nhật không thành công.");
        }
    });
}


function  add(){
    const urlAdd ="/admin/role/add";
    //console.log('DataID:', id);
    $.ajax({
        url: "/admin/role/add",
        type: 'POST',
        dataType: 'json',
        success: function(data) {
            console.log("data return add: ",data);
            //window.location.href = "/admin/role";
        },
        error: function(xhr, status, error) {
            console.error('Error:', status, error);
        }
    });
}

document.getElementById("btnSave").addEventListener("click",function (event){
    var hoTen = document.getElementById("hoTen").value.trim();
    var email = document.getElementById("emailID").value.trim();
    var password = document.getElementById("passwordID").value.trim();
    var sdt = document.getElementById("sdtID").value.trim();
    var vaiTro = document.getElementById("vaiTroID").value.trim();
    var isValid = true;
        clearErrors();
    //check họ tên
    if(hoTen === null ){
        displayError("hoTenError", "Họ và Tên không được để trống.");
        isValid = false;
    }else if(hoTen.length < 2 || hoTen.length > 70){
        displayError("hoTenError", "Họ và Tên phải từ 2 đến 70 ký tự.");
        isValid = false;
    }
    // Kiểm tra trường Email
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (email === "") {
        displayError("emailError", "Email không được để trống.");
        isValid = false;
    } else if (!emailPattern.test(email)) {
        displayError("emailError", "Email không hợp lệ.");
        isValid = false;
    }

    // Kiểm tra trường Password
    if (password === "") {
        displayError("passwordError", "Password không được để trống.");
        isValid = false;
    } else if (password.length < 6) {
        displayError("passwordError", "Password phải có ít nhất 6 ký tự.");
        isValid = false;
    }

    // Kiểm tra trường Số điện thoại
    var phonePattern = /^(0[0-9]{9})$/;
    if (sdt === "") {
        displayError("sdtError", "Số điện thoại không được để trống.");
        isValid = false;
    } else if (!phonePattern.test(sdt)) {
        displayError("sdtError", "Số điện thoại không hợp lệ.");
        isValid = false;
    }

    // Kiểm tra trường Vai trò
    if (vaiTro === "") {
        displayError("vaiTroError", "Vui lòng chọn vai trò.");
        isValid = false;
    }

    // Nếu có lỗi, ngăn form submit
    if (!isValid) {
        event.preventDefault();
    }
})


document.getElementById("btnVaiTroQTC").addEventListener("click",function (){
    modal3.show();
})

function displayError(elementId, errorMessage) {
    var errorElement = document.getElementById(elementId);
    errorElement.innerHTML = errorMessage;
    errorElement.style.display = "block";
    errorElement.previousElementSibling.classList.add("is-invalid");  // Đánh dấu input là invalid
}

function clearErrors() {
    var errorElements = document.querySelectorAll(".invalid-feedback");
    errorElements.forEach(function(element) {
        element.innerHTML = "";
        element.style.display = "none";
        element.previousElementSibling.classList.remove("is-invalid");
    });
}

function hideAlert() {
    const successAlert = document.getElementById("success-alert");
    const errorAlert = document.getElementById("error-alert");
    if (successAlert) {
        setTimeout(() => {
            successAlert.style.display = "none";
        }, 4000);
    }
    if (errorAlert) {
        setTimeout(() => {
            errorAlert.style.display = "none";
        }, 50000);
    }
}

window.onload = hideAlert;
