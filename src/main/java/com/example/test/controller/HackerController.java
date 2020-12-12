package com.example.test.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hacker")
public class HackerController {


	@PostMapping("/hacked")
	public String hackedFunction(@RequestBody String body) {
		return "This is hacked";
	}
	
	@PostMapping("/testJson")
	public String hackedFunction2() {
		String plainString = "{\"status\":\"00\",\"linkAjaRefnum\":\"7KP70AFYUD\",\"trxDate\":\"1606295215349000\",\"partnerTrxID\":\"BI2011250003\",\"merchantID\":\"quity_app3\",\"terminalID\":\"ELI\",\"terminalName\":\"Equity Life Indonesia\",\"totalAmount\":\"32000\",\"partnerApplink\":\"https://eli-sit.myequity.id/linkaja/payment/finish?regCode=UkVHMjAxMTI1MDAwMg==\",\"items\":[{\"id\":\"R2010002\",\"name\":\"LinkAja Covid Three Month Plan\",\"unitPrice\":\"32000\",\"qty\":\"1\"}],\"refData\":[{\"key\":\"key\",\"value\":\"val\"}]}";
    	JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(plainString);
		    System.out.println(jsonObj);
		    System.out.println(jsonObj.get("status12"));
		    System.out.println(jsonObj.get("partnerTrxID"));

		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	    return "";
	}

}
