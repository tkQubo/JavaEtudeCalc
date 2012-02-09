package test.com.qubo.challenge.calc.logics;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.junit.Test;

import com.qubo.challenge.calc.logics.Calculator;
import com.qubo.challenge.calc.logics.InfixNotationTokenizer;
import com.qubo.challenge.calc.logics.InvalidFormulaException;
import com.qubo.challenge.calc.tokens.Value;

/**
 * {@link Calculator}�p�̃e�X�g���`�����N���X
 * @author Qubo
 */
public class CalculatorTest {
	Calculator calculator = new Calculator();

	/** {@link Calculator#eval(String)}�̃e�X�g */
	@Test
	public void testEval() {
		try {
			doTestEval("2+5", 7);
			doTestEval("2 + 5", 7);
			doTestEval("-11", -11);
			doTestEval("--11", 11);
			doTestEval("2/1+5*3", 17);
			doTestEval("2 - 4 * (3 - 1)", -6);
			doTestEval("-5 * -3 / -(1---4)", 5);
			doTestEval("1/3 + 2/3 - 3/2 * 8 - 4", -15);
			doTestEval("(2 / 7 + 5 / 14) * 10 / (9/2)", 10/7.0f);
		} catch (InvalidFormulaException e) {
			fail(e.getMessage());
		}
		failTestEval("2+&", "&");
		failTestEval("4+%%%", "%%%");
		failTestEval("3.14", "3.14");
		failTestEval("3��6", "3��6");
		failTestEval("h", "h");
	}
	/**
	 * {@link #testEval()}�p�̓������\�b�h�B��`����Ă��Ȃ��g�[�N�����g�p���āA��O���������邩�ǂ������`�F�b�N
	 * @param input ����
	 * @param invalidToken ��O�ŕ\�������g�[�N��
	 */
	private void failTestEval(String input, String invalidToken) {
		try {
			calculator.eval(input);
			fail();
		} catch (InvalidFormulaException e) {
			String message = MessageFormat.format(InfixNotationTokenizer.ERROR_UNRECOGNIZED_TOKEN, invalidToken);
			assertThat(e.getMessage(), is(message));
		}
	}
	/**
	 * {@link #testEval()}�p�̓������\�b�h
	 * @param input
	 * @param expected
	 * @throws InvalidFormulaException �����ɃG���[���������ꍇ�ɔ���
	 */
	private void doTestEval(String input, double expected) throws InvalidFormulaException {
		Value value = calculator.eval(input);
		assertEquals(value.getRealValue(), expected, 0.0000001f);
	}

}
