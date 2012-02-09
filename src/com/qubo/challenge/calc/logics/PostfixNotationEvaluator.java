package com.qubo.challenge.calc.logics;

import java.text.MessageFormat;
import java.util.Stack;

import com.qubo.challenge.calc.tokens.BinaryOperator;
import com.qubo.challenge.calc.tokens.UnaryOperator;
import com.qubo.challenge.calc.tokens.Value;

/**
 * ��u�L�@�ŕ\�����������g�[�N�����]�����A{@link Value}�^�Ŗ߂��N���X
 * @author Qubo
 */
public class PostfixNotationEvaluator {
	private static final String ERROR_UNPROCESSED_VALUES = "���l��[{0}]�������ł��܂���ł����I";
	/** �I�y�����h��: <code>"�P�����Z�q�̃I�y�����h"</code> */
	public static final String UNARY_OPERAND = "�P�����Z�q[{0}]�̃I�y�����h";
	/** �I�y�����h��: <code>"�񍀉��Z�q�̍��I�y�����h"</code> */
	public static final String BINARY_OPERAND1 = "�񍀉��Z�q[{0}]�̍��I�y�����h";
	/** �I�y�����h��: <code>"�񍀉��Z�q�̉E�I�y�����h"</code> */
	public static final String BINARY_OPERAND2 = "�񍀉��Z�q[{0}]�̉E�I�y�����h";
	/** �G���[���b�Z�[�W: <code>"{0}������܂���I"</code> */
	public static final String ERROR_DEFICIENT_OPERAND = "{0}������܂���I";

	/**
	 * ��u�L�@�ŕ\�����������g�[�N�����]�����A{@link Value}�^�Ŗ߂�
	 * @param tokens ����
	 * @return �v�Z����
	 * @throws InvalidFormulaException �����ɃG���[������ꍇ�ɔ���
	 */
	public Value eval(Iterable<Object> tokens) throws InvalidFormulaException {
		Stack<Value> stack = new Stack<Value>();
		for (Object token : tokens) {
			if (token instanceof Integer) {
				stack.push(new Value((int) token));
			} else if (token instanceof UnaryOperator) {
				UnaryOperator unaryOperator = (UnaryOperator) token;
				checkOperandDeficiency(stack, MessageFormat.format(UNARY_OPERAND, unaryOperator));
				Value operand = stack.pop();
				Value result = unaryOperator.operate(operand);
				stack.push(result);
			} else if (token instanceof BinaryOperator) {
				BinaryOperator binaryOperator = (BinaryOperator) token;
				checkOperandDeficiency(stack, MessageFormat.format(BINARY_OPERAND1, binaryOperator));
				Value operand2 = stack.pop();
				checkOperandDeficiency(stack, MessageFormat.format(BINARY_OPERAND2, binaryOperator));
				Value operand1 = stack.pop();
				Value result = binaryOperator.operate(operand1, operand2);
				stack.push(result);
			} else {
				throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
			}
		}

		checkSingleValue(stack);

		return stack.pop();
	}

	/**
	 * ���Z�ɂ��A�X�^�b�N����{@link Value}��1�Ɏ������ꂽ���ǂ������`�F�b�N����B
	 * �X�^�b�N����{@link Value}��2�ȏ�c���Ă����ꍇ�͗�O����������B
	 * @param stack �ΏۃX�^�b�N
	 * @throws InvalidFormulaException �X�^�b�N����{@link Value}��2�ȏ�c���Ă����ꍇ�ɔ���
	 */
	private void checkSingleValue(Stack<Value> stack) throws InvalidFormulaException {
		if (stack.size() != 1) {
			StringBuilder builder = new StringBuilder();
			for (Value value : stack) {
				if (builder.length() > 0) builder.append(", ");
				builder.append(value);
			}
			String message = MessageFormat.format(ERROR_UNPROCESSED_VALUES, builder);
			throw new InvalidFormulaException(message);
		}
	}

	/**
	 * �I�y�����h���s�����Ă��Ȃ����`�F�b�N�B����Ȃ��ꍇ�͗�O������
	 * @param stack �I�y�����h�̓������X�^�b�N
	 * @param item �ΏۃI�y�����h��
	 * @throws InvalidFormulaException �I�y�����h������Ȃ��ꍇ�ɔ���
	 */
	private void checkOperandDeficiency(Stack<Value> stack, String item) throws InvalidFormulaException {
		if (stack.isEmpty()) throw new InvalidFormulaException(MessageFormat.format(ERROR_DEFICIENT_OPERAND, item));
	}
}
