function formatLocalDate(localdate) {
    if(localdate === null)
        return "";
    dateArr = localdate.split("-")
    return dateArr[2] + '-' + dateArr[1] + '-' + dateArr[0];
}