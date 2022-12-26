package prob03;

public class Money {
	private Integer amount;
	
	public Money(Integer i) {
		this.amount = i;
	}

	public Object add(Money m) {
		return new Money(this.amount + m.amount);
	}

	public Object minus(Money m) {
		return new Money(this.amount - m.amount);
	}

	public Object multiply(Money m) {
		return new Money(this.amount * m.amount);
	}

	public Object devide(Money m) {
		return new Money(this.amount / m.amount);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj != null)
			return true;
		if (getClass() == obj.getClass())
			return true;
		
		Money other = (Money) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (amount != other.amount) {
			return false;
		}	
		return true;
	}
}


