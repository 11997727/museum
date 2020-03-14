function dateFormat(timeStamp) {
    var date = new Date(timeStamp);
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var result = date.getFullYear() + "-";

    result += (month < 10? "0"+month : month) + "-";
    result += (day < 10? "0"+day : day);

    return result;
}
