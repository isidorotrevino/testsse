package testsse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;
import io.reactiverse.reactivex.pgclient.PgConnection;
import io.reactiverse.reactivex.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class PruebaSSEDao {
	
	// Inyectamos dependencia del pool de conexiones
	@Inject
	protected PgPool client;

	@Inject
	protected PruebaService pruebaService;
	
	@EventListener
	@Async
	public void inicializarServicio(final ServiceStartedEvent event) {
		//inicializamos la conexión
	
		client.getConnection(ar1 -> {
			
			PgConnection conn = ar1.result();
			conn.notificationHandler(handler -> {
				if("notificacion_prueba".equals(handler.getChannel())) {
					pruebaService.notificarEventoDesdeBD(handler.getPayload());
				}
			});
			
			conn.query("LISTEN notificacion_prueba", h ->{
				log.info("Ya estamos escuchando notificacion_prueba");
			});
			
		});
	}
}
