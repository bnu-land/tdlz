/**
 * Created by WUCAN on 2014/5/27.
 */

//格式化函数
// formatting function for numbers
function formatRound(value) {
    return number.round(value, 2);
}

// formatting function for dates
function formatDate(value) {
    var inputDate = new Date(value);
    return locale.format(inputDate, {
        selector: "date",
        datePattern: "MMMM d, y"
    });
}

