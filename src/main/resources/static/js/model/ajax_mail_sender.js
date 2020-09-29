const URL_MAIL_API = "/api/v1/public/mail";

function mailForgotPassword(email){
    let formData = new FormData();
    formData.append("email", email);
    return ajaxUploadFormData(`${URL_MAIL_API}/forgot-password`,formData);
}