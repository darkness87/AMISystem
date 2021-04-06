package com.cnu.ami.common;

/**
 * Exception 코드
 * 
 * @author sookwon
 *
 */
public class ExceptionConst {
	// STATUS
	public static final int SUCCESS = 0;
	public static final int FAIL = 99;
	public static final int NULL_EXCEPTION = 999;

	// SYSTEM (1xx)
	public static final int SESSION_EXPIRED = 120;
	public static final int ACCESS_DENIED = 131;
	public static final int AUTHENTICATION_NEEDED = 132;
	public static final int INVALID_LOGINID = 101;
	public static final int INVALID_PASSWORD = 102;
	public static final int UNREGISTERED_USER = 103;
	public static final int UNREGISTERED_IP = 104;
	public static final int LOCKED_USER = 105;
	public static final int SERVICE_EXPIRED = 106;
	public static final int DUPLICATE_USER = 107;

	// AUTH (2xx)
	public static final int CANNOT_FOUND_PRIVATE_KEY = 200;
	public static final int NOT_SYSTEM_ADMIN = 201;

	// FILE (3xx)
	public static final int FILE_MOVE_FAILURE = 300;
	public static final int FILE_NOT_EXISTS = 301;
	public static final int FILE_NOT_ACCESS = 302;
	public static final int PATH_NOT_DIRECTORY = 303;
	public static final int DELETE_FILE_FAILED = 304;
	public static final int PROPERTIES_READ_FAIL = 305;
	public static final int PROPERTIES_WRITE_FAIL = 306;

	// NETWORK (5xx)
	public static final int UNKNOWN_HOST = 501;

	// COMMON (6xx)
	public static final int COMMON_JSON_PARSING_FAIL = 601;

	// 중복 (7xx)
	public static final int DUPLICATION = 701;

	// LOGIC (8xx)
	public static final int RSA_NO_SUCH_ALGORITHM = 801;
	public static final int RSA_INVALID_KEY_SPEC = 802;
	public static final int RSA_ENCRYPTION_ERROR = 803;
	public static final int RSA_DECRYPTION_ERROR = 804;

	// VALIDATION (11xx)
	public static final int VALIDATION_COMMON = 1100;
	public static final int VALIDATION_PARAMETER = 1101;
	public static final int VALIDATION_MEMBER = 1102;
	public static final int VALIDATION_DATE = 1103;
	public static final int VALIDATION_WORK_END = 1104;
	public static final int VALIDATION_LP_DAT = 1105;

	// DB (12xx)
	public static final int DB_COMMIT_FAILURE = 1200;
	public static final int TRANSACTION_WAS_NOT_STARTED = 1201;
	public static final int DB_NULL = 1202;
	public static final int DB_ERROR = 1299;

}