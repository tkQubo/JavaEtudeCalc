package test.com.qubo.challenge.calc.tokens;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.qubo.challenge.calc.tokens.Paren;

/**
 * {@link Paren}�p�̃e�X�g�N���X
 * @author Qubo
 */
public class ParenTest {
	/** {@link Paren#toString()}�̃e�X�g */
	@Test
	public void testToString() {
		assertThat(Paren.Left.toString(), is(""+Paren.SYMBOL_PAREN_LEFT));
		assertThat(Paren.Right.toString(), is(""+Paren.SYMBOL_PAREN_RIGHT));
	}

}
