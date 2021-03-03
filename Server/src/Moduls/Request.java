package Moduls;

import java.io.Serializable;

public class Request implements Serializable {
    private final static long serialVersionUID = 1L;
    private RequestType requestType;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
