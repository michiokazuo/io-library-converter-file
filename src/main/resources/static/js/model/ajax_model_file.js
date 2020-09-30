const URL_PUBLIC = "/public";
const URL_FORMDATA = "/api/v1" + URL_PUBLIC;

function convert(toFormat, formData){
    return ajaxUploadFormData(`${URL_FORMDATA}/convert/to/${toFormat}`, formData);
}

function findAll() {
    return ajaxGet(`${URL_PUBLIC}/find-all`);
}

function findById(id) {
    return ajaxGet(`${URL_PUBLIC}/find-by-id/${id}`);
}

function search(formData) {
    return ajaxUploadFormData(`${URL_FORMDATA}/search`, formData);
}

function sort(formData) {
    return ajaxUploadFormData(`${URL_FORMDATA}/sort-by`, formData);
}

function deleteF(id, fileDTO) {
    return ajaxDelete(`${URL_PUBLIC}/delete-file/${id}`, fileDTO);
}

function updateViewFile(id, formData) {
    return ajaxUploadFormData(`${URL_FORMDATA}/update-view-file/${id}`, formData);
}

function share(id, formData) {
    return ajaxUploadFormData(`${URL_FORMDATA}/mail/share-file/${id}`, formData);
}