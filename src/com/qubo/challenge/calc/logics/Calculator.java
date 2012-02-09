package com.qubo.challenge.calc.logics;

import com.qubo.challenge.calc.tokens.Value;

/**
 * ������ŗ^����ꂽ�������v�Z���A���ʂ�Value�^�Ƃ��ĕԂ��N���X�B
 * @author Qubo
 */
public class Calculator {
	/** �v�Z�ߒ��Ő����钆�u�L�@�g�[�N���� */
	private Iterable<Object> infixNotationTokens;
	/** �v�Z�ߒ��Ő������u�L�@�g�[�N���� */
	private Iterable<Object> postfixNotationTokens;

	/**
	 * �Ō��{@link #eval(String)}�Ŏ��s���ꂽ�����́A���u�L�@�g�[�N�����Ԃ�
	 * @return ���u�L�@�g�[�N����
	 */
	public Iterable<Object> getInfixNotationTokens() { return infixNotationTokens; }
	/**
	 * �Ō��{@link #eval(String)}�Ŏ��s���ꂽ�����́A��u�L�@�g�[�N�����Ԃ�
	 * @return ��u�L�@�g�[�N����
	 */
	public Iterable<Object> getPostfixNotationTokens() { return postfixNotationTokens; }

	/**
	 * ������ŗ^����ꂽ�������v�Z���A���ʂ�Value�^�Ƃ��ĕԂ��B
	 * <br />
	 * �v�Z�͈ȉ��̃v���Z�X�Ő��藧�B
	 * <ol>
	 * <li>�������A���E���Z�q�E���ʂȂǂ̍\���v�f���Ƃɐ؂蕪����</li>
	 * <li>���̔z��𒆒u�L�@�̃g�[�N����֕ϊ�</li>
	 * <li>���������u�L�@�֕ϊ�</li>
	 * <li>������������A�v�Z�v�Z���ʂ��擾����</li>
	 * </ol>
	 * 1�`3�̉ߒ��Ŕ����������u�L�@�g�[�N����A��u�L�@�g�[�N����́A���\�b�h�̎��s��A���ꂼ��
	 * {@link #getInfixNotationTokens()}�A{@link #getPostfixNotationTokens()}�Ŏ擾���邱�Ƃ��ł���B
	 *
	 * @param input ����
	 * @return �v�Z����
	 * @throws InvalidFormulaException �����ɃG���[���������ꍇ�ɔ���
	 */
	public Value eval(String input) throws InvalidFormulaException {
		InfixNotationTokenizer tokenizer = new InfixNotationTokenizer();
		PostfixNotationConverter converter = new PostfixNotationConverter();
		PostfixNotationEvaluator evaluator = new PostfixNotationEvaluator();

		infixNotationTokens = tokenizer.tokenize(input);
		postfixNotationTokens = converter.convert(infixNotationTokens);
		Value value = evaluator.eval(postfixNotationTokens);

		return value;
	}
}
