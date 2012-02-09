package com.qubo.challenge.calc.tokens;

/**
 * ���Z�q��\������C���^�[�t�F�[�X
 * @author Qubo
 */
public interface Operator {
	/** ���Z�̕�����\��: {@code "+"} */
	public static final char SYMBOL_ADD = '+';
	/** ���Z�̕�����\��: {@code "-"} */
	public static final char SYMBOL_SUB = '-';
	/** ��Z�̕�����\��: {@code "*"} */
	public static final char SYMBOL_MUL = '*';
	/** ���Z�̕�����\��: {@code "/"} */
	public static final char SYMBOL_DIV = '/';
	/** �����̕�����\��: {@code "neg"} */
	public static final String SYMBOL_NEG = "neg";
	/** ��Βl�̕�����\��: {@code "abs"} */
	public static final String SYMBOL_ABS = "abs";

	/** ���Z */
	public static final BinaryOperator Add = BinaryOperator.Add;
	/** ���Z */
	public static final BinaryOperator Sub = BinaryOperator.Sub;
	/** ��Z */
	public static final BinaryOperator Mul = BinaryOperator.Mul;
	/** ���Z */
	public static final BinaryOperator Div = BinaryOperator.Div;
	/** ���� */
	public static final UnaryOperator Neg = UnaryOperator.Neg;
	/** ��Βl */
	public static final UnaryOperator Abs = UnaryOperator.Abs;

	/** ���Z�̗D�揇�ʂP�i���Z�A���Z�j */
	final int PRIORITY_1 = 1;
	/** ���Z�̗D�揇�ʂQ�i��Z�A���Z�j */
	final int PRIORITY_2 = 2;
	/** ���Z�̗D�揇�ʂR�i�����A��Βl�j */
	final int PRIORITY_3 = 3;
	/**
	 * ���Z�q�̗D�揇�ʂ�Ԃ�
	 * @return ���Z�q�̗D�揇��
	 */
	public abstract int getPriority();
}