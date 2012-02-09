package com.qubo.challenge.calc.tokens;

/**
 * �P�����Z�q��\������C���^�[�t�F�[�X
 * @author Qubo
 */
public interface UnaryOperator extends Operator {
	/**
	 * �P�����Z���s��
	 * @param operand �I�y�����h
	 * @return ���Z����
	 */
	Value operate(Value operand);
	/** ���� */
	static UnaryOperator Neg = new AbstractUnaryOperator(SYMBOL_NEG, PRIORITY_3) {
		@Override
		public Value operate(Value operand) {
			return new Value(-operand.getNumerator(), operand.getDenominator());
		}
	};
	/** ��Βl */
	static UnaryOperator Abs = new AbstractUnaryOperator(SYMBOL_ABS, PRIORITY_3) {
		@Override
		public Value operate(Value operand) {
			return new Value(Math.abs(operand.getNumerator()), operand.getDenominator());
		}
	};

	/**
	 * �����p�B{@link UnaryOperator}�������������ۃN���X�B
	 * @author Qubo
	 */
	abstract class AbstractUnaryOperator implements UnaryOperator {
		/** ���Z�q�̃V���{�� */
		private final String symbol;
		/** ���Z�̗D�揇�� */
		private final int priority;

		AbstractUnaryOperator(String symbol, int priority) {
			this.symbol = symbol;
			this.priority = priority;
		}
		/*
		 * (�� Javadoc)
		 * @see com.qubo.caea0121.calc.tokens.Operator#getPriority()
		 */
		@Override
		public int getPriority() { return priority; }
		/*
		 * (�� Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() { return symbol; }

	}
}
