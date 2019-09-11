package testsse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.micronaut.http.sse.Event;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class PruebaService implements Publisher<Event<String>> {
	
	protected List<Subscriber<? super Event<String>>> suscriptores = new ArrayList<>();

	@Override
	public void subscribe(Subscriber<? super Event<String>> s) {
		log.info("Se está suscribiendo {}",s);
		suscriptores.add(s);
	}

	public void notificarEventoDesdeBD(String evento) {
		for(Subscriber<? super Event<String>> s : suscriptores) {
			try {
				s.onNext(Event.of(evento));
			}catch(Throwable t) {
				log.error("Ocurrió un error al notificar {}",s, t);
			}
		}
	}
	
}
