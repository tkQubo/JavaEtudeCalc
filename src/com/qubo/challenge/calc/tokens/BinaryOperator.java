package com.qubo.challenge.calc.tokens;


/**
 * �񍀉��Z�q��\������C���^�[�t�F�[�X
 * @author Qubo
 */
public interface BinaryOperator extends Operator {
	/**
	 * �񍀉��Z���s��
	 * @param operand1 �I�y�����h�P
	 * @param operand2 �I�y�����h�Q
	 * @return ���Z����
	 */
	Value operate(Value operand1, Value operand2);

	/** ���Z */
	static BinaryOperator Add = new AbstractBinaryOperator(SYMBOL_ADD, PRIORITY_1) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			int lcmOfDenominator = Function.lcm(operand1.getDenominator(), operand2.getDenominator());
			int num1 = operand1.getNumerator() * lcmOfDenominator / operand1.getDenominator();
			int num2 = operand2.getNumerator() * lcmOfDenominator / operand2.getDenominator();
			return new Value(num1 + num2, lcmOfDenominator);
		}
	};
	/** ���Z */
	final static BinaryOperator Sub = new AbstractBinaryOperator(SYMBOL_SUB, PRIORITY_1) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			int lcmOfDenominator = Function.lcm(operand1.getDenominator(), operand2.getDenominator());
			int num1 = operand1.getNumerator() * lcmOfDenominator / operand1.getDenominator();
			int num2 = operand2.getNumerator() * lcmOfDenominator / operand2.getDenominator();
			return new Value(num1 - num2, lcmOfDenominator);
		}
	};
	/** ��Z */
	final static BinaryOperator Mul = new AbstractBinaryOperator(SYMBOL_MUL, PRIORITY_2) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			return new Value(operand1.getNumerator() * operand2.getNumerator(), operand1.getDenominator() * operand2.getDenominator());
		}
	};
	/** ���Z */
	final static BinaryOperator Div = new AbstractBinaryOperator(SYMBOL_DIV, PRIORITY_2) {
		@Override
		public Value operate(Value operand1, Value operand2) {
			return new Value(operand1.getNumerator() * operand2.getDenominator(), operand1.getDenominator() * operand2.getNumerator());
		}
	};

	/**
	 * �����p�B{@link BinaryOperator}�������������ۃN���X�B
	 * @author Qubo
	 */
	abstract class AbstractBinaryOperator implements BinaryOperator {
		/** ���Z�q�̃V���{�� */
		private final char symbol;
		/** ���Z�̗D�揇�� */
		private final int priority;

		AbstractBinaryOperator(char symbol, int priority) {
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
		public String toString() { return "" + symbol; }

	}
}
