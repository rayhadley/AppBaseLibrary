package com.stardust.app.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取url中的参数
 * Created by haohua on 2017/1/18.
 */

public class GetUrlParamsUtil {

    /**
     * 解析出url请求的路径，包括页面
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL)
    {
        String strPage=null;
        String[] arrSplit=null;

        strURL=strURL.trim().toLowerCase();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>0)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[0]!=null)
                {
                    strPage=arrSplit[0];
                }
            }
        }

        return strPage;
    }
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL)
    {
        String strAllParam=null;
        String[] arrSplit=null;

        strURL=strURL.trim().toLowerCase();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>1)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[1]!=null)
                {
                    strAllParam=arrSplit[1];
                }
            }
        }

        return strAllParam;
    }
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=null;

        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null)
        {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit)
        {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");

            //解析出键值
            if(arrSplitEqual.length>1)
            {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            }
            else
            {
                if(arrSplitEqual[0]!="")
                {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 获取参数
     * */
    public static String getParamByKey(String url, String key) {
        String result = "";
        if (url == null || url.trim().equals("") || key == null || key.trim().equals("")) {
            result  = "";
        }
        Map<String, String> mapRequest = URLRequest(url);

        return mapRequest.get(key) + "";
    }

    public static void main(String[] args) {
        // 请求url
//        String str = "index.jsp?Action=del&id=123&sort=";
        String str = "native:imageUpload?event=on_imgUpload_Callback&isCut=1&apiUrl=http%3A%2F%2F101.201.209.107%3A8088%2Fmars-router-api%2Frouter%3Faccess_token%3D2a421e46caef4c50b246e36c109a30e4%26app_key%3Dydzf_mobile_key%26client_id%3DUUID%26format%3Djson%26os_type%3Dandroid%26response_sign%3Dfalse%26service_id%3Dcommon_service.uploadFile%26timestamp%3D1493690154006%26type%3D1%26version%3D1%26sign%3Db67beacc382ce196a1fccb2822ce6c0a";

        //url页面路径
        System.out.println(GetUrlParamsUtil.UrlPage(str));

        //url参数键值对
        String strRequestKeyAndValues="";
        Map<String, String> mapRequest = GetUrlParamsUtil.URLRequest(str);

        for(String strRequestKey: mapRequest.keySet()) {
            String strRequestValue=mapRequest.get(strRequestKey);
            strRequestKeyAndValues+="key:"+strRequestKey+",Value:"+strRequestValue+";";

        }
        System.out.println(strRequestKeyAndValues);

        //获取无效键时，输出null
        System.out.println(mapRequest.get("page"));
    }

}

