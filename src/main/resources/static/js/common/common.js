
/** formating money to ###.## */
function formatMoney(money) {
    if(money === 0){
        return ''
    } else if(typeof money == 'number') {
        money = money.toString();
    }
    let moneyArray = money.split('.');
    if(moneyArray.length === 2 && moneyArray[1].length === 1) {
        return `${moneyArray[0]}.${moneyArray[1]}0`
    } else if (moneyArray.length === 1) {
        return `${moneyArray[0]}.00`
    }
    return money;
}


/** Date class to yyyy-mm-dd  */
function formatDate(date){
    var dd = String(date.getDate()).padStart(2, '0');
    var mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = date.getFullYear();
    return yyyy + '-' + mm + '-' + dd;
}


/** reverses fields order */ //TODO ???
function formatLocalDate(localdate) {
    if(localdate === null)
        return "";
    dateArr = localdate.split("-")
    return dateArr[2] + '-' + dateArr[1] + '-' + dateArr[0];
}

/** #xxxxxx to rgb(x,x,x) */
function hexToRGB(h) {
    let r = 110, g = 110, b = 255;
    if(h){
        if (h.length == 4) { // 3 digits
            r = "0x" + h[1] + h[1];
            g = "0x" + h[2] + h[2];
            b = "0x" + h[3] + h[3];
        } else if (h.length == 7) { // 6 digits
            r = "0x" + h[1] + h[2];
            g = "0x" + h[3] + h[4];
            b = "0x" + h[5] + h[6];
        }
    }
    return "rgb("+ +r + "," + +g + "," + +b + ")";
}

function random_rgba() {
    var o = Math.round, r = Math.random, s = 255;
    return 'rgba(' + o(r()*s) + ',' + o(r()*s) + ',' + o(r()*s) + ',' + r().toFixed(1) + ')';
}

