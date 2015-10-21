/**
 * Created by WUCAN on 2014/5/27.
 */
//加载其他资源
//document.write('<script src="map/HashTable.js" type="text/javascript"></script>');
var MAPURLS;

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


