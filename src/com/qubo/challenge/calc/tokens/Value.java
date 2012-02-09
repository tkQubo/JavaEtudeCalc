package com.qubo.challenge.calc.tokens;


/**
 * �L������\�������N���X�B
 * @author Qubo
 */
public class Value {
	/** ���ꂪ0�ɂȂ����Ƃ��ɔ�������{@link ArithmeticException}�̃��b�Z�[�W���e */
	public static final String EXCEPTION_DIVIDED_BY_ZERO = "0�ŏ��Z���܂����I";
	/** ���� */
	private int denominator;
	/** ���q */
	private int numerator;

	/**
	 * ������擾����
	 * @return ����
	 */
	public int getDenominator() { return denominator; }
	/**
	 * �����ݒ肷��
	 * @param denominator �ݒ肷��l
	 */
	public void setDenominator(int denominator) {
		if (denominator == 0) throw new ArithmeticException(EXCEPTION_DIVIDED_BY_ZERO);
		this.denominator = denominator;
		reduce();
	}
	/**
	 * ���q���擾����
	 * @return ���q
	 */
	public int getNumerator() { return numerator; }
	/**
	 * ���q��ݒ肷��
	 * @param numerator �ݒ肷��l
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
		reduce();
	}

	/**
	 * ���ꂪ1�̐����𐶐�����
	 * @param value ����
	 */
	public Value(int value) { this(value, 1); }
	/**
	 * �W���̃R���X�g���N�^
	 * @param numerator ���q
	 * @param denominator ����
	 */
	public Value(int numerator, int denominator) {
		if (denominator == 0) throw new ArithmeticException(EXCEPTION_DIVIDED_BY_ZERO);
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
	}

	/** �񕪂��s�� */
	private void reduce() {
		if (denominator < 0) {
			denominator = -denominator;
			numerator = -numerator;
		}
		int gcd = Function.gcd(numerator, denominator);
		if (gcd != 1) {
			numerator /= gcd;
			denominator /= gcd;
		}
	}

	/**
	 * �l�������Ŏ擾����
	 * @return ���l�̎����\��
	 */
	public double getRealValue() {
		return (double) numerator / denominator;
	}

	/**
	 * �l�𕪐��\���̕�����ŕ\������
	 */
	@Override
	public String toString() {
		return numerator + (denominator != 1 ? "/" + denominator : "");
	}
	/*
	 * (�� Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Value) {
			Value other = (Value) obj;
			return this.numerator == other.numerator && this.denominator == other.denominator;
		} else if (obj instanceof Integer) {
			Integer other = (Integer) obj;
			return this.denominator == 1 && this.numerator == other;
		} else if (obj instanceof Double) {
			Double other = (Double) obj;
			return this.getRealValue() == other;
		}
		return false;
	}
}
