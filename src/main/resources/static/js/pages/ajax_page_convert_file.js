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

    fileConvert.change(function () {
        $('.result').addClass("hidden");
        textFileNameFrom.val(fileConvert[0].files[0].name);
    });

    btnFileLocal.click(function () {
        fileConvert.click();
    })

    btnFileGoogleDrive.click(function () {
        // The Browser API key obtained from the Google API Console.
        // Replace with your own Browser API key, or your own key.
        var developerKey = 'AIzaSyDtAzWsaO1-phtipZkgakFuVexsM6V4qU0';

        // The Client ID obtained from the Google API Console. Replace with your own Client ID.
        var clientId = "637109986363-j4jh5r54q7gdqda01pbt10lfndphki57.apps.googleusercontent.com"

        // Replace with your own project number from console.developers.google.com.
        // See "Project number" under "IAM & Admin" > "Settings"
        var appId = "637109986363";

        // Scope to use to access user's Drive items.
        var scope = ['https://www.googleapis.com/auth/drive.file'];

        var pickerApiLoaded = false;
        var oauthToken;

        loadPicker();

        // Use the Google API Loader script to load the google.picker script.
        function loadPicker() {
            gapi.load('auth', { 'callback': onAuthApiLoad });
            gapi.load('picker', { 'callback': onPickerApiLoad });
        }

        function onAuthApiLoad() {
            window.gapi.auth.authorize(
                {
                    'client_id': clientId,
                    'scope': scope,
                    'immediate': false
                },
                handleAuthResult);
        }

        function onPickerApiLoad() {
            pickerApiLoaded = true;
            createPicker();
        }

        function handleAuthResult(authResult) {
            if (authResult && !authResult.error) {
                oauthToken = authResult.access_token;
                createPicker();
            }
        }

        // Create and render a Picker object for searching images.
        function createPicker() {
            if (pickerApiLoaded && oauthToken) {
                var view = new google.picker.View(google.picker.ViewId.DOCS);
                view.setMimeTypes("image/png,image/jpeg,image/jpg");
                var picker = new google.picker.PickerBuilder()
                    .enableFeature(google.picker.Feature.NAV_HIDDEN)
                    .enableFeature(google.picker.Feature.MULTISELECT_ENABLED)
                    .setAppId(appId)
                    .setOAuthToken(oauthToken)
                    .addView(view)
                    .addView(new google.picker.DocsUploadView())
                    .setDeveloperKey(developerKey)
                    .setCallback(pickerCallback)
                    .build();
                picker.setVisible(true);
            }
        }

        // A simple callback implementation.
        function pickerCallback(data) {
            if (data.action == google.picker.Action.PICKED) {
                console.log(data.docs);
                var fileId = data.docs[0].id;
                alert('The user selected: ' + fileId);
            }
        }
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
        $('.result').addClass("hidden");
        textFileNameFrom.val("");

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
        textFileNameFrom.val("");
        $('.result').addClass("hidden");

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
    btnConvert.click(function () {
        selectFormatFrom.prop("disabled", true);
        selectFormatTo.prop("disabled", true);
        $(".loading").removeClass("hidden");


        $('.loading').addClass("hidden");
        $('.result').removeClass("hidden");
        selectFormatFrom.prop("disabled", false);
        selectFormatTo.prop("disabled", false);
    })
}