package com.qubo.challenge.calc.logics;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.Paren;

/**
 * ������Ƃ��ė^����ꂽ�������A���u�L�@�̃g�[�N����ɐ؂蕪���邽�߂̃N���X
 * @author Qubo
 */
public class InfixNotationTokenizer {
	/** �G���[������F <code>"�F������Ȃ��g�[�N��[{0}]��������܂����I"</code> */
	public static final String ERROR_UNRECOGNIZED_TOKEN = "�F������Ȃ��g�[�N��[{0}]��������܂����I";
	/** �P�����Z�q�u�����v���o�̂��߁A���O�̃g�[�N����ۑ����Ă����I�u�W�F�N�g */
	private Object prevToken;

	/**
	 * �^����ꂽ�������A���u�L�@�̃g�[�N����ɐ؂蕪����
	 * @param input ����
	 * @return ���u�L�@�g�[�N����
	 * @throws InvalidFormulaException �F���ł��Ȃ��g�[�N�������������ꍇ�ɔ���
	 */
	public List<Object> tokenize(String input) throws InvalidFormulaException {
		input = putSpace(input.toLowerCase());
		prevToken = null;
		StringTokenizer stringTokenizer = new StringTokenizer(input);

		Stack<Object> stack = new Stack<Object>();
		while (stringTokenizer.hasMoreElements()) {
			stack.add(getToken(stringTokenizer.nextToken()));
		}

		return Collections.unmodifiableList(stack);
	}

	/**
	 * �������̍\���v�f�̊ԂɃX�y�[�X��}������
	 * @param input ����
	 * @return �X�y�[�X�ŋ�؂�ꂽ����
	 */
	private String putSpace(String input) {
		Pattern pattern = Pattern.compile("\\+|\\-|\\*|/|\\(|\\)|abs");
		Matcher matcher = pattern.matcher(input);
		String result = matcher.replaceAll(" $0 ");
		return result;
	}
	/**
	 * ������g�[�N�����A�K�؂ȃI�u�W�F�N�g�g�[�N���ɕϊ�����
	 * @param tokenString ������g�[�N��
	 * @return ������ɑΉ�����I�u�W�F�N�g
	 * @throws InvalidFormulaException �F���ł��Ȃ��g�[�N�������������ꍇ�ɔ���
	 */
	private Object getToken(String tokenString) throws InvalidFormulaException {
		Object token = doGetToken(tokenString);
		prevToken = token;
		return token;
	}
	/**
	 * ������g�[�N�����A�K�؂ȃI�u�W�F�N�g�g�[�N���ɕϊ�����i�����p�j
	 * @param tokenString ������g�[�N��
	 * @return
	 * @throws InvalidFormulaException �F���ł��Ȃ��g�[�N�������������ꍇ�ɔ���
	 */
	private Object doGetToken(String tokenString) throws InvalidFormulaException {
		if (Operator.SYMBOL_ABS.equals(tokenString)) {
			return Operator.Abs;
		} else if (tokenString.length() == 1) {
			switch (tokenString.charAt(0)) {
			case Operator.SYMBOL_ADD: return Operator.Add;
			case Operator.SYMBOL_SUB:
				if (prevToken == Paren.Right || prevToken instanceof Integer)
					return Operator.Sub;
				else
					return Operator.Neg;
			case Operator.SYMBOL_MUL: return Operator.Mul;
			case Operator.SYMBOL_DIV: return Operator.Div;
			case '(': return Paren.Left;
			case ')': return Paren.Right;
			}
		}
		try {
			return Integer.parseInt(tokenString);
		} catch (NumberFormatException e) {
			String message = MessageFormat.format(ERROR_UNRECOGNIZED_TOKEN, tokenString);
			throw new InvalidFormulaException(message, e);
		}
	}
}
