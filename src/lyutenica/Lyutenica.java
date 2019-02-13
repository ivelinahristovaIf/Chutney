package lyutenica;

import java.time.LocalDateTime;
import java.util.List;

public class Lyutenica {
	private final List<Zelenchyk> partida;
	private final LocalDateTime date;
	private final int kg;

	public Lyutenica(List<Zelenchyk> partida, LocalDateTime date, int kg) {
		this.partida = partida;
		this.date = date;
		this.kg = kg;
	}

	@Override
	public String toString() {
		return "Lyutenica [date: " + date + ", kg: " + kg +", partida:" + partida + "]";
	}

}
