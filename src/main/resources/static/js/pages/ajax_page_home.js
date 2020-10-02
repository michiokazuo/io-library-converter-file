let strURL = window.location.href;
if (strURL.includes("/login")){
    $("#home").removeClass("active");
    $("#convert-file").removeClass("active")
    $("#login").addClass("active");
}
if (strURL.includes("/home")){
    $("#home").addClass("active");
    $("#convert-file").removeClass("active")
    $("#login").removeClass("active");
}
if (strURL.includes("/convert-file")){
    $("#home").removeClass("active");
    $("#convert-file").addClass("active")
    $("#login").removeClass("active");
}
let user = {};
let navUser, listData, userInfo;

$(async function (){
    navUser = $("#nav-user");
    listData = $("#list-data");
    userInfo = $("#user-info");

    await getUserInfo();
    setUserInfo();
    setListData();
})

function setListData(){
    let output = `<span class="text-center text-danger">Dont have any file</span>`;
    try{
        if (user.fileDTOList.length > 0){
            output = user.fileDTOList.map( (item) => {
                return `<li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fas fa-file text-info"></i>
                            <span class="ml-2 text-dark">${item.fileName}</span>
                        </a>
                    </li>`;
            }).join("");
        }
    }catch (e){
        //
    }
    listData.html(output);
}

function setUserInfo(){
    if (user.name !== undefined){
        let output = `<img src="${user.avatar}" class="rounded-circle" alt="" width="50px">
                      <span class="ml-2 text-success">${user.name}</span>`;
        userInfo.html(output);
    }
}

async function getUserInfo(){
    await getUser()
        .then(rs => {
            if (rs.status === 200){
                user = rs.data;
            }
        })
        .catch(e => {
            // dont do nothing
        })
}