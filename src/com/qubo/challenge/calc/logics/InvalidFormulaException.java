package com.qubo.challenge.calc.logics;

/**
 * �����ɃG���[���������ꍇ�ɔ����B
 * @author Qubo
 */
public class InvalidFormulaException extends Exception {
	/** �V���A���o�[�W����UID�H */
	private static final long serialVersionUID = 1L;
	/** �W���̃R���X�g���N�^ */
	public InvalidFormulaException() { super(); }
	public InvalidFormulaException(String message, Throwable throwable) { super(message, throwable); }
	public InvalidFormulaException(String mesasge) { super(mesasge); }
	public InvalidFormulaException(Throwable throwable) { super(throwable); }
}
