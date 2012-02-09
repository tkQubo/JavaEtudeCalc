package com.qubo.challenge.calc.logics;

import java.util.Collections;
import java.util.Stack;

import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.Paren;
import com.qubo.challenge.calc.tokens.UnaryOperator;

/**
 * ���u�L�@���A��u�L�@�ɕϊ�����N���X
 * @author Qubo
 */
public class PostfixNotationConverter {
	/** �G���[���b�Z�[�W: <code>"�����ʂ��]���Ă��܂��I"</code> */
	public static final String ERROR_UNPROCESSED_LEFTPAREN = "�J�����ʂ��]���Ă��܂��I";
	/** �G���[���b�Z�[�W: <code>"�J�����ʂ��s�����Ă��܂��I"</code> */
	public static final String ERROR_DEFICIT_LEFTPAREN = "�J�����ʂ��s�����Ă��܂��I";

	/**
	 * {@link Iterable}{@code <}{@link Object}{@code >}�ŗ^����ꂽ���u�L�@�g�[�N������A��u�L�@�g�[�N����ɕϊ�����
	 * @param tokens ���u�L�@�ŕ\����������
	 * @return ��u�L�@�ŕ\����������
	 * @throws InvalidFormulaException �g�[�N�����s�����Ă���ꍇ�ɔ���
	 */
	public Iterable<Object> convert(Iterable<Object> tokens) throws InvalidFormulaException {
		// ��u�L�@�X�^�b�N
		Stack<Object> stackPn = new Stack<Object>();
		// ���Z�q�X�^�b�N
		Stack<Object> stackOp = new Stack<Object>();

		for (Object token : tokens) {
			if (token instanceof Integer) {
				stackPn.push(token);
			} else if (token == Paren.Left) {
				stackOp.push(token);
			} else if (token == Paren.Right) {
				checkEmpty(stackOp, ERROR_DEFICIT_LEFTPAREN);
				while (stackOp.peek() != Paren.Left) {
					stackPn.push(stackOp.pop());
					checkEmpty(stackOp, ERROR_DEFICIT_LEFTPAREN);
				}
				stackOp.pop();
			} else if (token instanceof UnaryOperator) {
				stackOp.push(token);
			} else if (token instanceof Operator) {
				consumeOperatorStack(stackPn, stackOp, (Operator) token);
			} else {
				throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
			}
		}

		while (!stackOp.empty()) {
			checkRightParen(stackOp);
			stackPn.push(stackOp.pop());
		}

		return Collections.unmodifiableList(stackPn);
	}

	/**
	 * �ΏۃX�^�b�N���̖������̊J�����ʂ��`�F�b�N����B�������̊J�����ʂ����݂����ꍇ�͗�O������
	 * @param stackOp �ΏۃX�^�b�N
	 * @throws InvalidFormulaException �������̊J�����ʂ����݂����ꍇ�ɔ���
	 */
	private void checkRightParen(Stack<Object> stackOp) throws InvalidFormulaException {
		if (stackOp.peek() == Paren.Left) throw new InvalidFormulaException(ERROR_UNPROCESSED_LEFTPAREN);
	}
	/**
	 * �ΏۃX�^�b�N����łȂ����`�F�b�N����B��̏ꍇ�͗�O�������B
	 * @param stackOp �ΏۃX�^�b�N
	 * @param message �G���[���b�Z�[�W
	 * @throws InvalidFormulaException �g�[�N�����s�����Ă���ꍇ�ɔ���
	 */
	private void checkEmpty(Stack<Object> stackOp, String message) throws InvalidFormulaException {
		if (stackOp.isEmpty()) throw new InvalidFormulaException(message);
	}
	/**
	 * �D�揇�ʂɊ�Â��āA���Z�q�X�^�b�N�ɂ���{@link Operator}�C���X�^���X����u�L�@�X�^�b�N�Ƀv�b�V������B
	 * @param stackPn ��u�L�@�X�^�b�N
	 * @param stackOp ���Z�q�X�^�b�N
	 * @param currentOperatorToken �V����{@link Operator}�C���X�^���X
	 */
	private void consumeOperatorStack(Stack<Object> stackPn, Stack<Object> stackOp, Operator currentOperatorToken) {
		while (!stackOp.empty() && stackOp.peek() instanceof Operator) {
			Operator stackTopOperatorToken = (Operator) stackOp.peek();
			if (currentOperatorToken.getPriority() <= stackTopOperatorToken.getPriority())
				stackPn.push(stackOp.pop());
			else
				break;
		}
		stackOp.push(currentOperatorToken);
	}
}
