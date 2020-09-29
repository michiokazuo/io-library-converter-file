let btnDelete, btnShare, btnView, textEmailTo, selectSort, tableData, textSearchName, dateSearchCreateDate, btnSearch;
let listFile = [];
let listSort = [
    {text: "(Name) A -> Z", val: "filename-true"},
    {text: "(Name) Z -> A", val: "filename-false"},
    {text: "Recently", val: "create_date-false"}
]
let indexFile, file;
const OBJECT = "object";
$(async function () {
    btnDelete = $("#btn-delete");
    btnShare = $("#btn-share");
    btnView = $("#btn-view");
    textEmailTo = $("#email-to");
    selectSort = $("#sort");
    tableData = $("#table-data");
    textSearchName = $("#input-search-name");
    dateSearchCreateDate = $("#input-search-create-date");
    btnSearch = $("#btn-search");

    showSelectOption(selectSort, listSort);
    await loadFile();
    viewMyFile();
    confirmShareFile();
    sortFile();
    searchFile();
    confirmDeleteFile();
})

async function loadFile() {
    await findAll().then(rs => {
        if (rs.status === 200) {
            typeof rs.data === OBJECT ? listFile = rs.data : listFile = [];
        } else {
            listFile = [];
            console.log(rs.status);
        }
    }).catch(e => {
        console.log(e);
    });
}

function viewMyFile() {
    let rs = `<tr><td colspan='4'><strong>No Data</strong></td></tr>`;

    if (listFile && listFile.length > 0) {
        rs = dataFilter(listFile).map((data, index) => {
            return `<tr data-index="${index}">
                        <th scope="row">${index + 1}</th>
                        <td>${data.fileName}</td>
                        <td>${new Date(data.createDate).toLocaleDateString("en-US")}</td>
                        <td>
                            <button type="button" class="btn btn-warning m-1 share-file">
                                <i class="fas fa-share-alt"></i>
                                Share
                            </button>
                            <button type="button" class="btn btn-danger m-1 delete-file">
                                <i class="fas fa-trash-alt"></i>
                                Delete
                            </button>
                                <a href="${data.urlDownload}" class="text-decoration-none text-light btn btn-success m-1">
                                    <i class="fas fa-download"></i>
                                    <span class="text-light"> Download File </span>
                                </a>
                        </td>
                    </tr>`;
        });
    }

    tableData.html(rs);
    shareFile();
    deleteFile();
}

function shareFile() {
    $(".share-file").click(function () {
        indexFile = $(this).parents("tr").attr("data-index");
        textEmailTo.val("");

        $("#share-file").modal("show");
    })
}

function confirmShareFile() {
    btnShare.click(async function () {
        file = listFile[indexFile - 0];

        let linkShare = $("#link-share");
        linkShare.attr('href', file.urlDownload);
        linkShare.html('http:localhost:8080' + file.urlDownload);

        let {val: valEmail, check: checkEmailShare} = checkEmail(textEmailTo, "You haven't entered email");
        if (checkEmailShare) {
            let formData = new FormData();
            formData.append("email", valEmail);

            await share(file.id, formData).then(function (rs) {
                let str = "";

                if (rs.status === 200) {
                    str = "Share Completed";
                } else {
                    str = "Share Failed. Please share file again!!!"
                }

                alert(str);
            }).catch(function (e) {
                console.log(e);
            });

            $("#share-file").modal("hide");
        }
    });
}

function deleteFile() {
    $(".delete-file").click(function () {
        indexFile = $(this).parents("tr").attr("data-index");

        $("#delete-file").modal("show");
    });
}

function confirmDeleteFile() {
    btnDelete.click(async function () {
        file = listFile[indexFile - 0];

        await deleteF(file.id, file).then(function (rs) {
            let str = "";

            if (rs.status === 200) {
                str = "Delete Completed";
                listFile = listFile.filter(function (data, index) {
                    return index !== (indexFile - 0);
                });
            } else {
                str = "Delete Failed. Please delete file again!!!"
            }

            alert(str);
        }).catch(function (e) {
            console.log(e);
        });

        $("#delete-file").modal("hide");
        viewMyFile();
    })
}

function sortFile() {
    selectSort.change(async function () {
        await search_sortFile();
    });
}

function searchFile() {
    btnSearch.click(async function f() {
        await search_sortFile();
    })
}

async function search_sortFile() {
    let listF1 = [];
    let listF2 = listFile;

    let valSelectSearchSort = selectSort.val();
    let valName = textSearchName.val();
    let valCreateDate = dateSearchCreateDate.val();
    let formData = new FormData();

    let valSort = valSelectSearchSort.split("-");
    valSort[0] && valSort[0] !== "" ? formData.append("field", valSort[0]) : "";
    valSort[1] && valSort[1] !== "" ? formData.append("isASC", valSort[1]) : "";

    await sort(formData).then(function (rs) {
        if (rs.status === 200) {
            typeof rs.data === OBJECT ? listF2 = rs.data : listF2 = [];
        }
    }).catch(function (e) {
        console.log(e);
    });

    formData = new FormData();
    valName !== "" ? formData.append("file-name", valName) : "";
    if (valCreateDate !== "") {
        formData.append("start-date", Date.parse(new Date(valCreateDate).toLocaleDateString()));
        formData.append("end-date", Date.parse(new Date(valCreateDate).toLocaleDateString()));
    }

    await search(formData).then(function (rs) {
        if (rs.status === 200) {
            typeof rs.data === OBJECT ? listF1 = rs.data : listF1 = [];
        }
    }).catch(function (e) {
        console.log(e);
    });

    listFile = listF1;
    if (listF1 && listF1.length > 0)
        listFile = listF2.filter((data, index) => {
            return listF1.find((d, i) => {
                return d.id === data.id;
            });
        });

    viewMyFile();
}

function showSelectOption(element, list) {
    element.empty();
    element.append('<option value=""> - Sort - </option>');
    list.forEach(function (e) {
        element.append($('<option></option>').val(e.val.toLowerCase()).text(e.text));
    })
}