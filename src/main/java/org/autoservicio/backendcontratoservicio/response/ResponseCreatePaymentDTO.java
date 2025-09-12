package org.autoservicio.backendcontratoservicio.response;

public class ResponseCreatePaymentDTO {  private String status;
    private AnswerDTO answer;
    private String ticket;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AnswerDTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDTO answer) {
        this.answer = answer;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public class AnswerDTO {
        private String formToken;
        private String errorCode;
        private String errorMessage;
        private String detailedErrorMessage;

        public String getFormToken() {
            return formToken;
        }

        public void setFormToken(String formToken) {
            this.formToken = formToken;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getDetailedErrorMessage() {
            return detailedErrorMessage;
        }

        public void setDetailedErrorMessage(String detailedErrorMessage) {
            this.detailedErrorMessage = detailedErrorMessage;
        }

    }
}
