let selectFormatFrom, selectFormatTo, fileConvert, btnFileLocal, btnFileGoogleDrive, textFileNameFrom,
    textPassword, btnConvert, textFileNameTo, textEmailTo, btnShare;
let listFormat = [
    {text: "PDF", val: "pdf", type: "application/pdf"},
    {text: "Image", val: "png", type: "image/*"},
    {
        text: "PowerPoint",
        val: "pptx",
        type: "application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.slideshow,application/vnd.openxmlformats-officedocument.presentationml.presentation"
    },
    {
        text: "Excel",
        val: "xlsx",
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel, .csv"
    },
    {text: "Word", val: "docx", type: "application/msword"}
];
let pdf = [
    {text: "compress", val: "compress"},
    {text: "decompress", val: "decompress"},
    {text: "decrypt", val: "decrypt"},
    {text: "encrypt", val: "encrypt"}
];
let first = true;
let file, fileDTO;
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
    btnShare = $("#btn-share");

    fileConvert.change(function () {
        file = fileConvert[0].files[0];
        $('.result').addClass("hidden");
        textFileNameFrom.val(fileConvert[0].files[0].name);
    });

    btnFileLocal.click(function () {
        fileConvert.click();
    })

    showSelectOption(selectFormatFrom, listFormat);
    showSelectOption(selectFormatTo, listFormat.concat(pdf));
    chooseFormat();
    if (checkFormat(selectFormatFrom, selectFormatTo, $(".loading"))) {
        let from = listFormat.find(f => f.val === selectFormatFrom.val());
        chooseFileGGDrive(from ? from.type : "");
        fileConvert.attr('accept', from ? from.type : "");
    }
    convertFile();
    confirmShareFile();
});

function showSelectOption(element, list) {
    element.empty();
    element.append('<option value=""> - select - </option>');
    list.forEach(function (e) {
        element.append($('<option></option>').val(e.val.toLowerCase()).text(e.text));
    })
}

function chooseFormat() {
    if ($("#format") && first) {
        selectFormatFrom.val($("#format #fromFormat").val());
        selectFormatTo.val($("#format #toFormat").val());
        first = false;
    }

    let loading = $(".loading");
    let result = $('.result');
    let password = $("#password");

    selectFormatFrom.change(function () {
        result.addClass("hidden");
        textFileNameFrom.val("");

        let new_listFormat = [];
        let valSelectFormatFrom = selectFormatFrom.val().toLowerCase();
        let valSelectFormatTo = selectFormatTo.val().toLowerCase();

        if (valSelectFormatFrom === "") {
            new_listFormat = listFormat.concat(pdf);
        } else {
            new_listFormat = valSelectFormatFrom === "pdf" ? listFormat.filter(function (f, i) {
                return f.val !== "pdf";
            }).concat(pdf) : [{text: "PDF", val: "pdf"}];
        }

        let from = listFormat.find(f => f.val === selectFormatFrom.val());
        chooseFileGGDrive(from ? from.type : "");
        fileConvert.attr('accept', from ? from.type : "");

        showSelectOption(selectFormatTo, new_listFormat);
        selectFormatTo.val(valSelectFormatTo);

        checkFormat(selectFormatFrom, selectFormatTo, loading);
    });

    selectFormatTo.change(function () {
        textFileNameFrom.val("");
        password.addClass("hidden");
        result.addClass("hidden");

        let new_listFormat = [];
        let valSelectFormatFrom = selectFormatFrom.val().toLowerCase();
        let valSelectFormatTo = selectFormatTo.val().toLowerCase();

        if (valSelectFormatTo === "") {
            new_listFormat = listFormat;
        } else {
            new_listFormat = valSelectFormatTo === "pdf" ? listFormat.filter(function (f, i) {
                return f.val !== "pdf";
            }) : [{text: "PDF", val: "pdf"}];
        }

        showSelectOption(selectFormatFrom, new_listFormat);
        selectFormatFrom.val(valSelectFormatFrom);

        if (valSelectFormatTo === "decrypt" || valSelectFormatTo === "encrypt") {
            password.removeClass("hidden");
        }

        checkFormat(selectFormatFrom, selectFormatTo, loading);
    });
}

function chooseFileGGDrive(typeFile) {
    btnFileGoogleDrive.click(function () {
        var developerKey = 'AIzaSyCSLBQVWyGIqIzieQDqvN6RtFRgOz57-mA';
        var clientId = "1060024421170-l6t9ngeqnaohi0c3ijbkcqcjrtq312ns.apps.googleusercontent.com"
        var appId = "1060024421170";
        var scope = ['https://www.googleapis.com/auth/drive.file'];

        var pickerApiLoaded = false;
        var oauthToken;

        loadPicker();

        // Use the Google API Loader script to load the google.picker script.
        function loadPicker() {
            $('.result').addClass("hidden");
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
                view.setMimeTypes(typeFile);
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
                console.log(data.docs[0])
                fileId = data.docs[0].id;
                fileName = data.docs[0].name;
                fileType = data.docs[0].mimeType;
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

        function getContentFile(id, name, type) {
            fetchBlob('https://www.googleapis.com/drive/v3/files/' + id + '?alt=media', function (data) {
                console.log(data)
                file = new File([new Blob([data], {type: type})], name, {
                    type: type,
                    lastModified: Date.now()
                });

                textFileNameFrom.val(name);

                console.log(file);
            });
        }
    });
}

function shareFile() {
    $(".share-file").click(function () {
        textEmailTo.val("");
        $("#share-file").modal("show");
    })
}

function confirmShareFile() {
    btnShare.click(async function () {
        let {val: valEmail, check: checkEmailShare} = checkEmail(textEmailTo, "You haven't entered email");

        if (checkEmailShare) {
            let formData = new FormData();
            formData.append("email", valEmail);

            let str = "Share Failed. Please share file again!!!";
            let check = false;

            await share(fileDTO.id, formData).then(function (rs) {

                if (rs.status === 200) {
                    str = "Share Completed";
                    check = true;
                }

            }).catch(function (e) {
                console.log(e);
            });

            alertReport(check, str);
            $("#share-file").modal("hide");
        }

    });
}

function convertFile() {
    btnConvert.click(async function () {
        selectFormatFrom.prop("disabled", true);
        selectFormatTo.prop("disabled", true);

        let result = `<h3 class="text-dark w-100">Your file hasn't converted completed. Please convert file again!!!</h3>`;
        let {val: valSelectFormatFrom, check: checkFormatFrom} = checkData(selectFormatFrom, "You haven't chosen format source file");
        let {val: valSelectFormatTo, check: checkFormatTo} = checkData(selectFormatTo, "You haven't chosen format target file");
        let {val: valFileName, check: checkFileName} = checkData(textFileNameFrom, "You haven't chosen file");

        if (checkFormatTo && checkFormatFrom && checkFileName) {
            let formData = new FormData();

            if (valSelectFormatTo.toLowerCase() === "decrypt" || valSelectFormatTo.toLowerCase() === "encrypt") {
                let {val: valPassword, check: checkPassword} = checkData(textPassword, /./, "You haven't entered password");
                if (checkPassword) formData.append("password", valPassword);
            }

            formData.append("file", file);

            $(".loading").removeClass("hidden");
            $(".loading > div").removeClass("hidden");

            await convert(valSelectFormatTo.toLowerCase(), formData).then(function (rs) {
                if (rs.status === 200) {
                    fileDTO = rs.data;
                    result = `<h3 class="text-dark w-100">Your file has converted completed. Please download that file!!!</h3>
                            <div class="file-convert w-100 row justify-content-between align-items-center">
                            <div class="file-convert-name col-sm-6 font-weight-bold">
                                <i class="far fa-file text-success" width="1em" height="1em"></i>
                                <span id="file-convert-name" class>${fileDTO.fileName}</span>
                            </div>
                            <div class="download col-sm-6">
                                <button type="button" class="btn btn-warning m-1 share-file">
                                        <i class="fas fa-share-alt"></i>
                                        Share
                                </button>
                                <a href="${fileDTO.urlDownload}" class="text-decoration-none text-light btn btn-success m-1">
                                    <i class="fas fa-download"></i>
                                    <span class="text-light"> DOWNLOAD FILE </span>
                                </a>
                            </div>`;

                    let linkShare = $("#link-share");
                    linkShare.attr('href', fileDTO.urlDownload);
                    linkShare.html('http:localhost:8080' + fileDTO.urlDownload);
                }
            }).catch(function (e) {
                console.log(e);
            })

            $(".result > .container > .row").html(result);
            $('.result').removeClass("hidden");
            shareFile();
        }

        $(".loading > div").addClass("hidden");
        $('.loading').addClass("hidden");
        selectFormatFrom.prop("disabled", false);
        selectFormatTo.prop("disabled", false);
    })
}

function checkFormat(e1, e2, m) {
    m.removeClass("hidden");
    $("#text-alert").removeClass("hidden");
    if (e1.val() !== "" && e2.val() !== "") {
        m.addClass("hidden");
        $("#text-alert").addClass("hidden");
        return true;
    }
    return false;
}