package com.qubo.challenge.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.Value;

/**
 * �R���\�[���v���O�����̃G���g���|�C���g����`���ꂽ�N���X
 * @author Qubo
 */
public class Main {
	/** �����o�͗p�̃I�v�V����: {@code "-d"} */
	private static final String OPTION_REAL_VALUE = "-d";
	/** �v�Z�ߒ��o�͗p�̃I�v�V����: {@code "-s"} */
	private static final String OPTION_SHOW_FORMULA = "-s";

	/** {@link Calculator}�C���X�^���X */
	private static Calculator calculator = new Calculator();
	/** {@link FormulaAnalyzer}�C���X�^���X */
	private static FormulaAnalyzer analyzer = new FormulaAnalyzer();
	/** �����o�͂��L�����ǂ��� */
	private static boolean realValueRequired;
	/** �v�Z�ߒ��o�͂��L�����ǂ��� */
	private static boolean showFormula;

	/**
	 * <p>
	 * �R���\�[���v���O�����̃G���g���|�C���g�B
	 * �����Ƃ���
	 * <ul>
	 * <li>����</li>
	 * <li>�����\���I�v�V����({@code "-d"})</li>
	 * <li>�v�Z�ߒ��o�̓I�v�V����({@code "-s"})</li>
	 * </ul>
	 * �̂R����邱�Ƃ��ł���B
	 * <br />
	 * �N���R�}���h��F<br />
	 * <code>
	 * java com.qubo.caea0121.calc.Main "1 + 4 * -5"<br />
	 * java com.qubo.caea0121.calc.Main 3-5/2+8 -d -s<br />
	 * java com.qubo.caea0121.calc.Main 3*-5+(-7*4) -s<br />
	 * </code>
	 * </p>
	 * <p>
	 * �����ɐ������܂߂Ȃ������ꍇ�A�R���\�[�����琔���̓��͂����߂�u�A�����s���[�h�v�ŋN������B
	 * ���̃��[�h�ł́A�I�v�V�����w�肪�Ȃ��ꍇ�A�u�����\���I�v�V�����Ȃ��v�u�v�Z�ߒ��o�̓I�v�V��������v�̏�ԂŋN������B
	 * �܂��A�������͂���Enter�L�[�������ďI������܂ŁA���x���v�Z�����s���邱�Ƃ��ł���B
	 * </p>
	 * @param args �R�}���h���C������
	 */
	public static void main(String[] args) {
		realValueRequired = isRealValueRequired(args);
		showFormula = showFormula(args);
		String formula = getFormula(args);
		if (formula != null) {
			doCalculate(formula);
		} else {
			if (args.length == 0) {
				showFormula = true;
			}
			while ((formula = requestInput()).length() > 0) {
				doCalculate(formula);
			}
		}
	}
	/**
	 * �^����ꂽ�����̌v�Z���s��
	 * @param formula ����
	 */
	private static void doCalculate(String formula) {
		try {
			Value value = calculator.eval(formula);
			if (showFormula) {
				print("���u�L�@", calculator.getInfixNotationTokens());
				print("��u�L�@", calculator.getPostfixNotationTokens());
				analyzer.printStructure(formula);
				System.out.print("�v�Z���ʁF");
			}
			System.out.println(realValueRequired ? value.getRealValue() : value);
		} catch (InvalidFormulaException e) {
			System.out.println(e.getMessage());
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * �R�}���h���C����������A�������擾����B�������w�肳��Ă��Ȃ��ꍇ��{@code null}��Ԃ��B
	 * @param args �R�}���h���C������
	 * @return ����
	 */
	private static String getFormula(String[] args) {
		for (String string : args)
			if (!OPTION_REAL_VALUE.equals(string) && !OPTION_SHOW_FORMULA.equals(string))
				return string;
		return null;
	}
	/**
	 * �R�}���h���C����������A�����o�͂�L���ɂ��邩�ǂ����̃I�v�V����({@link #OPTION_REAL_VALUE})�w����擾����
	 * @param args �R�}���h���C������
	 * @return �I�v�V�������L�����ǂ���
	 */
	private static boolean isRealValueRequired(String[] args) {
		for (String string : args)
			if (OPTION_REAL_VALUE.equals(string))
				return true;
		return false;
	}
	/**
	 * �R�}���h���C����������A�v�Z�ߒ��o�͂�L���ɂ��邩�ǂ����̃I�v�V����({@link #OPTION_SHOW_FORMULA})�w����擾����
	 * @param args �R�}���h���C������
	 * @return �I�v�V�������L�����ǂ���
	 */
	private static boolean showFormula(String[] args) {
		for (String string : args)
			if (OPTION_SHOW_FORMULA.equals(string))
				return true;
		return false;
	}
	/**
	 * ���[�U�[�̐������͂����߂�B�������͂��Ȃ������ꍇ�A�����ŏ������I������B
	 * @return ����
	 */
	private static String requestInput() {
		System.out.println("��������͂��ĉ������B�������͂��Ȃ��ꍇ�́A���̂܂܏I�����܂��B");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				return reader.readLine();
			} catch (IOException e) { }
		}
	}
	/**
	 * �g�[�N������X�y�[�X��؂�ŁA��s�ŏo�͂���
	 * @param title �^�C�g��
	 * @param tokens �g�[�N����
	 */
	private static void print(String title, Iterable<Object> tokens) {
		System.out.print(title + ":");
		for (Object token : tokens) {
			System.out.print(" " + token);
		}
		System.out.println();
	}
}
