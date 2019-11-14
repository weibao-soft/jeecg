package com.weibao.chaopei.util.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:guoren_api.properties"})
public class GuorenApiConfig {
	
	@Value("${API.URL}")
	public String API_URL;
	
	@Value("${HEAD.CLIENT_ENCODE}")
    public String CLIENT_ENCODE; 

    @Value("${HEAD.SERVE_CODE}")
    public String SERVE_CODE; 

    @Value("${HEAD.CHANNEL_CODE}")
    public String CHANNEL_CODE; 
    
    @Value("${HEAD.INTERFACE_CODE.HEBAO}")
    public String INTERFACE_CODE_HEBAO;
    
    @Value("${HEAD.INTERFACE_CODE_PAY}")
    public String INTERFACE_CODE_PAY;
    
    @Value("${HEAD.INTERFACE_USER_CODE}")
    public String INTERFACE_USER_CODE;
    
    
    @Value("${HEAD.INTERFACE_PWD}")
    public String INTERFACE_PWD;     
    
    
    @Value("${HEAD.IFTEST}")
    public String IFTEST;        
    
    @Value("${BODY.platform}")
    public String platform; 
    
    @Value("${BODY.operType}")
    public String operType;
    
    @Value("${BODY.order_source}")
    public String order_source;
    
    @Value("${BODY.appnum}")
    public String appnum;
    
    @Value("${base_reltab}")
    public String base_reltab;
    
    @Value("${base_call_back_url}")
    public String call_back_url;
    
    @Value("${appl_reltab}")
    public String appl_reltab;
    
    @Value("${ins_reltab}")
    public String ins_reltab;
    
    @Value("${tgt_reltab}")
    public String tgt_reltab;    		
}
