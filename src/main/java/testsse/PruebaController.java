package testsse;

import javax.inject.Inject;

import org.reactivestreams.Publisher;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;

@Controller("/")
public class PruebaController {

	@Inject
	protected PruebaService pruebaService;
	
	@Get("/suscribirnos")
	public Publisher<Event<String>> suscribirnos(){
		//Regresamos el servicio, porque es un publisher
		//Micronaut se encarga de convertir cada evento, en un paquete SSE (por detrás)
		return pruebaService;
	}
	
}
