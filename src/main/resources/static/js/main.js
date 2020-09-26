function checkData(selector, regex, textError) {
    let val = $(selector).val().trim();
    let check = false;
    if (val.length > 0 && regex.test(val)) {
        check = true;
        hiddenError(selector);
    } else {
        viewError(selector, textError);
    }

    return {val, check};
}

function dataFilter(list) {
    for (const p of list) {
        for (const pp in p) {
            if (typeof pp !== "boolean")
                p[pp] = p[pp] ? p[pp] : "";
        }
    }
    return list;
}

function viewError(selector, text) {
    $(selector).addClass("is-invalid");
    $(selector).siblings(".invalid-feedback").html(text + ". Mời nhập lại!");
}

function hiddenError(selector) {
    $(selector).removeClass("is-invalid");
}

const URL_API = "/api/v1";

async function ajaxGet(url) {
    let rs = null;
    await $.ajax({
        type: 'GET',
        dataType: "json",
        url: URL_API + url,
        timeout: 30000,
        cache: false,
        success: function (result) {
            rs = result;
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
        success: function (result) {
            rs = result
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
        success: function (result) {
            rs = result
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