package vn.metech.constant;

public enum RequestStatus {

    /**
     * Trang thai bat dau cua request, cho gui yeu cau
     */
    PENDING,

    /**
     * Trang thai da gui yeu cau toi mbf-communicate services
     */
    QUESTION_SENT,

    /**
     * Trang thai yeu cau da duoc gui sang mbf
     */
    QUESTION_ACCEPTED,

    /**
     * Trang thai yeu cau gui sang mbf nhung khong thanh cong
     */
    QUESTION_REJECTED,

    /**
     * Request da nhan duoc ket qua tu mbf
     */
    ANSWER_RECEIVED,
    ANSWER_SENT,
    PARAM_INVALID,
    TIMEOUT,

    /**
     * Trang thai khong gui yeu cau khi duplicate voi request da co ket qua
     */
    REQUEST_NOT_SEND,
    CLOSED

}
