//const modal1 = new bootstrap.Modal(document.getElementById('tuyendung'));
const showModal1 = document.querySelector("#show-modal1");


showModal1.addEventListener("click",function (){

    $('#maDot').val('') ;
    $('#tenDot').val('') ;
    $('#noiDung').val('') ;
    $('#deadline').val('');

    $('#tuyendung').modal('show');


});

// function clearErrors() {
//     // Clear the content of the elements displaying the error messages
//     document.getElementById("maDotError").textContent = "";
//     document.getElementById("tenDotError").textContent = "";
//     document.getElementById("noiDungError").textContent = "";
//     document.getElementById("deadlineError").textContent = "";
// }
function displayError(elementId, errorMessage) {
    //document.getElementById(elementId).textContent = errorMessage;
    var errorElement = document.getElementById(elementId);
    errorElement.innerHTML = errorMessage;
    errorElement.style.display = "block";
    errorElement.previousElementSibling.classList.add("is-invalid");
}

document.getElementById("btnSave1").addEventListener("click",function (event){
    var maDot = document.getElementById("maDot").value.trim();
    var tenDot = document.getElementById("tenDot").value.trim();
    var noiDung = document.getElementById("noiDung").value.trim();
    var deadline = document.getElementById("deadline").value.trim();
    var isValid =true;

    //clearErrors();
    // console.log(maDot);
    // console.log(tenDot);
    // console.log(noiDung);
    // console.log(deadline);
    //check họ tên
    if(maDot === null ){
        displayError("maDotError", "Mã Đợt không được để trống.");
        isValid = false;
    }else if(maDot.length < 2 || maDot.length > 70){
        displayError("maDotError", "Mã Đợt phải từ 2 đến 70 ký tự.");
        isValid = false;
    }
    // // Kiểm tra trường ten đợt
    //
    if(tenDot === "" ){
        displayError("tenDotError", "Tên Đợt không được để trống.");
        isValid = false;
    }else if(tenDot.length < 2 || tenDot.length > 70){
        displayError("tenDotError", "Tên Đợt phải từ 2 đến 70 ký tự.");
        isValid = false;
    }

    if(noiDung === "" ){
        displayError("noiDungError", "Nội Dung không được để trống.");
        isValid = false;
    }else if(noiDung.length < 2 || noiDung.length > 500){
        displayError("noiDungError", "Nội Dung phải từ 2 đến 70 ký tự.");
        isValid = false;
    }
    if(!isValid){
        console.log("TEST"+maDot);
        event.preventDefault();
    }

})

function  edit(id){
    const urlEdit ="/tuyen-dung/edit/"+id;
    console.log('DataID:', id);
    $.ajax({
        url: urlEdit,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('Data:', data);
            $('#maDot').val(data.maDot) ;
            $('#tenDot').val(data.tenDot) ;
            $('#noiDung').val(data.noiDung) ;
            $('#deadline').val(data.deadline);


            $('#btnUpdate1').attr('formaction', '/tuyen-dung/update/' + data.id);

            $('#btnUpdate1').show();
            $('#btnSave').hide();

        },
        error: function(xhr, status, error) {
            console.error('Error:', status, error);
        }
    });
    $('#tuyendung').modal('show');
}