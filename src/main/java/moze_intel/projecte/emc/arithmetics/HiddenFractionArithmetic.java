package moze_intel.projecte.emc.arithmetics;

import moze_intel.projecte.emc.IValueArithmetic;

import org.apache.commons.lang3.math.Fraction;

public class HiddenFractionArithmetic implements IValueArithmetic<Fraction>
{

	@Override
	public boolean isZero(Fraction value)
	{
		return value.getNumerator() == 0;
	}

	@Override
	public Fraction getZero()
	{
		return Fraction.ZERO;
	}

	@Override
	public Fraction add(Fraction a, Fraction b)
	{
		if (isFree(a)) return b;
		if (isFree(b)) return a;

		return a.add(b);
	}

	@Override
	public Fraction mul(int a, Fraction b)
	{
		if (this.isFree(b)) return getFree();
		return zeroOrInt(b.multiplyBy(Fraction.getFraction(a, 1)));
	}

	@Override
	public Fraction div(Fraction a, int b)
	{
		if (this.isFree(a)) return getFree();
		Fraction result = a.divideBy(Fraction.getFraction(b, 1));
		if (Fraction.ZERO.compareTo(result) <= 0 && result.compareTo(Fraction.ONE) < 0)
		{
			return result;
		}
		return Fraction.getFraction(result.intValue(), 1);
	}

	@Override
	public Fraction getFree()
	{
		return Fraction.getFraction(Integer.MIN_VALUE, 1);
	}

	@Override
	public boolean isFree(Fraction value)
	{
		return value.getNumerator() == Integer.MIN_VALUE;
	}

	protected Fraction zeroOrInt(Fraction value)
	{
		if (Fraction.ZERO.compareTo(value) <= 0 && value.compareTo(Fraction.ONE) < 0) return Fraction.ZERO;
		return Fraction.getFraction(value.intValue(), 1);
	}
}
