package com.qubo.challenge.calc.tokens;

/**
 * ���w�֗̕��֐������߂��N���X
 * @author Qubo
 */
public class Function {
	/** ���̃R���X�g���N�^�͎g��Ȃ��� */
	private Function() { throw new RuntimeException("���̃N���X�̓C���X�^���X���ł��܂���I"); }

	/**
	 * ��̐����̍ő���񐔂����߂�
	 * @param a �����P
	 * @param b �����Q
	 * @return �ő����
	 */
	public static int gcd(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		int r = a % b;
		return r == 0 ? b : gcd(b, r);
	}
	/**
	 * ��̐����̍ŏ����{�������߂�
	 * @param a �����P
	 * @param b �����Q
	 * @return �ŏ����{��
	 */
	public static int lcm(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		return a * b / gcd(a, b);
	}
}
