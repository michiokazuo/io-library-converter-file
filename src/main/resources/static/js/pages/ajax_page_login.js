let emailLogin, passwordLogin, rememberMe, btnLogin, linkForgotPassword, linkSignUp,
    emailForgotPassword, btnSubmitForgotPassword, nameSignUp, emailSignUp, passwordSignUp, passwordConfirmSignUp,
    avatarSignUp, btnSubmitSignUp, modalForgotPassword, modalSignUp;

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
    modalForgotPassword = $("#modal-forgot-password");
    modalSignUp = $("#modal-sign-up");

    login();
    signUp();
    submitSignUp();
    forgotPassword();
    submitForgotPassword();
})

function forgotPassword(){
    linkForgotPassword.click(function (){
        modalForgotPassword.modal("show");
    })
}

function submitForgotPassword(){
    btnSubmitForgotPassword.click(async function (){
        let {val : valueEmailForgotPassword, check : checkEmailForgotPassword} = checkEmail(emailForgotPassword, "Invalid email");
        if (checkEmailForgotPassword){
            await mailForgotPassword(valueEmailForgotPassword)
                .then(rs => {
                    if (rs.status === 200){
                        alertReport(true, "Your password was reset. Get in your email to get new password");
                        modalForgotPassword.modal("hide");
                    }else{
                        alertReport(false, "Send to your email fail");
                    }
                })
                .catch(e => {
                    alertReport(false, "Send to your email fail");
                })
        }
    })
}

function signUp(){
    linkSignUp.click(async function (){
        modalSignUp.modal("show");
    })
}

function submitSignUp(){
    btnSubmitSignUp.click(async function (){
        let {val : valueName, check : checkName} = checkData(nameSignUp, "Not empty");
        let {val : valueEmailSignUp, check : checkEmailSignUp} = checkEmail(emailSignUp, "Invalid email");
        let {val : valuePasswordSignUp, check : checkPasswordSignUp} = checkPassword(passwordSignUp, "Password contains at least 8 characters including both numbers and letters");
        let {val : valuePasswordConfirmSignUp, check : checkPasswordConfirmSignUp} = checkPasswordConfirm(passwordConfirmSignUp, valuePasswordSignUp,"Dont match with password!");
        let {val : valueAvatar, check : checkAvatar} = checkData(avatarSignUp, "Not empty");

        if (checkName && checkEmailSignUp && checkPasswordSignUp && checkPasswordConfirmSignUp && checkAvatar){
            await uploadAvatar(avatarSignUp.files[0])
                .then(rs => {
                    if (rs.status === 200){
                        valueAvatar = rs.data.pathFile;
                    }else{
                        valueAvatar = "/media/avatar-user.png";
                    }
                })
                .catch(e => {
                    valueAvatar = "/media/avatar-user.png";
                })
            let user = {
                name : valueName,
                email : valueEmailSignUp,
                password : valuePasswordSignUp,
                passwordConfirm : valuePasswordConfirmSignUp,
                avatar : valueAvatar
            }

            await insertUser(user)
                .then(rs => {
                    if (rs.status === 200){
                        modalSignUp.modal("hide");
                        alertReport(true, "Sign Up Successful");
                    }else{
                        alertReport(false, "Fail. Try again!")
                    }
                })
                .catch(e => {
                    alertReport(false, "Fail. Try again!");
                })
        }
    })
}


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
                .then( (rs) => {
                    if (rs.status === 200){
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