let selectFormatFrom, selectFormatTo, fileConvert, btnFileLocal, btnFileGoogleDrive, textFileNameFrom,
    textPassword, btnConvert, textFileNameTo, textEmailTo;
let listFormat = [
    {"text": "PDF", "val": "pdf"},
    {"text": "Image", "val": "png"},
    {"text": "PowerPoint", "val": "pptx"},
    {"text": "Excel", "val": "xlsx"},
    {"text": "Word", "val": "docs"}
];
let pdf = [
    {"text": "compress", "val": "compress"},
    {"text": "decompress", "val": "decompress"},
    {"text": "decrypt", "val": "decrypt"},
    {"text": "encrypt", "val": "encrypt"}
];

$(function () {
    selectFormatFrom = $("#source-format-select");
    selectFormatTo = $("#destination-format-select");
    fileConvert = $("#choose-file");
    btnFileLocal = $("#file-local");
    btnFileGoogleDrive = $("#file-drive");
    textFileNameFrom = $("#file-name");
    textPassword = $("#password");
    btnConvert = $("#btn-convert");
    textFileNameTo = $("#file-convert-name");
    textEmailTo = $("#email-to");

    fileConvert.change(function (){
       textFileNameFrom.val(fileConvert[0].files[0].name);
    });

    btnFileLocal.click(function () {
        fileConvert.click();
    })

    showSelectOption(selectFormatFrom, listFormat);
    showSelectOption(selectFormatTo, listFormat.concat(pdf));
    chooseFormat();

});

function showSelectOption(element, list) {
    element.append('<option value=""> - select - </option>');
    list.forEach(function (e) {
        element.append($('<option></option>').val(e.val.toLowerCase()).text(e.text));
    })
}

function chooseFormat() {
    selectFormatFrom.change(function () {
        let new_listFormat = [];
        let valSelectFormatFrom = selectFormatFrom.val().toLowerCase();
        let valSelectFormatTo = selectFormatTo.val().toLowerCase();

        if (valSelectFormatFrom === "") {
            new_listFormat = listFormat.concat(pdf);
        } else {
            new_listFormat = valSelectFormatFrom !== "pdf" ? listFormat.filter(function (f, i) {
                return f.val !== "pdf";
            }).concat(pdf) : [{"text": "PDF", "val": "pdf"}];
        }

        showSelectOption(selectFormatTo, new_listFormat);
        selectFormatTo.val(valSelectFormatTo);
    });

    selectFormatTo.change(function () {
        let new_listFormat = [];
        let valSelectFormatFrom = selectFormatFrom.val().toLowerCase();
        let valSelectFormatTo = selectFormatTo.val().toLowerCase();

        if (valSelectFormatTo === "") {
            new_listFormat = listFormat;
        } else {
            new_listFormat = valSelectFormatTo === "pdf" ? listFormat.filter(function (f, i) {
                return f.val !== "pdf";
            }) : [{"text": "PDF", "val": "pdf"}];
        }

        showSelectOption(selectFormatFrom, new_listFormat);
        selectFormatFrom.val(valSelectFormatFrom);
    });


}

function viewContentFile() {

}

function shareFile() {

}

function confirmShareFile() {

}

function convertFile() {

}