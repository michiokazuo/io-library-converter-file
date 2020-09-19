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