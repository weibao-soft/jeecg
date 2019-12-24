//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package test;

import com.ishdr.pay.model.PayModel;
import com.ishdr.pay.model.RefundModel;
import com.ishdr.pay.utils.PayClient;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.junit.Test;

public class Demo {
    public Demo() {
    }

    @Test
    public void PayTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PayClient payClient = new PayClient("83JD89UER-WEIBAO", "EF6AD27F389B01A247CD5A12F8E08EA5", "EO6AD27F389X01A247CD5A12F8E021K1");
        PayModel payModel = new PayModel();
        payModel.setSubject("测试商品1");
        payModel.setPrice(120000);
        payModel.setPartnerCode("WEIBAO");
        String partnerTransCode = UUID.randomUUID().toString().replace("-", "");
        System.out.println(partnerTransCode);
        payModel.setPartnerTransCode(partnerTransCode);
        String payUrl = payClient.getPayUrl(payModel);
        RefundModel refundModel = new RefundModel();
        refundModel.setPartnerCode("WEIBAO");
        refundModel.setPartnerTransCode("85ea684a915f48358826f7f2b4a745d1");
        String refundUrl = payClient.getRefundUrl(refundModel);
        System.out.println(payUrl);
        System.out.println(refundUrl);
    }
    
    public static void main(String[] args) {
    	Demo demo = new Demo();
    	try {
			demo.PayTest();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
