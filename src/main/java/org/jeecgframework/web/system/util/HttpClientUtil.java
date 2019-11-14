package org.jeecgframework.web.system.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibao.chaopei.util.http.Body;
import com.weibao.chaopei.util.http.Element;
import com.weibao.chaopei.util.http.Head;
import com.weibao.chaopei.util.http.RelFieldName;

public class HttpClientUtil {
	private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";
 
    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            // 整个连接池最大连接数
            cm.setMaxTotal(50);
            // 每路由最大连接数，默认值是2
            cm.setDefaultMaxPerRoute(5);
        }
    }
 
    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).build();
    }
 
    /**
     * @param url
     * @return
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }
 
    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }
 
    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);
        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }
 
    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }
 
    public static String httpPostRequest(String url,String json) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json,"utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }
 
    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }
 
    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
 
        return getResult(httpPost);
    }
 
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return pairs;
    }
 
    /**
     * 处理Http请求
     */
    private static String getResult(HttpRequestBase request) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
    	long s = System.currentTimeMillis();
        CloseableHttpClient httpClient = getHttpClient();
        long s2 = System.currentTimeMillis();
        System.out.println("getHttpClient:"+(s2-s));
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            long s3 = System.currentTimeMillis();
            System.out.println("excute:"+(s3-s2));
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_STR;
    }
    
    public static void main(String[] args) {
    	String url = "http://devyun.guorenpcic.com/httpCurrency/httpJsonCommonEntrance";
    	Gson gson = new GsonBuilder().create();
    	com.weibao.chaopei.util.http.ApiPackage package_ = new com.weibao.chaopei.util.http.ApiPackage();
    	Head head = new Head();
    	head.setCLIENT_ENCODE("UTF-8");
    	head.setSERVE_CODE("GR0002");    	
    	head.setCHANNEL_CODE("openA09SHML01");
    	head.setINTERFACE_CODE("II000042");
    	head.setINTERFACE_USER_CODE("SHML");
    	head.setINTERFACE_PWD("$2a$04$vnx/TydV3XjNo2EjuPVaCeLr0UnZeFH1zMYV/WKdxdJ3vNHxBtsVm");
    	head.setBUSINESS_UUID("4028dc816e4e59e2016e4e6fee980006");
    	head.setREQUEST_TIME("2019-11-11 23:05:12:0005");
    	head.setIFTEST("N");
    	head.setXML_LIST_SUFFIX("");
    	
    	package_.setHEAD(head);
    	Body body = new Body();
    	package_.setBODY(body);
    	body.setOperCode("18926493735");
    	body.setOperType("A");
    	body.setOrder_source("03");
    	body.setPermium("12");
    	body.setPlan_code("P2275");
    	body.setPlan_name("国任升华茂林物流责任险I");
    	body.setGood_code("G0586");
    	body.setGood_name("国任升华茂林物流责任险");
    	body.setAppnum("1");
    	
    	Element base = new Element();
    	body.setBase(base);
    	base.setProdCode("1531");
    	base.setRelTbl("ins_appl_main");
    	RelFieldName relFieldName_base = new RelFieldName();
    	base.setRelFieldName(relFieldName_base);
    	relFieldName_base.setPlcy_bgn_time("2019-11-30");
    	relFieldName_base.setPlcy_end_time("2019-11-29");
    	relFieldName_base.setCall_back_url("http://yundmz.pcic:90/jcx-host/guoren/guorenrecvData.ashx");
    	
    	Element applicant = new Element();
    	body.setApplicant(applicant);
    	applicant.setProdCode("1531");
    	applicant.setRelTbl("ins_appl_applnt");
    	RelFieldName relFieldName_applicant = new RelFieldName();
    	applicant.setRelFieldName(relFieldName_applicant);
    	relFieldName_applicant.setAppl_name("东莞市大朗超捷货运服务部");
    	relFieldName_applicant.setClnt_type("2");
    	relFieldName_applicant.setCertf_type("99");
    	relFieldName_applicant.setCertf_no("92441900L06692572F");
    	relFieldName_applicant.setEmail("");
    	relFieldName_applicant.setMobile("13826300503");
    	relFieldName_applicant.setBank_account("");
    	relFieldName_applicant.setBank_account_name("");
    	relFieldName_applicant.setBank_code("");
    	
    	Element insured = new Element();
    	body.setInsured(insured);
    	insured.setProdCode("1531");
    	insured.setRelTbl("ins_appl_insrnt");
    	RelFieldName relFieldName_insured = new RelFieldName();
    	insured.setRelFieldName(relFieldName_insured);    	
    	relFieldName_insured.setCertf_type("99");
    	relFieldName_insured.setCertf_no("92441900L06692572F");
    	relFieldName_insured.setInsrnt_name("东莞市大朗超捷货运服务部");
    	relFieldName_insured.setMobile("13826300503");
    	
    	Element tgtCar = new Element();
    	body.setTgtCar(tgtCar);
    	tgtCar.setProdCode("1531");
    	tgtCar.setRelTbl("ins_appl_tgt_car");
    	RelFieldName relFieldName_tgtCar = new RelFieldName();
    	tgtCar.setRelFieldName(relFieldName_tgtCar);    	
    	relFieldName_tgtCar.setLicense_no("粤S98684");
    	relFieldName_tgtCar.setFrame_no("LGGR2BG42CL714501");
    	relFieldName_tgtCar.setEngine_no("J64L1C00023");
    	relFieldName_tgtCar.setSeat_num("5");
    	relFieldName_tgtCar.setToncount("4.5");
    	
    	List limit = new ArrayList();
    	body.setLimit(limit);
    	
    	Map package_map = new HashMap();
    	package_map.put("PACKAGE", package_);
    	String json = gson.toJson(package_map);
    	
    	String response = HttpClientUtil.httpPostRequest(url, json);    	
    	System.out.println(response);
    	String response1 = HttpClientUtil.httpPostRequest(url, json);    	
    	System.out.println(response1);
	}
}
