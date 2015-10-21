/**
 * Created by WUCAN on 2014/5/27.
 */
//加载其他资源
//document.write('<script src="map/HashTable.js" type="text/javascript"></script>');
var MAPURLS;
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

Ext.Ajax.request(
    {
        url: 'map_getMapURLByName',
        success: function (response) {
            var result = Ext.decode(response.responseText);
            MAPURLS = result.VALUE;
            //console.log('basemap', list["basemap"]);
        },
        failure: function (response) {
            //failedResult();
            // Ext.Msg.alert('失败提示', '记录删除失败。');
        }
    });


