let selectFormatFrom, selectFormatTo, fileConvert, btnFileLocal, btnFileGoogleDrive, textFileNameFrom,
    textPassword, btnConvert, textFileNameTo, textEmailTo;
let listFormat = [
    {"text": "PDF", "val": "pdf", type : ""},
    {"text": "Image", "val": "png", type : ""},
    {"text": "PowerPoint", "val": "pptx", type : ""},
    {"text": "Excel", "val": "xlsx", type : ""},
    {"text": "Word", "val": "docx", type : ""}
];
let pdf = [
    {"text": "compress", "val": "compress", type : ""},
    {"text": "decompress", "val": "decompress", type : ""},
    {"text": "decrypt", "val": "decrypt", type : ""},
    {"text": "encrypt", "val": "encrypt", type : ""}
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

    if (selectFormatTo.val() === "" || selectFormatFrom.val() === ""){
        $(".loading").removeClass("hidden");
    }

    btnFileGoogleDrive.click(function () {
        // The Browser API key obtained from the Google API Console.
        // Replace with your own Browser API key, or your own key.
        var developerKey = 'AIzaSyCSLBQVWyGIqIzieQDqvN6RtFRgOz57-mA';

        // The Client ID obtained from the Google API Console. Replace with your own Client ID.
        var clientId = "1060024421170-l6t9ngeqnaohi0c3ijbkcqcjrtq312ns.apps.googleusercontent.com"

        // Replace with your own project number from console.developers.google.com.
        // See "Project number" under "IAM & Admin" > "Settings"
        var appId = "1060024421170";

        // Scope to use to access user's Drive items.
        var scope = ['https://www.googleapis.com/auth/drive.file'];

        var pickerApiLoaded = false;
        var oauthToken;

        let s;

        loadPicker();

        // Use the Google API Loader script to load the google.picker script.
        function loadPicker() {
            gapi.load('auth', {'callback': onAuthApiLoad});
            gapi.load('picker', {'callback': onPickerApiLoad});
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
                view.setMimeTypes("image/png");
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
            if (data.action === google.picker.Action.PICKED) {
                fileId = data.docs[0].id;
                fileName = data.docs[0].name;
                fileType = data.docs[0].mimeType;
                console.log(data.docs[0])
                getContentFile(fileId, fileName, fileType);
            }
        }

        function fetchBlob(url, callback) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url);
            xhr.responseType = 'blob';
            xhr.setRequestHeader("Authorization", 'Bearer ' + oauthToken);
            xhr.onload = function () {
                console.log("Got response");
                callback(this.response);
            };
            xhr.onerror = function () {
                console.log('Failed to fetch ' + url);
            };
            xhr.send();
        }

        /**
         * @param {String} id Google drive fileId
         */
        function getContentFile(id, name, type) {
            fetchBlob('https://www.googleapis.com/drive/v3/files/' + id + '?alt=' + type, function (data) {
                s = new File([new Blob([data], {type: type})], name, {
                    type: type,
                    lastModified: Date.now()
                });

                console.log(s);
            });
        }
    });

    showSelectOption(selectFormatFrom, listFormat);
    showSelectOption(selectFormatTo, listFormat.concat(pdf));
    chooseFormat();
    convertFile();
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


        // $('.loading').addClass("hidden");
        // $('.result').removeClass("hidden");
        // selectFormatFrom.prop("disabled", false);
        // selectFormatTo.prop("disabled", false);
    })
}