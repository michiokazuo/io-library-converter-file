let emailLogin, passwordLogin, rememberMe, btnLogin, linkForgotPassword, linkSignUp,
    emailForgotPassword, btnSubmitForgotPassword, nameSignUp, emailSignUp, passwordSignUp, passwordConfirmSignUp,
    avatarSignUp, btnSubmitSignUp;

$(function (){
    emailLogin = $("#email-login");
    passwordLogin = $("#password-login");
    rememberMe = $("#remember-me");
    btnLogin = $("#btn-submit-login");
    linkForgotPassword = $("#link-forgot-password");
    linkSignUp = $("#link-sign-up");
    emailForgotPassword = $("#email-forgot-password");
    btnSubmitForgotPassword = $("#btn-submit-forgot-password");
    nameSignUp = $("#name-sign-up");
    emailSignUp = $("#email-sign-up");
    passwordSignUp = $("#password-sign-up");
    passwordConfirmSignUp = $("#password-confirm-sign-up");
    avatarSignUp = $("#avatar");
    btnSubmitSignUp = $("#btn-submit-sign-up");

    login();
})


function login(){
    btnLogin.click(async function (){
        let {val : valueEmailLogin, check : checkEmailLogin} = checkData(emailLogin, "Invalid email");
        let {val : valuePasswordLogin, check : checkPasswordLogin} = checkData(passwordLogin, "Invalid password");
        let valueRememberMe = $("#remember-me").is(":checked");
        if (checkEmailLogin && checkPasswordLogin){
            let formData = new FormData();
            formData.append("username", valueEmailLogin);
            formData.append("password", valuePasswordLogin);
            formData.append("remember-me",valueRememberMe);
            await ajaxUploadFormData("/security-login", formData)
                .then( (data) => {
                    console.log(data.status);
                    if (data.status == 200){
                        alertReport(true, "Login Successful");
                        window.location.href = "/home";
                    }else {
                        alertReport(false, "Your information is incorrect");
                    }
                }).catch(e => {
                    alertReport(false, "Your information is incorrect");
                });
        }
    })
}


function reloadImage(){
    let file = document.getElementById("avatar").files[0];
    let img = document.getElementById("avatar-photo");
    let reader = new FileReader();
    reader.addEventListener("load", function(){
        img.src = reader.result;
    },false)
    if(file){
        reader.readAsDataURL(file);
    }
}