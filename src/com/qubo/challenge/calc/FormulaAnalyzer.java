package com.qubo.challenge.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.BinaryOperator;
import com.qubo.challenge.calc.tokens.Operator;
import com.qubo.challenge.calc.tokens.UnaryOperator;

/**
 * �������A���Z�̗D�揇�ʂɊ�Â��ĊK�w�\���ŕ\�����邽�߂̃N���X
 * @author Qubo
 */
public class FormulaAnalyzer {
	/**
	 * �������A���Z�̗D�揇�ʂɊ�Â��ĊK�w�\���ŕ\��
	 * @param input
	 * @throws InvalidFormulaException �����ɃG���[���������ꍇ�ɔ���
	 */
	public void printStructure(String input) throws InvalidFormulaException {
		String[] lines = getStructure(input);
		System.out.println("�����\���F");
		System.out.println("��" + pad(lines[0].length() + 2, '-') + "��");
		for (String line : lines) {
			System.out.println("�� " + line + " ��");
		}
		System.out.println("��" + pad(lines[0].length() + 2, '-') + "��");
	}
	/**
	 * �������A���Z�̗D�揇�ʂɊ�Â��ĊK�w�\���ɂ������̂�{@link List}{@code <}{@link String}{@code >}�Ƃ��Ď擾����
	 * @param input
	 * @return �K�w�\�������ꂽ����
	 * @throws InvalidFormulaException �����ɃG���[���������ꍇ�ɔ���
	 */
	public String[] getStructure(String input) throws InvalidFormulaException {
		LeveledToken value = getLeveledValue(input);
		List<String> lines = new ArrayList<String>();
		for (int i = 0; i <= value.level(); i++) {
			lines.add(value.getLevelString(i));
		}
		return lines.toArray(new String[0]);
	}
	/**
	 * �^����ꂽ���������ɁA�K�w���x���t�̐����g�[�N���𐶐���������p���\�b�h
	 * @param input ����
	 * @return {@link LeveledToken}�I�u�W�F�N�g
	 * @throws InvalidFormulaException �����ɃG���[���������ꍇ�ɔ���
	 */
	private LeveledToken getLeveledValue(String input) throws InvalidFormulaException {
		Calculator calc = new Calculator();
		calc.eval(input);

		Stack<LeveledToken> stack = new Stack<LeveledToken>();
		Iterable<Object> tokens = calc.getPostfixNotationTokens();
		for (Object token : tokens) {
			if (token instanceof Integer) {
				stack.push(new LeveledToken(token));
			} else if (token instanceof UnaryOperator) {
				UnaryOperator unaryOperator = (UnaryOperator) token;
				LeveledToken operand = stack.pop();
				LeveledToken child = LeveledToken.combine(null, unaryOperator, operand);
				stack.push(child);
			} else if (token instanceof BinaryOperator) {
				BinaryOperator binaryOperator = (BinaryOperator) token;
				LeveledToken operand2 = stack.pop();
				LeveledToken operand1 = stack.pop();
				LeveledToken child = LeveledToken.combine(operand1, binaryOperator, operand2);
				stack.push(child);
			} else {
				throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
			}
		}

		return (LeveledToken) stack.pop();
	}
	/**
	 * �K�w���x���t�̐����g�[�N���B
	 * @author Qubo
	 */
	static class LeveledToken {
		/** �����g�[�N�� */
		Object[] tokens;
		/**
		 * �R���X�g���N�^
		 * @param tokens �����g�[�N���̔z��
		 */
		LeveledToken(Object... tokens) { this.tokens = tokens; }
		/**
		 * {@link LeveledToken}�I�u�W�F�N�g�Ƃ��Ă̔퉉�Z�q�Ɖ��Z�q���������A�V����{@link LeveledToken}�I�u�W�F�N�g�𐶐�����B
		 * @param operand1 �퉉�Z�q�P
		 * @param operator ���Z�q
		 * @param operand2 �퉉�Z�q�Q
		 * @return �V���ɐ������ꂽ{@link LeveledToken}�I�u�W�F�N�g
		 */
		static LeveledToken combine(LeveledToken operand1, Operator operator, LeveledToken operand2) {
			List<Object> list = new ArrayList<Object>();
			if (operand1 != null) {
				if (operand1.isOperatorPriority(operator.getPriority())) {
					for (Object token : operand1.tokens) {
						list.add(token);
					}
				} else {
					list.add(operand1);
				}
			}
			list.add(operator);
			if (operand2 != null) {
				if (operand2.isOperatorPriority(operator.getPriority())) {
					for (Object token : operand2.tokens) {
						list.add(token);
					}
				} else {
					list.add(operand2);
				}
			}
			return new LeveledToken(list.toArray());
		}


		/**
		 * �����̕�����Ƃ��Ă̒������擾����B
		 * @return �����̕�����Ƃ��Ă̒���
		 */
		int length() {
			int total = 1;
			for (Object token : tokens) {
				if (token instanceof Integer) {
					total += token.toString().length();
				} else if (token instanceof Operator) {
					total += token.toString().length();
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (isInteger(child)) {
						total += child.tokens[0].toString().length();
					} else {
						total += child.length();
					}
				} else {
					throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
				}
			}
			total += tokens.length - 1;
			return total;
		}
		private boolean isInteger(LeveledToken token) {
			return token.tokens.length == 1 && token.tokens[0] instanceof Integer;
		}
		/**
		 * �����̃��x�����擾����
		 * @return �����̃��x��
		 */
		int level() {
			int level = 0;
			for (Object token : tokens) {
				if (token instanceof Integer) {
				} else if (token instanceof Operator) {
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (!isInteger(child)) {
						level = Math.max(level, child.level() + 1);
					}
				} else {
					throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
				}
			}
			return level;
		}
		/**
		 * �w�肵�����x���ɑ��݂��鐔���g�[�N���𕶎���Ƃ��Ď擾����B
		 * @param level
		 * @return
		 */
		String getLevelString(int level) {
			StringBuilder builder = new StringBuilder();
			for (Object token : tokens) {
				if (token instanceof Integer || token instanceof Operator) {
					if (token instanceof BinaryOperator)
						builder.append(this.level() < level ? '_' : ' ');

					if (this.level() == level) {
						builder.append(token);
					} else if (this.level() < level) {
						builder.append(pad(token, '_'));
					} else {
						builder.append(pad(token, ' '));
					}

					if (token instanceof Operator)
						builder.append(this.level() < level ? '_' : ' ');
				} else if (token instanceof LeveledToken) {
					LeveledToken child = (LeveledToken) token;
					if (isInteger(child)) {
						Object integer = child.tokens[0];
						if (this.level() == level) {
							builder.append(integer);
						} else if (this.level() < level) {
							builder.append(pad(integer, '_'));
						} else {
							builder.append(pad(integer, ' '));
						}
					} else {
						builder.append(child.getLevelString(level));
					}
				} else {
					throw new UnsupportedOperationException(token + "�͏����ł��܂���I");
				}
			}
			return builder.toString();
		}
		/**
		 * �����g�[�N����Ɋ܂܂�Ă��鉉�Z�q���A�w�肳�ꂽ�l�Ɠ������ǂ������`�F�b�N����B
		 * �g�[�N����ɉ��Z�q���܂܂�Ȃ��i�܂萔�l�̂݁j�̏ꍇ�́A{@code true}��Ԃ��B
		 * @param priority ���Z�q�̗D�揇��
		 * @return �`�F�b�N���ꂽ�l
		 */
		boolean isOperatorPriority(int priority) {
			for (Object token : tokens) {
				if (token instanceof Operator) {
					Operator operator = (Operator) token;
					return operator.getPriority() == priority;
				}
			}
			return true;
		}
	}
	/**
	 * �g�[�N���̕����񒷂ɑ�������p�f�B���O�������Ԃ�
	 * @param token �g�[�N��
	 * @param padding �p�f�B���O
	 * @return �p�f�B���O������
	 */
	private static String pad(Object token, char padding) {
		return pad(token.toString().length(), padding);
	}
	/**
	 * �w�肵�������̃p�f�B���O�������Ԃ�
	 * @param length ����
	 * @param padding �p�f�B���O
	 * @return �p�f�B���O������
	 */
	private static String pad(int length, char padding) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(padding);
		}
		return builder.toString();
	}
}
