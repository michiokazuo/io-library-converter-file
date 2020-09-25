const URL_USER = "/user";
const URL_USER_PUBLIC = "/public" + URL_USER;
const URL_USER_PRIVATE = "/private" + URL_USER;
const URL_UPLOAD_FILE = "/public/file";

function getAllUser(){
    return ajaxGet(`${URL_USER_PRIVATE}/find-all`);
}

function getUserById(id){
    return ajaxGet(`${URL_USER_PRIVATE}/find-by-id/${id}`);
}

function getUserByEmail(email){
    return ajaxGet(`${URL_USER_PRIVATE}/find-by-email/${email}`);
}

function getUser(){
    return ajaxGet(`${URL_USER_PUBLIC}/my-info`);
}

function insertUser(user){
    return ajaxPost(`${URL_USER_PUBLIC}/insert`,user);
}

function updateUser(user){
    return ajaxPut(`${URL_USER_PUBLIC}/update`,user);
}

function deleteUser(id){
    return ajaxDelete(`${URL_USER_PRIVATE}/delete/${id}`)
}

function uploadAvatar(avatar){
    return ajaxUploadFile(`${URL_UPLOAD_FILE}/upload-avatar`,avatar);
}

