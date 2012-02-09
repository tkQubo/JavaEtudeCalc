package com.qubo.challenge.calc.tokens;

/**
 * ���ʂ�\������N���X
 * @author Qubo
 */
public class Paren {
	/** �J�����ʂ̕�����\��: {@code "("} */
	public static final char SYMBOL_PAREN_LEFT = '(';
	/** �����ʂ̕����\��: {@code ")"} */
	public static final char SYMBOL_PAREN_RIGHT = ')';

	/** �V���{�� */
	private final char symbol;
	private Paren(char symbol) { this.symbol = symbol; }
	@Override public String toString() { return "" + symbol; }

	/** �J������: {@code "("} */
	public static Paren Left = new Paren(SYMBOL_PAREN_LEFT);
	/** ������: {@code ")"} */
	public static Paren Right = new Paren(SYMBOL_PAREN_RIGHT);
}
