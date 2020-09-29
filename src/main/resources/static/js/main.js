const URL_API = "/api/v1";

async function ajaxGet(url) {
    let rs = null;
    await $.ajax({
        type: 'GET',
        dataType: "json",
        url: URL_API + url,
        timeout: 30000,
        cache: false,
        success: function (result, textStatus, xhr) {
            rs = {
                data : result,
                status : xhr.status
            }
        }
    });
    return rs;
}

async function ajaxPost(url, data) {
    let rs = null;
    await $.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        url: URL_API + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result, textStatus, xhr) {
            rs = {
                data : result,
                status : xhr.status
            }
        }
    });
    return rs;
}

async function ajaxPut(url, data) {
    let rs = null;
    await $.ajax({
        type: 'PUT',
        data: JSON.stringify(data),
        // headers: {
        //     "Authorization": ss_lg
        // },
        url: URL_API + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result
        }
    })
    return rs;
}

async function ajaxDelete(url, data) {
    let rs = null;
    await $.ajax({
        type: 'DELETE',
        data: JSON.stringify(data),
        // headers: {
        //     "Authorization": ss_lg
        // },
        url: URL_API + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result, textStatus, xhr) {
            rs = {
                data : result,
                status : xhr.status
            }
        }
    })
    return rs;
}

async function ajaxUploadFile(url, file) {
    let formData = new FormData();
    formData.append("file", file);
    let rs = null;
    await $.ajax({
        type: "POST",
        url: url,
        data: formData,
        cache: false,
        contentType: false,
        enctype: "multipart/form-data",
        processData: false,
        success: function (result) {
            rs = result;
        }
    });
    return rs;
}

async function ajaxUploadFormData(url, formData) {
    let rs = null;
    await $.ajax({
        type: "POST",
        url: url,
        data: formData,
        cache: false,
        contentType: false,
        enctype: "multipart/form-data",
        processData: false,
        success: function (result, textStatus, xhr) {
            rs = {
                data : result,
                status : xhr.status
            }
        }
    });
    return rs;
}

function viewError(selector, text) {
    $(selector).addClass("is-invalid");
    $(selector).siblings(".invalid-feedback").html(text+". Enter again!");
}

function hiddenError(selector) {
    $(selector).removeClass("is-invalid");
}

function checkData(selector, textError){
    let val = $(selector).val();
    let check = false;
    if (val.length > 0){
        check = true;
        hiddenError(selector);
    }else {
        viewError(selector, textError);
    }
    // trả về một đối tượng có 2 thuộc tính val và check
    // val sẽ mang giá trị của biến val
    // check sẽ mang giá trị của biến check
    return {val, check};
}

function checkEmail(selector, textError){
    let val = $(selector).val();
    let check = false;
    const regex_email = /^[a-z][a-z0-9_\.]{5,32}@[a-z0-9]{2,}(\.[a-z0-9]{2,4}){1,2}$/;
    if (val.length > 0 && regex_email.test(val)){
        check = true;
        hiddenError(selector);
    }else {
        viewError(selector, textError);
    }
    return {val, check};
}

function checkPassword(selector, textError){
    let val = $(selector).val();
    let check = false;
    const regex_password = /^(?=.*\d)(?=.*[a-zA-Z]).{8,}$/;
    if (val.length > 0 && regex_password.test(val)){
        check = true;
        hiddenError(selector);
    }else {
        viewError(selector, textError);
    }
    return {val, check};
}

function checkPasswordConfirm(selector, password,textError){
    let val = $(selector).val();
    let check = false;
    if (val.length > 0 && val === password){
        check = true;
        hiddenError(selector);
    }else {
        viewError(selector, textError);
    }
    return {val, check};
}

function alertReport(isSuccess,text){
    let result = `<div class="alert alert-${isSuccess ? "success" : "danger"} animate-report">
                    <strong>!</strong> ${text}
                  </div>`;
    $("#alert-report").prepend(result);
    let firstElement = $("#alert-report").children().first();
    setTimeout(function (){
        firstElement.remove();
    },3000);
}