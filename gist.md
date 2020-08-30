# GIST

```java
String agent = request.getHeader("USER-AGENT").toLowerCase();
if (agent.indexOf("msie ") > 0 || agent.indexOf("rv:11") > 0) {
    // IE
    fileName = URLEncoder.encode(fileName, "UTF-8");
} else if (agent.indexOf("webkit") > 0 && (agent.indexOf("edg/") > 0 || agent.indexOf("edge/") > 0)) {
    // Edge
    fileName = URLEncoder.encode(fileName, "UTF-8");
} else {
    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
}
```
