
package co.diro.wing.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	
	public static long generateKey(){
		long prefix = Calendar.getInstance().getTimeInMillis();
		String key = String.format("%d%03d", prefix, 1);
		return Long.parseLong(key);
	}
	public static List<Long> generateKeys(int count){
		List<Long> keyList = new ArrayList<>();
		long prefix = Calendar.getInstance().getTimeInMillis();
		
		for(int suffix=1; suffix<count+1; suffix++) {
			String key = String.format("%d%03d", prefix, suffix);
			keyList.add(Long.parseLong(key));
		}
		return keyList;
	}
	
	
	
	public String getJarPath() {
	    File JarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	    String JarFilePath = JarFile.getAbsolutePath();
	    String result = JarFilePath.replace(JarFile.getName(), "");
	    return result.toString();
	  }

	  @SuppressWarnings("rawtypes")
	  public static JSONArray runIt(String clsNm, JSONObject ConfItem, JSONObject templateItem, byte[] message) {
	    JSONArray rtn = null;
	    try {
	      Class<?> clz = Class.forName(clsNm);
	      Object instanceObject = clz.newInstance();
	      Class[] methodParamClass = {JSONObject.class, JSONObject.class, byte[].class};
	      Object[] methodParamObject = {ConfItem, templateItem, message};
	      Method mth = clz.getDeclaredMethod("doit", methodParamClass );
	      rtn = (JSONArray) mth.invoke(instanceObject, methodParamObject );
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rtn;
	  }
	  @SuppressWarnings("rawtypes")
	  public static JSONArray runIt(String clsNm, JSONObject ConfItem, JSONObject templateItem) {
	    JSONArray rtn = null;
	    try {
	      Class<?> clz = Class.forName(clsNm);
	      Object instanceObject = clz.newInstance();
	      Class[] methodParamClass = {JSONObject.class, JSONObject.class};
	      Object[] methodParamObject = {ConfItem, templateItem};
	      Method mth = clz.getDeclaredMethod("doit", methodParamClass );
	      rtn = (JSONArray) mth.invoke(instanceObject, methodParamObject );
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rtn;
	  }


	  public static Object getData(JSONObject svc) throws Exception {
	    Object obj = null;
	    String url = urlAssemble(svc.getString("url_addr"), svc);

	    HttpResponse resp = OkUrlUtil.get(url, "Content-type", "application/json");
	    String payload = resp.getPayload();
	    if (resp.getStatusCode() >= 200 && resp.getStatusCode() < 301 ) {
	      if (payload.startsWith("{")) {
	        obj = new JSONObject(resp.getPayload());
	      } else if (payload.startsWith("[")) {
	        obj = new JSONArray(resp.getPayload());
	      } else if (payload.toLowerCase().startsWith("<")) {
	        obj = XML.toJSONObject(resp.getPayload());
	      } else {
	        obj = resp.getPayload();
	      }
	    }  else {
	      throw new Exception(resp.getStatusName());
	    }
	    return obj;
	  }

	  /**
	   * @param url
	   * @param svc
	   * @return
	 * @throws JSONException 
	   */
	  private static String urlAssemble(String url, JSONObject svc) throws JSONException {
	    if (svc.has("key_name") && svc.has("key_value")) {
	      url += "&" + svc.getString("key_name") + "=" + svc.getString("key_value");
	    }

	    if (svc.has("PathVariable")) {
	      String[] sp = svc.getString("PathVariable").split(",");
	      for (String it : sp) {
	        if (!"".equals(it) && svc.has(it)) {
	          String value = svc.getString(it);

	          if (value.indexOf(",") > -1) {
	            String[] val = value.split(",");
	            if (DateUtil.isPatternDate(val[0])) {
	              url += DateUtil.addDate(DateUtil.getChronoUnit(val[1]), Integer.parseInt(val[2]), val[0]) + "/";
	            }

	          } else {
	            if (DateUtil.isPatternDate(value)) {
	              url += DateUtil.getDate(value) + "/";
	            } else if (!"".equals(value)) {
	              url += value + "/";
	            }
	          }
	        }
	      }
	    }

	    if (svc.has("ParamVariable")) {

	      String[] sp = svc.getString("ParamVariable").split(",");
	      String value = "";

	      for (String it : sp) {
	        if (!"".equals(it) && svc.has(it)) {
	          value = svc.getString(it) + "";
	          if (value.indexOf(",") > -1) {
	            String[] val = value.split(",");
	            if (DateUtil.isPatternDate(val[0])) {
	              url += "&" + it + "=" + DateUtil.addDate(DateUtil.getChronoUnit(val[1]), Integer.parseInt(val[2]), val[0]);
	            }

	          } else {
	            if (DateUtil.isPatternDate(value)) {
	              url += "&" + it + "=" + DateUtil.getDate(value);
	            } else {
	              url += "&" + it + "=" + value;
	            }

	          }
	        } else {
	          if (svc.has("key")) {
	            url += "&" + it + "=" + svc.getString("key");
	          }
	        }
	      }
	    }
	    return url;
	  }

	  /**
	   * @param prefix
	   * @param localname
	   * @param filter
	   * @param svc
	   * @return
	   */
	  public static List<Map<String, String>> getSoapData(String prefix, String localname, String filter, JSONObject svc) {
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    try {
	      SOAPMessage msg = createSoapMessage(prefix, localname, svc.getString("ParamVariable").split(","), svc);
	      list = toList(callSoap(svc.getString("url_addr"), msg, filter));
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return list;
	  }

	  /**
	   * @param soapUrl
	   * @param soapMessage
	   * @param filter
	   * @return NodeList
	   * @throws Exception
	   */
	  public static NodeList callSoap(String soapUrl, SOAPMessage soapMessage, String filter) throws Exception {
	    //SOAPConnection create instance
	    SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
	    SOAPConnection connection = scf.createConnection();

	    //SOAP SEND MESSAGE
	    SOAPMessage resSoapMsg = connection.call(soapMessage, soapUrl);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    resSoapMsg.writeTo(out);
	    String soapResult = new String(out.toByteArray(), "UTF-8");

	    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(soapResult)));
	    XPath xpath = XPathFactory.newInstance().newXPath();
	    NodeList nodeList = (NodeList) xpath.evaluate("//" + filter, doc, XPathConstants.NODESET);
	    return nodeList;
	  }

	  /**
	   * @param child
	   * @return List
	   */
	  public static  List<Map<String, String>> toList(NodeList child) {
	    List<Map<String, String>> arr = new ArrayList<Map<String, String>>();
	    for (int i = 0; i < child.getLength(); i++) {
	      NodeList loopchild = child.item(i).getChildNodes();

	      Map<String, String> c = new HashMap<String, String>();
	      for (int j = 0; j < loopchild.getLength(); j++) {
	        Node node = loopchild.item(j);
	        c.put(node.getNodeName(), node.getTextContent());
	      }
	      arr.add(c);
	    }
	    if (log.isTraceEnabled()) {
	      for (Map<String, String> item : arr) {
	        for (Map.Entry<String, String> key : item.entrySet()) {
	          log.debug(key.getKey() + ":" + key.getValue());
	        }
	      }
	    }

	    return arr;
	  }

	  /**
	   * @param prefix
	   * @param localname
	   * @param sp
	   * @param svc
	   * @return SOAPMessage
	   * @throws Exception
	   */
	  public static SOAPMessage createSoapMessage(String prefix, String localname, String[] sp, JSONObject svc) throws Exception {
	    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
	    SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
	    envelope.addNamespaceDeclaration("ws", "http://ws.api.javaweb/");
	    SOAPBody soapBody = envelope.getBody();
	    SOAPElement child = soapBody.addChildElement(localname, prefix);
	    for (String it : sp) {
	      if (!"".equals(it) && svc.has(it)) {
	        String value = svc.getString(it);
	        child.addChildElement(it).addTextNode(value);
	      }
	    }

	    return soapMessage;
	  }
}
