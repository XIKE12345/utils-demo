//package com.jieyun.test.demo;
//
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @CalssName StrToJson
// * @Desc TODO
// * @Author huiKe
// * @email <link>754873891@qq.com </link>
// * @CreateDate 2020/1/16
// * @Version 1.0
// **/
//public class StrToJson {
//
//    public static void main(String[] args) throws JSONException  {
//
//        String str = "[{\"postTime\":\"1579157960557\",\"postBatchNo\":\"f1d0cecb43204caa9de570396ece2c2f\",\"factoryId\":\"JSNT_ZSSY_01\",\"dataBody\":[{\"dcsId\":\"LI023\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"273.6664\"},{\"dcsId\":\"LI024\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"7636.3220\"},{\"dcsId\":\"LI025\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"1746.8000\"},{\"dcsId\":\"LI026\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"2436.7420\"},{\"dcsId\":\"LI027\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"3892.7040\"},{\"dcsId\":\"LI028\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"385.3347\"},{\"dcsId\":\"LI029\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"13498.1200\"},{\"dcsId\":\"LI030\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"3569.2070\"},{\"dcsId\":\"LI031\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"43.8990\"},{\"dcsId\":\"LI032\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"561.2429\"},{\"dcsId\":\"LI033\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"9017.6070\"},{\"dcsId\":\"LI034\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"0.0000\"},{\"dcsId\":\"LI035\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"1214.4460\"},{\"dcsId\":\"LI036\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"1338.0810\"},{\"dcsId\":\"LI037\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"3973.8030\"},{\"dcsId\":\"LI038\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"2752.3210\"},{\"dcsId\":\"LI039\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"4560.2030\"},{\"dcsId\":\"LI040\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"1180.3200\"},{\"dcsId\":\"TI023\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"3.8810\"},{\"dcsId\":\"TI024\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"8.5037\"},{\"dcsId\":\"TI025\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"37.4939\"},{\"dcsId\":\"TI026\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.5786\"},{\"dcsId\":\"TI027\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"5.6287\"},{\"dcsId\":\"TI028\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.0847\"},{\"dcsId\":\"TI029\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.3253\"},{\"dcsId\":\"TI030\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"9.2763\"},{\"dcsId\":\"TI031\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"7.3259\"},{\"dcsId\":\"TI032\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"5.2615\"},{\"dcsId\":\"TI033\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"5.7174\"},{\"dcsId\":\"TI034\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.1987\"},{\"dcsId\":\"TI035\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.3886\"},{\"dcsId\":\"TI036\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"5.2488\"},{\"dcsId\":\"TI037\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"4.6535\"},{\"dcsId\":\"TI038\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"4.2356\"},{\"dcsId\":\"TI039\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.2493\"},{\"dcsId\":\"TI040\",\"collectionTime\":\"1579157731000\",\"dcsValue\":\"6.6293\"}],\"sign\":\"0ECA988B875DF2C8CD9008BBC91CE0839B36338CDE2B17B69F96B83FEC4073EF\"}]";
//
//        String mapStr = StrToJson.getMapStr(str);
//        System.out.println(mapStr);
//    }
//
//    private static String getMapStr(String str) throws JSONException{
//        Map<String, Object> strMap = new HashMap<>(16);
//        JSONArray jsonArray = new JSONArray(str);
//        if (jsonArray.length() > 0) {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                strMap.put("postTime", jsonObject.get("postTime"));
//                strMap.put("postBatchNo", jsonObject.get("postBatchNo"));
//                strMap.put("factoryId", jsonObject.get("factoryId"));
//                strMap.put("sign", jsonObject.get("sign"));
//            }
//        }
//        return strMap.toString();
//    }
//}
