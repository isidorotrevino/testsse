create table prueba (
   id int4,
   contenido text
);

create sequence seq_prueba;

CREATE OR REPLACE FUNCTION public.trig_actualizacion_prueba()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	if TG_OP ='INSERT' then
	   perform(
		   with payload(id,nombre,valor) as (
		   	select nextval('seq_prueba'),'juan',random()
		   )
		   select pg_notify('notificacion_prueba',row_to_json(payload)::text) from payload
	  );
		return new;
	elsif TG_OP='UPDATE' then
		perform(
			with payload(id,nombre,valor) as (
		   	select nextval('seq_prueba'),'pedro',random()
		   )
		   select pg_notify('notificacion_prueba',row_to_json(payload)::text) from payload
		);
		return new;
	else 
		perform(
			with payload(id,nombre,valor) as (
		   	select nextval('seq_prueba'),'no entro en las condiciones',random()
		   )
		   select pg_notify('notificacion_prueba',row_to_json(payload)::text) from payload
		);
		return new;
	end if;
	return new;
end;
$function$
;


create trigger trg_actualizacion_prueba after insert
    or update
        on
        public.prueba for each row execute procedure public.trig_actualizacion_prueba()


insert into prueba(id,contenido) values (1,'yadayada');

listen prueba;
