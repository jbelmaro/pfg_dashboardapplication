package es.tid.bluevia.m2mservice.api;

/**
 * 
 * @author agc327
 * 
 */
public class Exception {
    private String exceptionId;
    private String exceptionText;
    private String moreInfo;
    private String userMessage;

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getExceptionText() {
        return exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((exceptionId == null) ? 0 : exceptionId.hashCode());
        result = prime * result
            + ((exceptionText == null) ? 0 : exceptionText.hashCode());
        result = prime * result
            + ((moreInfo == null) ? 0 : moreInfo.hashCode());
        result = prime * result
            + ((userMessage == null) ? 0 : userMessage.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Exception other = (Exception) obj;
        if (exceptionId == null) {
            if (other.exceptionId != null) {
                return false;
            }
        } else if (!exceptionId.equals(other.exceptionId)) {
            return false;
        }
        if (exceptionText == null) {
            if (other.exceptionText != null) {
                return false;
            }
        } else if (!exceptionText.equals(other.exceptionText)) {
            return false;
        }
        if (moreInfo == null) {
            if (other.moreInfo != null) {
                return false;
            }
        } else if (!moreInfo.equals(other.moreInfo)) {
            return false;
        }
        if (userMessage == null) {
            if (other.userMessage != null) {
                return false;
            }
        } else if (!userMessage.equals(other.userMessage)) {
            return false;
        }
        return true;
    }

}
