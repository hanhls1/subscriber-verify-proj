package vn.metech.constant;

public enum RecordStatus {
	/**
	 * Begin status
	 * List pending records will be send to partner
	 */
	PENDING,
	/**
	 * Question sent
	 * Can fetch result after that
	 */
	ACCEPTED,
	/**
	 * Question sent
	 * Partner reject question
	 * Cannot fetch result after that
	 */
	REJECTED,
	/**
	 * Ready to fetch result
	 * Ready to update Request
	 */
	READY_FETCH,
	/**
	 * Fetched answer from partner
	 */
	HAVE_ANSWER,
	/**
	 * Fetched answer from partner but no answer
	 */
	NO_ANSWER,
	/**
	 * Closed with a success
	 * Answer replied
	 */
	SUCCESS_CLOSED,
	/**
	 * Closed with an error
	 */
	ERROR_CLOSED;
}
