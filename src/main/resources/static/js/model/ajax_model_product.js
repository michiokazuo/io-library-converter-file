const URL_PUBLIC = "/public"

function convertFile(toFormat, formData){
    return ajaxUploadFile(`${URL_PUBLIC}/convert/to/${toFormat}`, formData);
}

function findAll() {
    return ajaxGet(`${URL_PUBLIC}/find-all`);
}

function findById(id) {
    return ajaxGet(`${URL_PUBLIC}/find-by-id/${id}`);
}

function search(fileName, startDate, endDate) {
    return ajaxGet(`${URL_PUBLIC}//search/${fileName}/${startDate}/${endDate}`)
}

function sort(field, isASC) {
    return ajaxGet(`${URL_PUBLIC}/sort-by/${field}/${isASC}`);
}

function deleteFile(id, fileDTO) {
    return ajaxDelete(`${URL_PUBLIC}/delete/${id}`, fileDTO);
}

function updateViewFile(id, fileDTO) {
    return ajaxPut(`${URL_PUBLIC}/update-view-file/${id}`, fileDTO);
}