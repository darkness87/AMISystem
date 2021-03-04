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
	public static final int FAIL = 999;
	public static final int NULL_EXCEPTION = 9999;

	// SYSTEM (0~199)
	public static final int SESSION_EXPIRED = 20;
	public static final int ACCESS_DENIED = 31;
	public static final int AUTHENTICATION_NEEDED = 32;
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

	// SXSSF (Excel)
	public static final int EXCEL_FILE_NOT_FOUND = 351;

	// NETWORK (5xx)
	public static final int UNKNOWN_HOST = 501;

	// COMMON (6xx)
	public static final int COMMON_JSON_PARSING_FAIL = 601;

	// LOGIC
	public static final int RSA_NO_SUCH_ALGORITHM = 1401;
	public static final int RSA_INVALID_KEY_SPEC = 1402;
	public static final int RSA_ENCRYPTION_ERROR = 1403;
	public static final int RSA_DECRYPTION_ERROR = 1404;

	// VALIDATION (15xx)
	public static final int VALIDATION_COMMON = 1500;
	public static final int VALIDATION_PARAMETER = 1501;
	public static final int VALIDATION_MEMBER = 1502;
	public static final int VALIDATION_DATE = 1503;
	public static final int VALIDATION_WORK_END = 1504;
	public static final int VALIDATION_LP_DAT = 1505;

	// DB (20xx)
	public static final int DB_COMMIT_FAILURE = 2000;
	public static final int TRANSACTION_WAS_NOT_STARTED = 2001;
	public static final int DB_NULL = 2002;
	public static final int DB_ERROR = 2099;

}